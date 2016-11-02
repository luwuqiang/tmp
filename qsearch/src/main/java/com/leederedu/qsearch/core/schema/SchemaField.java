package com.leederedu.qsearch.core.schema;

import java.util.Collections;
import java.util.Map;

/**
 * Created by liuwuqiang on 2016/11/2.
 */
public final class SchemaField extends FieldProperties{

    public enum TrieTypes {
        INTEGER,
        LONG,
        FLOAT,
        DOUBLE,
        DATE
    }
    private static final String FIELD_NAME = "name";
    private static final String TYPE_NAME = "type";
    private static final String DEFAULT_VALUE = "default";

    final String name;
    final FieldType type;
    final int properties;
    final String defaultValue;
    boolean required = false;  // this can't be final since it may be changed dynamically

    /** Declared field property overrides */
    Map<String,?> args = Collections.emptyMap();

    public SchemaField(String name, FieldType type) {
        this(name, type, type.properties, null);
    }

    /** Create a new SchemaField from an existing one by using all
     * of the properties of the prototype except the field name.
     */
    public SchemaField(SchemaField prototype, String name) {
        this(name, prototype.type, prototype.properties, prototype.defaultValue);
        args = prototype.args;
    }

    public SchemaField(String name, FieldType type, int properties, String defaultValue ) {
        this.name = name;
        this.type = type;
        this.properties = properties;
        this.defaultValue = defaultValue;

        // initialize with the required property flag
        required = (properties & REQUIRED) !=0;

        type.checkSchemaField(this);
    }

    public boolean hasDocValues() { return (properties & DOC_VALUES) != 0; }

}
