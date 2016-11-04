package com.leederedu.qsearch.core;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by liuwuqiang on 2016/11/2.
 */
public class IndexSchema {

    private String primaryFieldName;
    private Map<String, Method> setterMap = new HashMap<String, Method>();
    private Map<String, Method> getterMap = new HashMap<String, Method>();

    public String getPrimaryFieldName() {
        return primaryFieldName;
    }

    public IndexSchema setPrimaryFieldName(String primaryFieldName) {
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

    public void addFieldSetter(Class type, Field field) {
        this.setterMap.put(field.getName(), this.setter(type, field));
    }

    public void addFieldGetter(Class type, Field field) {
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

}
