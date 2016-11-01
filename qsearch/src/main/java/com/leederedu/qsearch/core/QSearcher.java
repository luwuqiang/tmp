package com.leederedu.qsearch.core;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import com.leederedu.qsearch.core.cfg.SolrConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Lynch on 2016/10/24.
 */
public class QSearcher {
    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private String qsearchConf = "qsearch.properties";
    protected volatile CoreContainer cores;

    public void init() throws IOException {
        log.info("QSearcher.init(): {}", this.getClass().getClassLoader());

        SolrConfig solrConfig = new SolrConfig(this.getQsearchConf());
        solrConfig.init();

        this.cores = createCoreContainer(solrConfig);
    }

    public SolrCore getCore(IndexName indexName) {
        return this.getCores().getCore(indexName.val());
    }

    /**
     * Override this to change CoreContainer initialization
     *
     * @return a CoreContainer to hold this server's cores
     */
    protected CoreContainer createCoreContainer(SolrConfig solrConfig) {
        cores = new CoreContainer(solrConfig, true);
        cores.load();
        return cores;
    }

    public void destroy() {
        log.info("QSearcher destroy.");
        if (cores != null) {
            try {
                cores.shutdown();
            } finally {
                cores = null;
            }
        }
    }

    public CoreContainer getCores() {
        return cores;
    }

    public String getQsearchConf() {
        return qsearchConf;
    }

    public void setQsearchConf(String qsearchConf) {
        this.qsearchConf = qsearchConf;
    }
}
