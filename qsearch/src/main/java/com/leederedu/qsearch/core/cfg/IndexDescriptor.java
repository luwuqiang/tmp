package com.leederedu.qsearch.core.cfg;

import com.leederedu.qsearch.core.schema.ISchema;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by liuwuqiang on 2016/11/2.
 */
public abstract class IndexDescriptor {
    private Class<?> iSchema;

    private String name;
    private String instanceDir;

    public abstract void init();

    public String getName() {
        return name;
    }

    public IndexDescriptor setName(String name) {
        this.name = name;
        return this;
    }

    public Path getInstanceDir() {
        return Paths.get(instanceDir);
    }

    public IndexDescriptor setInstanceDir(String instanceDir) {
        this.instanceDir = instanceDir;
        return this;
    }

    public void setiSchema(Class<?> type) {
        if (type == null || !ISchema.class.isAssignableFrom(type)) {
            throw new RuntimeException("fail to set [setiSchema(Class<?> type)]");
        }
        this.iSchema = type;
    }

    public Class<?> getiSchema() {
        return iSchema;
    }
}
