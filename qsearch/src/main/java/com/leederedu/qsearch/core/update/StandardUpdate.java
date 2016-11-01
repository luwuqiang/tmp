package com.leederedu.qsearch.core.update;

import com.leederedu.qsearch.core.QSearchCommand;
import com.leederedu.qsearch.core.SolrCore;
import com.leederedu.qsearch.handler.component.HighlightComponent;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuwuqiang on 2016/10/27.
 */
public class StandardUpdate extends UpdateBase {


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
