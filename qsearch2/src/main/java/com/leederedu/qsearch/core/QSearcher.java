package com.leederedu.qsearch.core;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.List;

import com.leederedu.qsearch.core.cfg.IndexDescriptor;
import com.leederedu.qsearch.core.cfg.SolrConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Lynch on 2016/10/24.
 */
public class QSearcher {
    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    protected volatile CoreContainer coreContainer;
    private List<IndexDescriptor> indexList;

    public void init() throws IOException {
        log.info("QSearcher.init(): {}", this.getClass().getClassLoader());

        this.coreContainer = createCoreContainer();
    }

    public SolrCore getCore(IndexName indexName) {
        return this.getCores().getCore(indexName.val());
    }

    /**
     * Override this to change CoreContainer initialization
     *
     * @return a CoreContainer to hold this server's cores
     */
    protected CoreContainer createCoreContainer() {
        coreContainer = new CoreContainer(indexList, true);
        coreContainer.load();
        return coreContainer;
    }

    public void destroy() {
        log.info("QSearcher destroy...");
        if (coreContainer != null) {
            try {
                coreContainer.shutdown();
            } finally {
                coreContainer = null;
            }
        }
    }

    public CoreContainer getCores() {
        return coreContainer;
    }

    public void setIndexList(List<IndexDescriptor> indexList) {
        this.indexList = indexList;
    }

    public List<IndexDescriptor> getIndexList() {
        return indexList;
    }
}
