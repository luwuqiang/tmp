package com.leederedu.qsearch.handler.component;

/**
 * Created by liuwuqiang on 2016/10/26.
 */
public class ResponseBuilder {

    public static int STAGE_START = 0;
    public static int STAGE_PARSE_QUERY = 1000;
    public static int STAGE_TOP_GROUPS = 1500;
    public static int STAGE_EXECUTE_QUERY = 2000;
    public static int STAGE_GET_FIELDS = 3000;
    public static int STAGE_DONE = Integer.MAX_VALUE;
}
