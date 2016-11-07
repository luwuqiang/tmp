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
    private String indexPath;

    public abstract void init();

    public String getName() {
        return name;
    }

    public IndexDescriptor setName(String name) {
        this.name = name;
        return this;
    }

    public String getIndexPath() {
        return indexPath;
    }

    public IndexDescriptor setIndexPath(String indexPath) {
        this.indexPath = indexPath;
        return this;
    }

    public void setiSchema(Class<?> type) {
        if (type == null || !ISchema.class.isAssignableFrom(type)) {
            throw new RuntimeException("索引字段表对象必须实现ISchema接口");
        }
        this.iSchema = type;
    }

    public Class<?> getiSchema() {
        return iSchema;
    }

    public Path getInstanceDirPath() {
        return Paths.get(indexPath);
    }
}
