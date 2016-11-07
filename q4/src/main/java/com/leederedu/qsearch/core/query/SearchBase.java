package com.leederedu.qsearch.core.query;

import com.leederedu.qsearch.core.SolrCore;
import com.leederedu.qsearch.handler.SearchHandler;
import com.leederedu.qsearch.utils.DocumentBuilder;
import org.apache.lucene.document.Document;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by liuwuqiang on 2016/10/25.
 */
public abstract class SearchBase implements SearchHandler {

    public final SolrCore core;

    public abstract void init(List<String> argsList);

    protected SearchBase(SolrCore core) {
        this.core = core;
    }

    public void queryResultToMap(QueryResult qr) {
        for (int id : qr.getDocs()) {
            Map<String, Object> document = DocumentBuilder.docToMap(
                    getDocById(id), core.getLatestSchema());
            qr.addDocument(document);
        }
    }

    public void queryResultToISchema(QueryResult qr) {
        int[] docsIds = qr.getDocs();
        if (docsIds != null && docsIds.length > 0) {
            for (int id : docsIds) {
                Map<String, Object> document = DocumentBuilder.docToMap(
                        getDocById(id), core.getLatestSchema());
                qr.addDocument(document);
            }
        }
    }

    public Document getDocById(int docId) {
        try {
            return core.getIndexSearcher().doc(docId);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
