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
import org.apache.lucene.index.Term;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by liuwuqiang on 2016/11/3.
 */
public class DocumentBuilder {

    private static void addField(Document doc, String name, Object val, boolean isStored) {
        if (val == null) return;

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

    public static Term newIdTerm(ISchema iSchema, IndexSchema schema) {
        Object id = schema.getValue(iSchema, schema.getPrimaryFieldName());
        return newIdTerm(id, schema);
    }

    public static Term newIdTerm(Object id, IndexSchema schema) {
        Term term = new Term(schema.getPrimaryFieldName(), id.toString());
        return term;
    }

    public static Document toDocument(ISchema iSchema, IndexSchema schema) {
        Document out = new Document();
        for (Field field : iSchema.getClass().getDeclaredFields()) {
            IndexField indexField = field.getAnnotation(IndexField.class);
            boolean isStored = false;
            if (indexField != null) {
                isStored = indexField.stored();
            }
            addField(out, field.getName(), schema.getValue(iSchema, field.getName()), isStored);
        }
        //todo
        return out;
    }

}
