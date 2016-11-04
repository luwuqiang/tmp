package com.leederedu.qsearch.core.query;

import com.leederedu.qsearch.core.SolrCore;
import com.leederedu.qsearch.handler.RequestHandler;
import com.leederedu.qsearch.handler.SearchHandler;

import java.util.List;

/**
 * Created by liuwuqiang on 2016/10/25.
 */
public abstract class SearchBase implements SearchHandler, RequestHandler {

    public final SolrCore core;

    public abstract void init(List<String> argsList);

    protected SearchBase(SolrCore core) {
        this.core = core;
    }

    public abstract void handleBody();

    @Override
    public void excuteHanlder() {
        handleBody();
    }
}
