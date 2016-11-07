package com.leederedu.qsearch.utils;

/**
 * Created by liuwuqiang on 2016/10/31.
 */
public class NumberUtils {

    public static int toInt(Object obj, int defaultVal) {
        if (obj == null) return defaultVal;
        try {
            return Integer.parseInt(obj.toString());
        } catch (Exception e) {
            return defaultVal;
        }
    }
}
