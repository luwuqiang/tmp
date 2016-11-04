package com.leederedu.qsearch.core.manager;

import com.leederedu.qsearch.core.IndexSchema;
import com.leederedu.qsearch.core.StandardHandler;
import com.leederedu.qsearch.core.cfg.IndexDescriptor;
import com.leederedu.qsearch.handler.SearchHandler;
import com.leederedu.qsearch.handler.UpdateHandler;
import org.apache.lucene.codecs.Codec;
import org.apache.lucene.index.IndexDeletionPolicy;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
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
public abstract class AbstractIndexManager {

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

    public UpdateHandler updateHandler;
    public SearchHandler searchHandler;

    private final String indexPath;
    private final IndexSchema latestSchema;
    public final IndexDescriptor indexDesc;

    public IndexSchema getLatestSchema() {
        return latestSchema;
    }

    public AbstractIndexManager(IndexDescriptor indexDesc) {
        this.indexDesc = indexDesc;
        this.indexPath = indexDesc.getIndexPath();
        latestSchema = initIndexSchema();
    }

    protected abstract IndexSchema initIndexSchema();

    public void init(IndexWriterConfig.OpenMode openMode) {
        try {
            if (openMode == null) {
                openMode = IndexWriterConfig.OpenMode.CREATE_OR_APPEND;
            }

            IndexWriterConfig iwconf = getIndexWriterConfig();
            iwconf.setOpenMode(openMode).
                    setIndexDeletionPolicy(initDeletionPolicy())
                    .setCodec(initCodec());
            Directory d = getDirectory();
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


    public abstract IndexWriterConfig getIndexWriterConfig();

    public abstract Directory getDirectory() throws IOException;

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

    public IndexWriter getIndexWriter() {
        if (trackingIndexWriter == null) {
            // TODO: 2016/11/4
            return null;
        }
        return trackingIndexWriter.getIndexWriter();
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

    public abstract IndexDeletionPolicy initDeletionPolicy();

    public abstract Codec initCodec();

    public String getIndexPath() {
        return indexPath;
    }

    public UpdateHandler getUpdateHandler() {
        return updateHandler;
    }

    public void setUpdateHandler(UpdateHandler updateHandler) {
        this.updateHandler = updateHandler;
    }

    public SearchHandler getSearchHandler() {
        return searchHandler;
    }

    public void setSearchHandler(SearchHandler searchHandler) {
        this.searchHandler = searchHandler;
    }

    public abstract void initHandler();

}
