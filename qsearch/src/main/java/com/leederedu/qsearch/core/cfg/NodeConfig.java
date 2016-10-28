package com.leederedu.qsearch.core.cfg;

import java.nio.file.Path;

/**
 * Created by liuwuqiang on 2016/10/24.
 */
public class NodeConfig {
    private final Path coreRootDirectory;
    private final Integer coreLoadThreads;
    private final int transientCacheSize;

    public static class NodeConfigBuilder {
        public static final int DEFAULT_CORE_LOAD_THREADS = 3;
    }

    public NodeConfig(Integer coreLoadThreads, Path coreRootDirectory, int transientCacheSize) {
        this.coreLoadThreads = coreLoadThreads;
        this.coreRootDirectory = coreRootDirectory;
        this.transientCacheSize = transientCacheSize;
    }

    public int getCoreLoadThreadCount() {
        return coreLoadThreads == null ? NodeConfigBuilder.DEFAULT_CORE_LOAD_THREADS : coreLoadThreads;
    }


    public Path getCoreRootDirectory() {
        return coreRootDirectory;
    }


    public int getTransientCacheSize() {
        return transientCacheSize;
    }
}
