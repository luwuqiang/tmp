package com.leederedu.qsearch.core;

import com.leederedu.qsearch.analysis.lucene.IKAnalyzer;
import com.leederedu.qsearch.core.cfg.IndexDescriptor;
import com.leederedu.qsearch.core.common.SolrException;
import com.leederedu.qsearch.core.manager.AbstractIndexManager;
import com.leederedu.qsearch.core.query.StandardSearch;
import com.leederedu.qsearch.core.schema.Id;
import com.leederedu.qsearch.core.schema.IndexField;
import com.leederedu.qsearch.core.update.StandardUpdate;
import com.leederedu.qsearch.handler.component.SearchComponent;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.util.ClasspathResourceLoader;
import org.apache.lucene.analysis.util.ResourceLoader;
import org.apache.lucene.codecs.Codec;
import org.apache.lucene.index.IndexDeletionPolicy;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.KeepOnlyLastCommitDeletionPolicy;
import org.apache.lucene.index.TrackingIndexWriter;
import org.apache.lucene.store.Directory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by liuwuqiang on 2016/10/24.
 */
public class SolrCore extends AbstractIndexManager implements Closeable {
    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private String name;


    private final PluginBag<SearchComponent> searchComponents = new PluginBag<>(SearchComponent.class, this);

    public SolrCore(IndexDescriptor indexDesc) {
        super(indexDesc);

        try {
            setName(indexDesc.getName());

            super.init(IndexWriterConfig.OpenMode.CREATE_OR_APPEND);
            loadSearchComponents();
            this.initHandler();
        } catch (Throwable e) {
            throw new SolrException(SolrException.ErrorCode.SERVER_ERROR, e.getMessage(), e);
        } finally {
        }
    }

    @Override
    protected IndexSchema initIndexSchema() {
        IndexSchema indexSchema = new IndexSchema();
        Class type = super.indexDesc.getiSchema();
        for (Field field : type.getDeclaredFields()) {
            if (field.getAnnotation(IndexField.class) != null
                    || field.getAnnotation(Id.class) != null) {
                indexSchema.addFieldSetter(type, field);
                indexSchema.addFieldGetter(type, field);
            }
            if (field.getAnnotation(Id.class) != null) {
                indexSchema.setPrimaryFieldName(field.getName());
            }
        }
        return indexSchema;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * Register the default search components
     */
    private void loadSearchComponents() {
        Map<String, SearchComponent> instances = createInstances(SearchComponent.standard_components);
        searchComponents.init(instances, this);
    }

    @Override
    public IndexWriterConfig getIndexWriterConfig() {
        Analyzer analyzer = new IKAnalyzer(true);
        IndexWriterConfig iwf = new IndexWriterConfig(analyzer);
        return iwf;
    }

    @Override
    public Directory getDirectory() throws IOException {
        DirectoryFactory directoryFactory = new StandardDirectoryFactory();
        Directory d = directoryFactory.get(super.getIndexPath(),
                DirectoryFactory.DirContext.DEFAULT, DirectoryFactory.LOCK_TYPE_NATIVE);
        return d;
    }

    @Override
    public IndexDeletionPolicy initDeletionPolicy() {
        return new KeepOnlyLastCommitDeletionPolicy();
    }

    private <T> Map<String, T> createInstances(Map<String, Class<? extends T>> map) {
        Map<String, T> result = new LinkedHashMap<>(map.size(), 1);
        ResourceLoader resourceLoader = new ClasspathResourceLoader();
        for (Map.Entry<String, Class<? extends T>> e : map.entrySet()) {
            try {
                Object o = resourceLoader.newInstance(e.getValue().getName(), e.getValue());
                result.put(e.getKey(), (T) o);
            } catch (Exception exp) {
                //should never happen
                throw new SolrException(SolrException.ErrorCode.SERVER_ERROR, "Unable to instantiate class", exp);
            }
        }
        return result;
    }

    @Override
    public void initHandler() {
        super.setSearchHandler(new StandardSearch(this));
        super.setUpdateHandler(new StandardUpdate(this));
    }

    // this core current usage count
    private final AtomicInteger refCount = new AtomicInteger(1);

    /**
     * expert: increments the core reference count
     */
    public void open() {
        refCount.incrementAndGet();
    }

    /**
     * @return a Search Component registered to a given name.  Throw an exception if the component is undefined
     */
    public SearchComponent getSearchComponent(String name) {
        return searchComponents.get(name);
    }

    @Override
    public Codec initCodec() {
        final CodecFactory factory;
        factory = new CodecFactory() {
            @Override
            public Codec getCodec() {
                return Codec.getDefault();
            }
        };
        return factory.getCodec();
    }


    @Override
    public void close() {
        int count = refCount.decrementAndGet();
        if (count > 0) {
            try {
                Thread.currentThread().sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        log.info(" CLOSING SolrCore " + this);

        try {
            TrackingIndexWriter tiw = super.getTrackingIndexWriter();
            IndexWriter indexWriter = tiw.getIndexWriter();
            indexWriter.commit();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
