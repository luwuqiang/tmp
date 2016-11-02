package com.leederedu.qsearch.core.schema;

import com.leederedu.qsearch.core.common.SolrException;

/**
 * Created by liuwuqiang on 2016/11/2.
 */
public abstract class FieldType {

    public void checkSchemaField(final SchemaField field) {
        // override if your field type supports doc values
        if (field.hasDocValues()) {
            throw new SolrException(SolrException.ErrorCode.SERVER_ERROR, "Field type " + this + " does not support doc values");
        }
    }


}
