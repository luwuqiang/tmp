package com.leederedu.qsearch.core.update;

import com.leederedu.qsearch.core.QSearchCommand;
import com.leederedu.qsearch.core.SchemaField;
import com.leederedu.qsearch.core.SolrCore;
import com.leederedu.qsearch.utils.RefCounted;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.document.Document;

import java.io.IOException;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by liuwuqiang on 2016/11/1.
 */
public class DirectUpdateHandler {
    protected final SolrCoreState solrCoreState;
    protected final SolrCore core;
    // stats
    AtomicLong addCommands = new AtomicLong();
    AtomicLong addCommandsCumulative = new AtomicLong();
    AtomicLong deleteByIdCommands = new AtomicLong();
    AtomicLong deleteByIdCommandsCumulative = new AtomicLong();
    AtomicLong deleteByQueryCommands = new AtomicLong();
    AtomicLong deleteByQueryCommandsCumulative = new AtomicLong();
    AtomicLong expungeDeleteCommands = new AtomicLong();
    AtomicLong mergeIndexesCommands = new AtomicLong();
    AtomicLong commitCommands = new AtomicLong();
    AtomicLong optimizeCommands = new AtomicLong();
    AtomicLong rollbackCommands = new AtomicLong();
    AtomicLong numDocsPending = new AtomicLong();
    AtomicLong numErrors = new AtomicLong();
    AtomicLong numErrorsCumulative = new AtomicLong();

    public DirectUpdateHandler(SolrCore core, SolrCoreState solrCoreState) {
        this.core = core;
        this.solrCoreState = solrCoreState;
    }

    public int addDoc(QSearchCommand cmd) throws IOException {
        try {
            return addDoc0(cmd);
        } catch (Exception e) {
            throw e;
        }
    }

    private int addDoc0(QSearchCommand cmd) throws IOException {
        int rc = -1;

        addCommands.incrementAndGet();
        addCommandsCumulative.incrementAndGet();

        try {
            doNormalUpdate(cmd);

            // TODO: 2016/11/1 软提交

            rc = 1;
        } finally {
            if (rc != 1) {
                numErrors.incrementAndGet();
                numErrorsCumulative.incrementAndGet();
            } else {
                numDocsPending.incrementAndGet();
            }
        }

        return rc;
    }

    private void doNormalUpdate(QSearchCommand cmd) throws IOException {
        System.out.println("doNormalUpdate...");
        Term updateTerm;
        // TODO: 2016/11/1
        Term idTerm = new Term(SchemaField.uniqueKeyFieldName, cmd.getIndexedId());
        boolean del = false;
        if (cmd.updateTerm == null) {
            updateTerm = idTerm;
        } else {
            // this is only used by the dedup update processor
            del = true;
            updateTerm = cmd.updateTerm;
        }

        RefCounted<IndexWriter> iw = solrCoreState.getIndexWriter(core);
        try {
//            IndexWriter writer = iw.get();
//            if (cmd.isBlock()) {
//                writer.updateDocuments(updateTerm, cmd);
//            } else {
//                Document luceneDocument = cmd.getLuceneDocument();
//                // SolrCore.verbose("updateDocument",updateTerm,luceneDocument,writer);
//                writer.updateDocument(updateTerm, luceneDocument);
//            }
//            // SolrCore.verbose("updateDocument",updateTerm,"DONE");
//
//            if (del) { // ensure id remains unique
//                BooleanQuery.Builder bq = new BooleanQuery.Builder();
//                bq.add(new BooleanClause(new TermQuery(updateTerm),
//                        BooleanClause.Occur.MUST_NOT));
//                bq.add(new BooleanClause(new TermQuery(idTerm), BooleanClause.Occur.MUST));
//                writer.deleteDocuments(new DeleteByQueryWrapper(bq.build(), core.getLatestSchema()));
//            }

        } finally {
            iw.decref();
        }


    }

}
