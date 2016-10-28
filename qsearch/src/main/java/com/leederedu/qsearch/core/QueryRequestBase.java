package com.leederedu.qsearch.core;


/**
 * Created by liuwuqiang on 2016/10/25.
 */
public class QueryRequestBase implements QueryRequest {

    public QueryRequestBase(SolrCore core) {
    }

    @Override
    public SolrCore getCore() {
        return null;
    }
}
