package com.leederedu.qsearch.core;

import com.leederedu.qsearch.core.common.SolrException;
import com.leederedu.qsearch.handler.SearchHandler;
import com.leederedu.qsearch.handler.UpdateHandler;

/**
 * Created by liuwuqiang on 2016/10/25.
 */
public class QSUtils {

    private static QSearcher qSearcher;

    public static SearchHandler searchHandler() {
        return searchHandler(IndexName.INDEX);
    }

    public static SearchHandler searchHandler(IndexName index) {
        SolrCore solrCore = qSearcher.getCore(index);
        if (solrCore == null) {
            throw new RuntimeException("not found index name=" + index);
        }
        return solrCore.getSearchHandler();
    }

    public static UpdateHandler updateHandler() {
        return updateHandler(IndexName.INDEX);
    }

    private static UpdateHandler updateHandler(IndexName index) {
        SolrCore solrCore = qSearcher.getCore(index);
        if (solrCore == null) {
            throw new RuntimeException("not found index name=" + index);
        }
        return solrCore.getUpdateHandler();
    }

    public void setqSearcher(QSearcher qSearcher) {
        QSUtils.qSearcher = qSearcher;
    }
}
