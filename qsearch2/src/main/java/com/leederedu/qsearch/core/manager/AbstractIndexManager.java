package com.leederedu.qsearch.core.manager;

import com.leederedu.qsearch.analysis.lucene.IKAnalyzer;
import com.leederedu.qsearch.core.CodecFactory;
import com.leederedu.qsearch.core.DirectoryFactory;
import org.apache.lucene.codecs.Codec;
import org.apache.lucene.index.IndexDeletionPolicy;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.KeepOnlyLastCommitDeletionPolicy;
import org.apache.lucene.index.TrackingIndexWriter;
import org.apache.lucene.search.ControlledRealTimeReopenThread;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ReferenceManager;
import org.apache.lucene.search.SearcherManager;
import org.apache.lucene.store.Directory;

import java.io.IOException;

/**
 * Created by liuwuqiang on 2016/11/3.
 */
public class AbstractIndexManager {

    private IndexWriter indexWriter;
    //更新索引文件的indexWriter
    private TrackingIndexWriter trackingIndexWriter;
    //索引管理对象
    private ReferenceManager<IndexSearcher> referenceManager;
    //索引重读线程
    private ControlledRealTimeReopenThread<IndexSearcher> controlledRealTimeReopenThread;

    //索引重读最大、最小时间间隔
    private double indexReopenMaxStaleSec;
    private double indexReopenMixStaleSec;

    AbstractIndexManager(String indexName, String path,
                         DirectoryFactory directoryFactory, IndexWriterConfig.OpenMode openMode) {
        try {
            if (openMode == null) {
                openMode = IndexWriterConfig.OpenMode.CREATE_OR_APPEND;
            }

            final Directory d = directoryFactory.get(path,
                    DirectoryFactory.DirContext.DEFAULT, DirectoryFactory.LOCK_TYPE_NATIVE);
            IKAnalyzer analyzer = new IKAnalyzer(true);
            IndexWriterConfig iwconf = new IndexWriterConfig(analyzer);
            iwconf.setOpenMode(openMode).
                    setIndexDeletionPolicy(initDeletionPolicy()).setCodec(initCodec());

            this.indexWriter = new IndexWriter(d, iwconf);
            this.trackingIndexWriter = new TrackingIndexWriter(indexWriter);

            this.referenceManager = new SearcherManager(
                    trackingIndexWriter.getIndexWriter(), false, null);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //开启守护线程
        this.setThread();
    }

    /**
     * 获取indexSearcher对象
     *
     * @return
     */
    public IndexSearcher getIndexSearcher() {
        try {
            return this.referenceManager.acquire();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public TrackingIndexWriter getTrackingIndexWriter() {
        return trackingIndexWriter;
    }

    /**
     * 创建索引管理线程
     */
    private void setThread() {
        this.controlledRealTimeReopenThread = new ControlledRealTimeReopenThread<IndexSearcher>(
                trackingIndexWriter, this.referenceManager, indexReopenMaxStaleSec, indexReopenMixStaleSec);
        this.controlledRealTimeReopenThread.setName("ReferenceManager Reopen Thread!");
        this.controlledRealTimeReopenThread.setPriority(Math.min(Thread.currentThread().getPriority() + 2, Thread.MAX_PRIORITY));
        this.controlledRealTimeReopenThread.setDaemon(true);
        this.controlledRealTimeReopenThread.start();
    }

    private IndexDeletionPolicy initDeletionPolicy() {
        return new KeepOnlyLastCommitDeletionPolicy();
    }

    private Codec initCodec() {
        final CodecFactory factory = new CodecFactory() {
            @Override
            public Codec getCodec() {
                return Codec.getDefault();
            }
        };
        return factory.getCodec();
    }
}
