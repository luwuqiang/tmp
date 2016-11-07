package com.leederedu.qsearch.handler;

import com.leederedu.qsearch.core.QueryBuilder;
import org.apache.lucene.search.Query;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Created by liuwuqiang on 2016/10/25.
 */
public interface SearchHandler {

    public boolean queryForList();

    public Map<String, Object> queryForMap(long id) throws IOException;

    public <T> List<T> page(Class<T> type, int pageNo, int pageSize);

    public <T> List<T> page(Class<T> type, Query query, int pageNo, int pageSize);

}
