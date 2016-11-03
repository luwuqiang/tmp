package com.leederedu.qsearch.core;

import com.leederedu.qsearch.core.cfg.IndexDescriptor;
import com.leederedu.qsearch.core.cfg.SolrConfig;
import com.leederedu.qsearch.core.common.SolrException;
import com.leederedu.qsearch.core.query.StandardSearch;
import com.leederedu.qsearch.core.update.DefaultSolrCoreState;
import com.leederedu.qsearch.core.update.SolrCoreState;
import com.leederedu.qsearch.core.update.SolrIndexWriter;
import com.leederedu.qsearch.core.update.StandardUpdate;
import com.leederedu.qsearch.handler.SearchHandler;
import com.leederedu.qsearch.handler.component.SearchComponent;
import com.leederedu.qsearch.utils.RefCounted;
import org.apache.lucene.analysis.util.ClasspathResourceLoader;
import org.apache.lucene.analysis.util.ResourceLoader;
import org.apache.lucene.codecs.Codec;
import org.apache.lucene.index.IndexDeletionPolicy;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.KeepOnlyLastCommitDeletionPolicy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by liuwuqiang on 2016/10/24.
 */
public class SolrCore extends StandardHandler implements Closeable {
    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private String name;
    private IndexSchema latestSchema;
    private final DirectoryFactory directoryFactory;
    private final SolrCoreState solrCoreState;
    private final IndexDeletionPolicy indexDeletionPolicy;
    private IndexReaderFactory indexReaderFactory;
    private Codec codec;
    private final PluginBag<SearchComponent> searchComponents = new PluginBag<>(SearchComponent.class, this);

    public SolrCore(IndexDescriptor dcore) {
        setName(dcore.getName());
        this.indexDeletionPolicy = this.initDeletionPolicy();
        this.directoryFactory = new StandardDirectoryFactory();
        this.solrCoreState = new DefaultSolrCoreState(directoryFactory);

        try {
            this.codec = initCodec();

            initIndex(true);
//            initWriters();
            loadSearchComponents();

            this.initHandler();
        } catch (Throwable e) {
            throw new SolrException(SolrException.ErrorCode.SERVER_ERROR, e.getMessage(), e);
        } finally {
        }

    }


    public void execute(SearchHandler handler) {

    }


    public String getName() {
        return name;
    }

    public IndexSchema getLatestSchema() {
        return latestSchema;
    }


    /**
     * Returns the indexdir as given in index.properties. If index.properties exists in dataDir and
     * there is a property <i>index</i> available and it points to a valid directory
     * in dataDir that is returned Else dataDir/index is returned. Only called for creating new indexSearchers
     * and indexwriters. Use the getIndexDir() method to know the active index directory
     *
     * @return the indexdir as given in index.properties
     */
    public String getNewIndexDir() {
        String result = "index/";

        //todo
        return result;
    }

    public DirectoryFactory getDirectoryFactory() {
        return directoryFactory;
    }

    public IndexDeletionPolicy getDeletionPolicy() {
        return this.indexDeletionPolicy;
    }

    public Codec getCodec() {
        return codec;
    }

    public void setName(String name) {
        this.name = name;
    }

    // protect via synchronized(SolrCore.class)
    private static Set<String> dirs = new HashSet<>();

    void initIndex(boolean reload) throws IOException {
        String indexDir = getNewIndexDir();
        boolean indexExists = getDirectoryFactory().exists(indexDir);
        boolean firstTime;
        synchronized (SolrCore.class) {
            firstTime = dirs.add(getDirectoryFactory().normalize(indexDir));
        }
        initIndexReaderFactory();

        // Create the index if it doesn't exist.
        if (!indexExists) {
            log.warn("Solr index directory '" + new File(indexDir) + "' doesn't exist."
                    + " Creating new index...");

            SolrIndexWriter writer = SolrIndexWriter.create(this,
                    "SolrCore.initIndex", indexDir, getDirectoryFactory(),
                    true, getLatestSchema(), indexDeletionPolicy, codec);
            writer.close();
        }
    }

    /**
     * Register the default search components
     */
    private void loadSearchComponents() {
        Map<String, SearchComponent> instances = createInstances(SearchComponent.standard_components);
        searchComponents.init(instances, this);
    }

    private void initIndexReaderFactory() {
        IndexReaderFactory indexReaderFactory = new StandardIndexReaderFactory();
        this.indexReaderFactory = indexReaderFactory;
    }

    private IndexDeletionPolicy initDeletionPolicy() {
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
        super.setSearchHandler(new StandardSearch());
        super.setUpdateHandler(new StandardUpdate(this, solrCoreState));
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

    private Codec initCodec() {
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
            RefCounted<IndexWriter> iw = solrCoreState.getIndexWriter(this);
            IndexWriter indexWriter = iw.get();
            indexWriter.commit();
            indexWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
