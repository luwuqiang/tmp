package com.leederedu.qsearch.core.update;

import com.leederedu.qsearch.core.QSearchCommand;
import com.leederedu.qsearch.core.SolrCore;
import com.leederedu.qsearch.core.schema.ISchema;
import com.leederedu.qsearch.utils.DocumentBuilder;
import com.leederedu.qsearch.utils.RefCounted;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;

import java.io.IOException;


/**
 * Created by liuwuqiang on 2016/10/27.
 */
public class StandardUpdate extends UpdateBase {

    public StandardUpdate(SolrCore solrCore, SolrCoreState solrCoreState) {
        super(solrCore, solrCoreState);
    }

    @Override
    public boolean deleteAll() {
        return false;
    }

    @Override
    public void addDoc(ISchema iSchema) {
        if (iSchema == null) {
            return;
        }
        try {
            Document document = DocumentBuilder
                    .toDocument(iSchema, super.core.getLatestSchema());

            RefCounted<IndexWriter> iw = super.solrCoreState.getIndexWriter(super.core);
            IndexWriter indexWriter = iw.get();
            indexWriter.addDocument(document);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void init() {

    }

    @Override
    public void inform(SolrCore core) {

    }

    @Override
    public void handleRequestBody() {

    }

}
