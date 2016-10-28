package com.leederedu.qsearch.core;

/**
 * Created by liuwuqiang on 2016/10/25.
 */
public class RequestParsers {

    public QueryRequest parse(SolrCore core, String path) throws Exception {
        QueryRequest sreq = buildRequestFrom(core);
        return sreq;
    }

    private QueryRequest buildRequestFrom(SolrCore core) {
        QueryRequestBase q = new QueryRequestBase(core);
        return q;
    }
}
