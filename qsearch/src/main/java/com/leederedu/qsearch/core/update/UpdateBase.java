package com.leederedu.qsearch.core.update;

import com.leederedu.qsearch.core.SolrCore;
import com.leederedu.qsearch.handler.UpdateHandler;


/**
 * Created by liuwuqiang on 2016/10/27.
 */
public abstract class UpdateBase extends DirectUpdateHandler implements UpdateHandler {

    public abstract void init();

    public abstract void inform(SolrCore core);

    public abstract void handleRequestBody();

    public void handlerRequest() {
        handleRequestBody();
    }
}
