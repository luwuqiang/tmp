package com.leederedu.qsearch.core.query;

import org.apache.lucene.search.Query;
import org.apache.lucene.search.Sort;

/**
 * Created by liuwuqiang on 2016/11/7.
 */
public class QueryCommand {
    private int supersetMaxDoc = 10;

    private Query query;
    private Sort sort;

    public int getSupersetMaxDoc() {
        return supersetMaxDoc;
    }

    public Query getQuery() {
        return query;
    }

    public QueryCommand setQuery(Query query) {
        this.query = query;
        return this;
    }

    public Sort getSort() { return sort; }
    public QueryCommand setSort(Sort sort) {
        this.sort = sort;
        return this;
    }


}
