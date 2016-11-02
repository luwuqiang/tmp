package com.leederedu.qsearch.core.cfg;

import com.leederedu.qsearch.analysis.lucene.IKAnalyzer;
import com.leederedu.qsearch.core.DirectoryFactory;
import com.leederedu.qsearch.core.SolrCore;
import org.apache.lucene.index.IndexWriterConfig;

import java.util.Properties;

/**
 * Created by liuwuqiang on 2016/10/26.
 */
public class SolrIndexConfig {

    public String lockType;

    public SolrIndexConfig(Properties solrConfig) {

        lockType = DirectoryFactory.LOCK_TYPE_NATIVE;
    }

    public IndexWriterConfig toIndexWriterConfig(SolrCore core) {
//        IndexSchema schema = core.getLatestSchema();
        System.out.println("--begin2----");
        IndexWriterConfig iwconf =null;
        try {
            IKAnalyzer analyzer = new IKAnalyzer(true);
             iwconf = new IndexWriterConfig(analyzer);
        }catch (Exception e){
            System.out.println(e);
        }
        System.out.println("--end----");
        //todo
        System.out.println(iwconf);
        return iwconf;
    }

    private static class DelayedSchemaAnalyzer extends IKAnalyzer {
        private final SolrCore core;

        public DelayedSchemaAnalyzer(SolrCore core) {
            super(true);
            this.core = core;
        }
    }
}
