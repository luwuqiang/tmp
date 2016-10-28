package com.leederedu.qsearch.core.cfg;

import com.leederedu.qsearch.analysis.lucene.IKAnalyzer;
import com.leederedu.qsearch.core.DirectoryFactory;
import com.leederedu.qsearch.core.SolrCore;
import org.apache.lucene.index.IndexWriterConfig;

/**
 * Created by liuwuqiang on 2016/10/26.
 */
public class SolrIndexConfig {

    public String lockType;

    private SolrIndexConfig(SolrConfig solrConfig) {

        lockType = DirectoryFactory.LOCK_TYPE_NATIVE;
    }

    public IndexWriterConfig toIndexWriterConfig(SolrCore core) {
        IndexSchema schema = core.getLatestSchema();
        IndexWriterConfig iwc = new IndexWriterConfig(new DelayedSchemaAnalyzer(core));

        //todo

        return iwc;
    }

    private static class DelayedSchemaAnalyzer extends IKAnalyzer {
        private final SolrCore core;

        public DelayedSchemaAnalyzer(SolrCore core) {
            this.core = core;
        }
    }
}
