package com.leederedu.qsearch.core.query;

import com.leederedu.qsearch.core.SolrCore;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.Collector;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.TopDocsCollector;
import org.apache.lucene.search.TopFieldCollector;
import org.apache.lucene.search.TopScoreDocCollector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Map;

/**
 * Created by liuwuqiang on 2016/10/25.
 */
public class StandardSearch extends SearchBase {
    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    private List<String> componentsName;

    public StandardSearch(SolrCore core) {
        super(core);
    }


    @Override
    public void init(List<String> argsList) {
        this.componentsName = argsList;
    }

    @Override
    public boolean queryForList() {

        log.info("queryForList..");
        return false;
    }

    @Override
    public Map<String, Object> queryForMap(long id) throws IOException {
        QueryCommand cmd = new QueryCommand();

        Term Idterm = new Term(super.core.getLatestSchema().primaryField(), String.valueOf(id));
        Query query = new TermQuery(Idterm);
        cmd.setQuery(query);

        QueryResult qr = getDocListNC(cmd);

        super.queryResultToMap(qr);
        if (qr.getCount() > 0) {
            return qr.getResult().get(0);
        }
        return null;
    }

    @Override
    public <T> List<T> page(Class<T> type, int pageNo, int pageSize) {

        return null;
    }

    @Override
    public <T> List<T> page(Class<T> type, Query query, int pageNo, int pageSize) {
        return null;
    }

    private QueryResult getDocListNC(QueryCommand cmd) throws IOException {
        int len = cmd.getSupersetMaxDoc();

        final int lastDocRequested = len;
        float maxScore;
        int nDocsReturned;
        int[] ids;

        //handle zero case
        // TODO: 2016/11/7

        final TopDocsCollector topCollector = buildTopDocsCollector(len, cmd);
        Collector collector = topCollector;
        super.core.getIndexSearcher().search(cmd.getQuery(), collector);

        int totalHits = topCollector.getTotalHits();
        TopDocs topDocs = topCollector.topDocs(0, len);

        maxScore = totalHits > 0 ? topDocs.getMaxScore() : 0.0f;
        nDocsReturned = topDocs.scoreDocs.length;
        ids = new int[nDocsReturned];
        for (int i = 0; i < nDocsReturned; i++) {
            ScoreDoc scoreDoc = topDocs.scoreDocs[i];
            ids[i] = scoreDoc.doc;
        }
        int sliceLen = Math.min(lastDocRequested, nDocsReturned);
        if (sliceLen < 0) sliceLen = 0;

        QueryResult qr = new QueryResult();
        qr.setDocList(0, sliceLen, ids, totalHits, maxScore);
        return qr;
    }

    private TopDocsCollector buildTopDocsCollector(int len, QueryCommand cmd) throws IOException {
        if (null == cmd.getSort()) {
            return TopScoreDocCollector.create(len);
        } else {
            // we have a sort
            return TopFieldCollector.create(cmd.getSort(), len, null, false, false, false);
        }
    }
}
