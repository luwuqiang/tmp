package com.leederedu.qsearch.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
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


    protected SolrCore putCore(String name, SolrCore core) {
        synchronized (modifyLock) {
            return cores.put(name, core);
        }
    }

    /* If you don't increment the reference count, someone could close the core before you use it. */
    SolrCore getCoreFromAnyList(String name, boolean incRefCount) {
        synchronized (modifyLock) {
            SolrCore core = cores.get(name);

            if (core != null && incRefCount) {
                core.open();
            }

            return core;
        }
    }
}
