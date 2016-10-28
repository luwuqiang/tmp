package com.leederedu.qsearch.core;

import com.leederedu.qsearch.core.cfg.SolrConfig;
import com.leederedu.qsearch.handler.SearchHandler;

/**
 * Created by liuwuqiang on 2016/10/25.
 */
public final class RequestHandlers {

    final PluginBag<SearchHandler> handlers;
    protected final SolrCore core;

    public RequestHandlers(SolrCore core) {
        this.core = core;
        // we need a thread safe registry since methods like register are currently documented to be thread safe.
        handlers = new PluginBag<>(SearchHandler.class, core, true);
    }

    /**
     * Trim the trailing '/' if it's there, and convert null to empty string.
     * <p>
     * we want:
     * /update/csv   and
     * /update/csv/
     * to map to the same handler
     */
    public static String normalize(String p) {
        if (p == null) return "";
        if (p.endsWith("/") && p.length() > 1)
            return p.substring(0, p.length() - 1);

        return p;
    }
    void initHandlersFromConfig(SolrConfig config) {

    }
}
