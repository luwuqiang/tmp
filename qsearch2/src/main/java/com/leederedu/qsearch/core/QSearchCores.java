package com.leederedu.qsearch.core;

import com.leederedu.qsearch.core.common.SolrException;
import com.leederedu.qsearch.utils.DefaultSolrThreadFactory;
import com.leederedu.qsearch.utils.ExecutorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;

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

    protected void close() {
        Collection<SolrCore> coreList = new ArrayList<>();

        do {
            coreList.clear();
            synchronized (modifyLock) {
                coreList.addAll(cores.values());
                cores.clear();
            }
            ExecutorService coreCloseExecutor = ExecutorUtil.newMDCAwareFixedThreadPool(Integer.MAX_VALUE,
                    new DefaultSolrThreadFactory("coreCloseExecutor"));
            try {
                for (final SolrCore core : coreList) {
                    coreCloseExecutor.submit(new Callable<SolrCore>() {
                        @Override
                        public SolrCore call() throws Exception {
                            try {
                                core.close();
                            } catch (Throwable e) {
                                SolrException.log(log, "Error shutting down core", e);
                                if (e instanceof Error) {
                                    throw (Error) e;
                                }
                            } finally {
                            }
                            return core;
                        }
                    });
                }
            } finally {
                ExecutorUtil.shutdownAndAwaitTermination(coreCloseExecutor);
            }

        } while (coreList.size() > 0);
    }
}
