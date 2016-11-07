package com.leederedu.qsearch.core.query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liuwuqiang on 2016/11/7.
 */
public class QueryResult {
    private int offset;    // starting position of the docs (zero based)
    private int len;       // number of positions used in arrays

    private int[] docs;    // a slice of documents (docs 0-100 of the query)

    private List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();

    private int matches;
    private float maxScore;

    private int count = 0;

    public void setDocList(int offset, int len, int[] docs, int matches, float maxScore) {
        this.offset = offset;
        this.len = len;
        this.docs = docs;
        this.matches = matches;
        this.maxScore = maxScore;
    }

    public int[] getDocs() {
        return docs;
    }

    public List<Map<String, Object>> getResult() {
        return result;
    }

    public QueryResult setResult(List<Map<String, Object>> result) {
        this.result = result;
        this.count = result.size();
        return this;
    }

    public void addDocument(Map<String, Object> document) {
        this.result.add(document);
        count++;
    }

    public int getCount() {
        return count;
    }
}
