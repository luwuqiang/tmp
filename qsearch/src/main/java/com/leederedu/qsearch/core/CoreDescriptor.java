package com.leederedu.qsearch.core;


import com.leederedu.qsearch.core.cfg.IndexDescriptor;
import com.leederedu.qsearch.core.schema.SimpleSchema;

/**
 * Created by liuwuqiang on 2016/10/26.
 */
public class CoreDescriptor extends IndexDescriptor {

    @Override
    public void init() {
        super.setiSchema(SimpleSchema.class);
    }
}
