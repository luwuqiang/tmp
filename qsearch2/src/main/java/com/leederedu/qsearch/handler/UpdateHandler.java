package com.leederedu.qsearch.handler;

import com.leederedu.qsearch.core.schema.ISchema;

/**
 * Created by liuwuqiang on 2016/10/27.
 */
public interface UpdateHandler {

    public boolean deleteAll();

    public void addDoc(ISchema ISchema);
}
