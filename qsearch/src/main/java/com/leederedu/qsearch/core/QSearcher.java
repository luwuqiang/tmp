package com.leederedu.qsearch.core;

import java.lang.invoke.MethodHandles;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

import com.leederedu.qsearch.core.cfg.NodeConfig;
import com.leederedu.qsearch.core.cfg.QSearchResourceLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Lynch on 2016/10/24.
 */
public class QSearcher {
    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    protected volatile CoreContainer cores;
    private String qSearchHome;


    public void init() {
        log.info("QSearcher.init(): {}", this.getClass().getClassLoader());

        Properties extraProperties = null;
        if (extraProperties == null)
            extraProperties = new Properties();

        this.cores = createCoreContainer(qSearchHome == null ?
                QSearchResourceLoader.locateSolrHome() : Paths.get(qSearchHome), extraProperties);
    }

    public SolrCore getCore(IndexName indexName) {
        return this.getCores().getCore(indexName.name());
    }

    /**
     * Override this to change CoreContainer initialization
     *
     * @return a CoreContainer to hold this server's cores
     */
    protected CoreContainer createCoreContainer(Path qsearchHome, Properties extraProperties) {
        NodeConfig nodeConfig = loadNodeConfig(qsearchHome);
        cores = new CoreContainer(nodeConfig, extraProperties, true);
        cores.load();
        return cores;
    }

    private NodeConfig loadNodeConfig(Path qsearchHome) {
        return null;
    }

    public void destroy() {
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
}
