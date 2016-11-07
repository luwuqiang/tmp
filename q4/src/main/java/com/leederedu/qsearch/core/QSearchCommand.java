package com.leederedu.qsearch.core;

import com.leederedu.qsearch.core.common.SolrException;
import org.apache.lucene.index.Term;
import org.apache.lucene.util.BytesRef;
import org.apache.lucene.util.BytesRefBuilder;

/**
 * Created by liuwuqiang on 2016/11/1.
 */
public class QSearchCommand{

    public Term updateTerm;
    private BytesRef indexedId;
    private boolean block;

    /** Returns the indexed ID for this document.  The returned BytesRef is retained across multiple calls, and should not be modified. */
    public BytesRef getIndexedId() {
        if (indexedId == null) {
            // TODO: 2016/11/1 获取文档id的值
        }
        return indexedId;
    }

    public boolean isBlock() {
        return block;
    }
}
