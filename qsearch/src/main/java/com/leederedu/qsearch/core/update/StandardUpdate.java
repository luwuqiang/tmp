package com.leederedu.qsearch.core.update;

import com.leederedu.qsearch.core.QSearchCommand;
import com.leederedu.qsearch.core.SolrCore;

import java.io.IOException;


/**
 * Created by liuwuqiang on 2016/10/27.
 */
public class StandardUpdate extends UpdateBase {

    public StandardUpdate(SolrCore solrCore, SolrCoreState solrCoreState) {
        super(solrCore, solrCoreState);
    }

    @Override
    public boolean deleteAll() {
        return false;
    }


    @Override
    public void init() {

    }

    @Override
    public void inform(SolrCore core) {

    }

    @Override
    public void handleRequestBody() {

    }

}
