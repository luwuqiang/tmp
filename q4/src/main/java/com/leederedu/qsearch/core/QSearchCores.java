package com.leederedu.qsearch.core;

import com.leederedu.qsearch.core.common.SolrException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by liuwuqiang on 2016/10/24.
 */
public class QSearchCores {
    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private final Map<String, SolrCore> cores = new LinkedHashMap<>(); // For "permanent" cores

    private static Object modifyLock = new Object(); // for locking around manipulating any of the core maps.

    private final CoreContainer container;

    QSearchCores(CoreContainer coreContainer) {
        this.container = coreContainer;
    }

    protected Object getModifyLock() {
        return modifyLock;
    }


    protected SolrCore putCore(String name, SolrCore core) {
        synchronized (modifyLock) {
            return cores.put(name, core);
        }
    }

    protected void close() {
        Collection<SolrCore> coreList = new ArrayList<>();

        do {
            coreList.clear();
            synchronized (modifyLock) {
                coreList.addAll(cores.values());
                cores.clear();
            }

            try {
                for (final SolrCore core : coreList) {
                    try {
                        core.close();
                    } catch (Exception e) {
                        SolrException.log(log, "Error shutting down core", e);
                    } finally {
                    }
                }
            } finally {
            }

        } while (coreList.size() > 0);
    }

    public SolrCore getCoreFromAnyList(String indexName) {
        return cores.get(indexName);
    }
}
