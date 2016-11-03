package com.leederedu.qsearch.core.schema;

import org.apache.lucene.document.FieldType;

import java.util.Collections;
import java.util.Map;

/**
 * Created by liuwuqiang on 2016/11/2.
 */
public final class SchemaField {

    public enum TrieTypes {
        INTEGER,
        LONG,
        FLOAT,
        DOUBLE,
        DATE,
        STRING
    }

    private static final String FIELD_NAME = "name";
    private static final String TYPE_NAME = "type";
    private static final String DEFAULT_VALUE = "default";

    final String name;
    final FieldType type;
    final String defaultValue;
    boolean required = false;  // this can't be final since it may be changed dynamically

    Map<String, ?> args = Collections.emptyMap();

    public SchemaField(String name, FieldType type) {
        this(name, type, null);
    }

    public SchemaField(SchemaField prototype, String name) {
        this(name, prototype.type, prototype.defaultValue);
        args = prototype.args;
    }

    public SchemaField(String name, FieldType type, String defaultValue) {
        this.name = name;
        this.type = type;
        this.defaultValue = defaultValue;

    }

}
