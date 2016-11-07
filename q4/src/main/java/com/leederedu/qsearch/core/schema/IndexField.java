package com.leederedu.qsearch.core.schema;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by liuwuqiang on 2016/11/2.
 */
@Target(value = {ElementType.FIELD, ElementType.TYPE})
@Retention(value = RetentionPolicy.RUNTIME)
public @interface IndexField {

    boolean request() default false;

    boolean indexed() default true;

    boolean stored() default true;

    boolean multiValued() default false;
}
