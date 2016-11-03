package com.leederedu.qsearch.utils;

import com.leederedu.qsearch.core.IndexSchema;
import com.leederedu.qsearch.core.schema.ISchema;
import com.leederedu.qsearch.core.schema.Id;
import com.leederedu.qsearch.core.schema.IndexField;
import com.leederedu.qsearch.core.schema.SimpleSchema;
import org.apache.lucene.analysis.payloads.IntegerEncoder;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.DoubleField;
import org.apache.lucene.document.FloatField;
import org.apache.lucene.document.IntField;
import org.apache.lucene.document.LongField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexableField;
import org.apache.lucene.document.Field.Store;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by liuwuqiang on 2016/11/3.
 */
public class DocumentBuilder {

    private static void addField(Document doc, String name, Object val, boolean isStored) {
        IndexableField f;
        Store store = isStored ? Store.YES : Store.NO;
        if (val instanceof Long) {
            f = new LongField(name, Long.parseLong(val.toString()), store);
        } else if (val instanceof String) {
            f = new StringField(name, val.toString(), store);
        } else if (val instanceof Float) {
            f = new FloatField(name, Float.parseFloat(val.toString()), store);
        } else if (val instanceof Integer) {
            f = new IntField(name, Integer.parseInt(val.toString()), store);
        } else if (val instanceof Double) {
            f = new DoubleField(name, Double.parseDouble(val.toString()), store);
        } else {
            f = new TextField(name, val.toString(), store);
        }
        doc.add(f);
    }

    public static Document toDocument(ISchema iSchema, IndexSchema schema) {
        Document out = new Document();
        for (Field field : iSchema.getClass().getDeclaredFields()) {
            IndexField indexField = field.getAnnotation(IndexField.class);
            boolean isStored = false;
            if (indexField != null) {
                isStored = indexField.stored();
            }
            addField(out, field.getName(), get(iSchema.getClass(), field, iSchema), isStored);
        }
        //todo
        return out;
    }


    private static Method getter(Class type, Field field) {
        String fieldName = field.getName();
        String methodName = "get" + Character.toUpperCase(fieldName.charAt(0))
                + fieldName.substring(1);
        Method result = null;
        try {
            result = type.getMethod(methodName);
        } catch (NoSuchMethodException ex) {
            if (field.getType() == boolean.class
                    || field.getType() == Boolean.class) {
                methodName = "is" + Character.toUpperCase(fieldName.charAt(0))
                        + fieldName.substring(1);
                try {
                    result = type.getMethod(methodName);
                } catch (NoSuchMethodException ex1) {
                    throw new RuntimeException(ex1);
                }
            } else {
                throw new RuntimeException(ex);
            }
        }
        if (result == null) {
            return null;
        }
        if (result.getReturnType() != field.getType()) {
            return null;
        }
        return result;
    }

    private static Method setter(Class type, Field field) {
        String fieldName = field.getName();
        String methodName = "set" + Character.toUpperCase(fieldName.charAt(0))
                + fieldName.substring(1);
        Method result = null;
        try {
            result = type.getMethod(methodName, field.getType());
        } catch (NoSuchMethodException ex) {
            throw new RuntimeException(ex);
        }
        if (result == null) {
            return null;
        }
        if (result.getReturnType() != void.class && result.getReturnType() != type) {
            return null;
        }
        return result;
    }

    public static Object get(Class type, Field field, Object obj) {
        try {
            Method method = getter(type, field);
            return method.invoke(obj);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
    }
}
