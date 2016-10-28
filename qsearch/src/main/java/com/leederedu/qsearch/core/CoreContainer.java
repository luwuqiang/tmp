package com.leederedu.qsearch.core;

import com.google.common.collect.Maps;
import com.leederedu.qsearch.core.cfg.NodeConfig;
import com.leederedu.qsearch.core.common.SolrException;
import com.leederedu.qsearch.utils.DefaultSolrThreadFactory;
import com.leederedu.qsearch.utils.ExecutorUtil;
import com.leederedu.qsearch.utils.SolrIdentifierValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.Locale;
import java.util.Properties;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Lynch on 2016/10/24.
 */
public class CoreContainer {

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private ExecutorService coreContainerWorkExecutor = ExecutorUtil.newMDCAwareCachedThreadPool(
            new DefaultSolrThreadFactory("coreContainerWorkExecutor"));
    protected Properties containerProperties;
    private boolean shutDown;
    protected final NodeConfig cfg;
    protected final CoresLocator coresLocator;
    final QSearchCores solrCores = new QSearchCores(this);

    public CoreContainer(NodeConfig config, Properties properties, boolean asyncSolrCoreLoad) {
        this(config, properties, new CorePropertiesLocator(config.getCoreRootDirectory()), asyncSolrCoreLoad);
    }

    public CoreContainer(NodeConfig config, Properties properties, CoresLocator locator, boolean asyncCoreLoad) {
        this.cfg = checkNotNull(config);
        this.containerProperties = new Properties(properties);
        this.coresLocator = locator;
    }

    public void load() {
        // setup executor to load cores in parallel
        final ExecutorService coreLoadExecutor = ExecutorUtil.newMDCAwareFixedThreadPool(
                cfg.getCoreLoadThreadCount(),
                new DefaultSolrThreadFactory("coreLoadExecutor"));
        final List<Future<SolrCore>> futures = new ArrayList<>();

        try {
            List<CoreDescriptor> cds = coresLocator.discover(this);
            checkForDuplicateCoreNames(cds);

            for (final CoreDescriptor cd : cds) {
                if (cd.isLoadOnStartup()) {
                    futures.add(coreLoadExecutor.submit(new Callable<SolrCore>() {
                        @Override
                        public SolrCore call() throws Exception {
                            SolrCore core;
                            try {
                                core = create(cd, false);
                            } finally {
                            }
                            return core;
                        }
                    }));
                }
            }
        } catch (Exception e) {
        } finally {
            if (futures != null) {
                Thread shutdownThread = new Thread() {
                    public void run() {
                        try {
                            for (Future<SolrCore> future : futures) {
                                try {
                                    future.get();
                                } catch (InterruptedException e) {
                                    Thread.currentThread().interrupt();
                                } catch (ExecutionException e) {
                                    log.error("Error waiting for SolrCore to be created", e);
                                }
                            }
                        } finally {
                            ExecutorUtil.shutdownAndAwaitTermination(coreLoadExecutor);
                        }
                    }
                };
                coreContainerWorkExecutor.submit(shutdownThread);
            } else {
                ExecutorUtil.shutdownAndAwaitTermination(coreLoadExecutor);
            }
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

    private static void checkForDuplicateCoreNames(List<CoreDescriptor> cds) {
        Map<String, Path> addedCores = Maps.newHashMap();
        for (CoreDescriptor cd : cds) {
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
    private SolrCore create(CoreDescriptor dcore, boolean publishState) {

        SolrCore core = null;
        try {
            SolrIdentifierValidator.validateCoreName(dcore.getName());
            core = new SolrCore(dcore, cfg, containerProperties);
            registerCore(dcore.getName(), core, publishState);
            return core;
        } catch (Exception e) {
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
