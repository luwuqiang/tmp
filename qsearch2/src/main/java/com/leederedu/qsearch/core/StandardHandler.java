package com.leederedu.qsearch.core;

import com.leederedu.qsearch.handler.SearchHandler;
import com.leederedu.qsearch.handler.UpdateHandler;

/**
 * Created by liuwuqiang on 2016/10/27.
 */
public abstract class StandardHandler {


    public UpdateHandler updateHandler;
    public SearchHandler searchHandler;

    public UpdateHandler getUpdateHandler() {
        return updateHandler;
    }

    public StandardHandler setUpdateHandler(UpdateHandler updateHandler) {
        this.updateHandler = updateHandler;
        return this;
    }

    public SearchHandler getSearchHandler() {
        return searchHandler;
    }

    public StandardHandler setSearchHandler(SearchHandler searchHandler) {
        this.searchHandler = searchHandler;
        return this;
    }

    public abstract void initHandler();
}
