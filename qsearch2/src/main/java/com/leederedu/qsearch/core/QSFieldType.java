package com.leederedu.qsearch.core;

import org.apache.lucene.document.FieldType;

/**
 * Created by liuwuqiang on 2016/11/3.
 */
public class QSFieldType extends FieldType {

    public enum Types {
        INTEGER,
        LONG,
        FLOAT,
        DOUBLE,
        DATE,
        STRING
    }


}
