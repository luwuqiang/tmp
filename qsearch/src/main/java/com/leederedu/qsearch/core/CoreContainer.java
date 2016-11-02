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

    private ExecutorService coreContainerWorkExecutor = ExecutorUtil.newMDCAwareCachedThreadPool(
            new DefaultSolrThreadFactory("coreContainerWorkExecutor"));
    private boolean shutDown;
    private final SolrConfig solrConfig;
    final QSearchCores solrCores = new QSearchCores(this);
    private final List<IndexDescriptor> indexList;

    public CoreContainer(List<IndexDescriptor> indexList, SolrConfig solrConfig, boolean asyncCoreLoad) {
        this.indexList = indexList;
        this.solrConfig = checkNotNull(solrConfig);
    }


    public void load() {
        try {
            if (indexList == null || indexList.isEmpty()) {
                throw new RuntimeException("can not found index");
            }

            checkForDuplicateCoreNames(indexList);
            //配置加载
            for (final IndexDescriptor dcore : indexList) {
                create(dcore, false);
            }
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
    }

    public boolean isShutDown() {
        return shutDown;
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

            core = new SolrCore(dcore, solrConfig);
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
