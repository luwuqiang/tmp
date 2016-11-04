package com.leederedu.qsearch.handler;

import com.leederedu.qsearch.core.schema.ISchema;

import java.util.List;

/**
 * Created by liuwuqiang on 2016/10/27.
 */
public interface UpdateHandler {

    public boolean deleteAll();

    public void addDoc(ISchema iSchema);

    public boolean deleteDoc(Object id);

    public boolean deleteDocs(Object[] ids);

    public void addDoc(List<ISchema> list);

    public void updateDoc(ISchema iSchema);
}
