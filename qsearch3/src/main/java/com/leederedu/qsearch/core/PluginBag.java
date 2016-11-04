package com.leederedu.qsearch.core;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by liuwuqiang on 2016/10/25.
 */
public class PluginBag<T> {
    private final Map<String, PluginHolder<T>> registry;
    private final Map<String, PluginHolder<T>> immutableRegistry;
    private SolrCore core;
    private final Class klass;

    /**
     * Constructs a non-threadsafe plugin registry
     */
    public PluginBag(Class<T> klass, SolrCore core) {
        this(klass, core, false);
    }

    public PluginBag(Class<T> klass, SolrCore core, boolean needThreadSafety) {
        this.core = core;
        this.klass = klass;
        // TODO: since reads will dominate writes, we could also think about creating a new instance of a map each time it changes.
        // Not sure how much benefit this would have over ConcurrentHashMap though
        // We could also perhaps make this constructor into a factory method to return different implementations depending on thread safety needs.
        this.registry = needThreadSafety ? new ConcurrentHashMap<String, PluginHolder<T>>() : new HashMap<String, PluginHolder<T>>();
        this.immutableRegistry = Collections.unmodifiableMap(registry);

    }

    /**
     * Get a plugin by name. If the plugin is not already instantiated, it is
     * done here
     */
    public T get(String name) {
        PluginHolder<T> result = registry.get(name);
        return result == null ? null : result.get();
    }

    /**
     * An indirect reference to a plugin. It just wraps a plugin instance.
     * subclasses may choose to lazily load the plugin
     */
    public static class PluginHolder<T> implements AutoCloseable {
        private T inst;

        public PluginHolder(T inst) {
            this.inst = inst;
        }

        public T get() {
            return inst;
        }

        public boolean isLoaded() {
            return inst != null;
        }

        @Override
        public void close() throws Exception {
            // TODO: there may be a race here.  One thread can be creating a plugin
            // and another thread can come along and close everything (missing the plugin
            // that is in the state of being created and will probably never have close() called on it).
            // can close() be called concurrently with other methods?
            if (isLoaded()) {
                T myInst = get();
                if (myInst != null && myInst instanceof AutoCloseable) ((AutoCloseable) myInst).close();
            }
        }

        public String getClassName() {
            if (isLoaded()) return inst.getClass().getName();
            return null;
        }
    }
    
    void init(Map<String, T> defaults, SolrCore solrCore) {
        core = solrCore;
        for (Map.Entry<String, T> e : defaults.entrySet()) {
            if (!contains(e.getKey())) {
                put(e.getKey(), new PluginHolder<T>(e.getValue()));
            }
        }
    }

    public boolean contains(String name) {
        return registry.containsKey(name);
    }

    PluginHolder<T> put(String name, PluginHolder<T> plugin) {
        PluginHolder<T> old = registry.put(name, plugin);
        return old;
    }
}
