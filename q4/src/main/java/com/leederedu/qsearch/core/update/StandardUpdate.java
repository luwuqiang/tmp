package com.leederedu.qsearch.core.update;

import com.leederedu.qsearch.core.SolrCore;
import com.leederedu.qsearch.core.schema.ISchema;
import com.leederedu.qsearch.utils.DocumentBuilder;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;

import java.io.IOException;
import java.util.List;


/**
 * Created by liuwuqiang on 2016/10/27.
 */
public class StandardUpdate extends UpdateBase {

    public StandardUpdate(SolrCore solrCore) {
        super(solrCore);
    }

    @Override
    public boolean deleteAll() {
        try {
            super.getIndexWriter().deleteAll();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public void addDoc(ISchema iSchema) {
        if (iSchema == null) {
            return;
        }
        try {
            Document document = DocumentBuilder.toDocument(
                    iSchema, super.core.getLatestSchema());

            super.getIndexWriter().addDocument(document);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean deleteDoc(Object id) {
        try {
            Term delTerm = DocumentBuilder.newIdTerm(
                    id, super.core.getLatestSchema());

            super.getIndexWriter().deleteDocuments(delTerm);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean deleteDocs(Object[] ids) {
        for (Object id : ids) {
            deleteDoc(id);
        }
        return false;
    }

    @Override
    public void addDoc(List<ISchema> list) {
        for (ISchema iSchema : list) {
            addDoc(iSchema);
        }
    }

    @Override
    public void updateDoc(ISchema iSchema) {
        try {
            Term delTerm = DocumentBuilder.newIdTerm(
                    iSchema, super.core.getLatestSchema());

            Document document = DocumentBuilder.toDocument(
                    iSchema, super.core.getLatestSchema());

            super.getIndexWriter().updateDocument(delTerm, document);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
