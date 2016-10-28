package com.leederedu.qsearch.core;

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
        return qSearcher.getCore(index).getSearchHandler();
    }

    public static UpdateHandler updateHandler() {
        return updateHandler(IndexName.INDEX);
    }

    private static UpdateHandler updateHandler(IndexName index) {
        return qSearcher.getCore(index).getUpdateHandler();
    }

    public static void setqSearcher(QSearcher qSearcher) {
        QSUtils.qSearcher = qSearcher;
    }

}
