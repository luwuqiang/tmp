package com.leederedu.qsearch.core;

import com.leederedu.qsearch.core.schema.ISchema;
import com.leederedu.qsearch.core.schema.Id;
import com.leederedu.qsearch.core.schema.IndexField;
import org.apache.lucene.index.IndexableField;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by liuwuqiang on 2016/11/2.
 */
public class IndexSchema {

    private final Class<?> type;
    private String primaryFieldName;
    private Map<String, Method> setterMap = new HashMap<String, Method>();
    private Map<String, Method> getterMap = new HashMap<String, Method>();

    public IndexSchema(Class<?> type) {
        if (type == null && !ISchema.class.isAssignableFrom(type)) {
            throw new RuntimeException("必须指定索引字段表");
        }
        this.type = type;
    }

    public String primaryField() {
        return primaryFieldName;
    }

    private IndexSchema setPrimaryFieldName(String primaryFieldName) {
        this.primaryFieldName = primaryFieldName;
        return this;
    }

    public Object getValue(Object obj, String fieldName) {
        Method method = this.getterMap.get(fieldName);
        try {
            if (method != null) {
                return method.invoke(obj);
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setValue(Object obj, IndexableField field) {
        try {
            Method method = this.setterMap.get(field.name());
            method.invoke(obj, this.getFieldIndexVal(field));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    private void addFieldSetter(Class type, Field field) {
        this.setterMap.put(field.getName(), this.setter(type, field));
    }

    private void addFieldGetter(Class type, Field field) {
        this.getterMap.put(field.getName(), this.getter(type, field));
    }

    private Method getter(Class type, Field field) {
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

    private Method setter(Class type, Field field) {
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

    public Object getFieldIndexVal(IndexableField field) {
        String fname = field.name();
        Class<?> fieldType = fieldType(fname);
        if (fieldType == String.class) {
            return field.stringValue();
        } else if (fieldType == int.class) {
            return field.numericValue().intValue();
        } else if (fieldType == long.class) {
            return field.numericValue().longValue();
        } else if (fieldType == double.class) {
            return field.numericValue().doubleValue();
        } else if (fieldType == float.class) {
            return field.numericValue().floatValue();
        }
        return field.readerValue();
    }

    public Class<?> fieldType(String fieldName) {
        Method method = setterMap.get(fieldName);
        Class<?> fieldType = null;
        if (method != null) {
            fieldType = method.getReturnType();
        }
        return fieldType;
    }

    public ISchema newISchema() {
        try {
            return (ISchema) type.newInstance();
        } catch (InstantiationException e) {
        } catch (IllegalAccessException e) {
        }
        return null;
    }

    public void init() {
        for (Field field : type.getDeclaredFields()) {
            if (field.getAnnotation(IndexField.class) != null
                    || field.getAnnotation(Id.class) != null) {
                this.addFieldSetter(type, field);
                this.addFieldGetter(type, field);
            }
            if (field.getAnnotation(Id.class) != null) {
                this.setPrimaryFieldName(field.getName());
            }
        }
    }
}
