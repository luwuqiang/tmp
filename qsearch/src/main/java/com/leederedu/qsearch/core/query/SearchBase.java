package com.leederedu.qsearch.core.query;

import com.leederedu.qsearch.core.PluginBag;
import com.leederedu.qsearch.core.SolrCore;
import com.leederedu.qsearch.handler.SearchHandler;
import com.leederedu.qsearch.handler.component.HighlightComponent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuwuqiang on 2016/10/25.
 */
public abstract class SearchBase implements SearchHandler {

    public abstract void init(List<String> argsList);

    public abstract void inform(SolrCore core);

    public abstract void handleBody();

}
