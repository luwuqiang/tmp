package com.leederedu.qsearch.core;

import com.google.common.collect.Maps;
import com.leederedu.qsearch.core.cfg.IndexDescriptor;
import com.leederedu.qsearch.core.cfg.SolrConfig;
import com.leederedu.qsearch.core.common.SolrException;
import com.leederedu.qsearch.utils.DefaultSolrThreadFactory;
import com.leederedu.qsearch.utils.ExecutorUtil;
import com.leederedu.qsearch.utils.SolrIdentifierValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.Locale;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Lynch on 2016/10/24.
 */
public class CoreContainer {

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    final QSearchCores solrCores = new QSearchCores(this);
    private final List<IndexDescriptor> indexList;

    public CoreContainer(List<IndexDescriptor> indexList, boolean asyncCoreLoad) {
        this.indexList = indexList;
    }


    public void load() {
        try {
            if (indexList == null || indexList.size() != 1) {
                throw new RuntimeException("当前仅支持一个索引");
            }

            checkForDuplicateCoreNames(indexList);
            //配置加载
            create(indexList.get(0), false);
        } catch (Exception e) {
            System.out.println(e);
        } finally {
        }
    }

    public SolrCore getCore(String indexName) {
        // Do this in two phases since we don't want to lock access to the cores over a load.
        SolrCore core = solrCores.getCoreFromAnyList(indexName, true);
        if (core != null) {
            //todo log
        }
        return core;
    }

    public void shutdown() {
        try {
            // First wake up the closer thread, it'll terminate almost immediately since it checks isShutDown.
            synchronized (solrCores.getModifyLock()) {
                solrCores.getModifyLock().notifyAll(); // wake up anyone waiting
            }
            // Now clear all the cores that are being operated upon.
            solrCores.close();

            // It's still possible that one of the pending dynamic load operation is waiting, so wake it up if so.
            // Since all the pending operations queues have been drained, there should be nothing to do.
            synchronized (solrCores.getModifyLock()) {
                solrCores.getModifyLock().notifyAll(); // wake up the thread
            }
        } catch (Exception ex) {

        }
    }

    private static void checkForDuplicateCoreNames(List<IndexDescriptor> cds) {
        Map<String, Path> addedCores = Maps.newHashMap();
        for (IndexDescriptor cd : cds) {
            final String name = cd.getName();
            if (addedCores.containsKey(name))
                throw new SolrException(SolrException.ErrorCode.SERVER_ERROR,
                        String.format(Locale.ROOT, "Found multiple cores with the name [%s], with instancedirs [%s] and [%s]",
                                name, addedCores.get(name), cd.getInstanceDir()));
            addedCores.put(name, cd.getInstanceDir());
        }
    }

    /**
     * Creates a new core based on a CoreDescriptor.
     *
     * @param dcore        a core descriptor
     * @param publishState publish core state to the cluster if true
     * @return the newly created core
     */
    private SolrCore create(IndexDescriptor dcore, boolean publishState) {

        SolrCore core = null;
        try {
            SolrIdentifierValidator.validateCoreName(dcore.getName());

            core = new SolrCore(dcore);
            registerCore(dcore.getName(), core, publishState);
            return core;
        } catch (Exception e) {
            //todo
            throw e;
        } finally {
        }
    }

    protected SolrCore registerCore(String name, SolrCore core, boolean registerInZk) {
        if (core == null) {
            throw new RuntimeException("Can not register a null core.");
        }
        SolrCore old;
        old = solrCores.putCore(name, core);
        core.setName(name);

        //todo
//        old.close();
        return old;
    }

}
