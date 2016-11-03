package com.leederedu.qsearch.core.cfg;

import com.leederedu.qsearch.utils.NumberUtils;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

/**
 * Created by liuwuqiang on 2016/10/25.
 */
public class SolrConfig {

    public final Properties pro;
    private Path coreRootDirectory;
    private int coreLoadThreadCount;
    public SolrIndexConfig indexConfig;

    public SolrConfig(String properties) throws IOException {
        this.pro = this.loadProperties(properties);

    }

    public void init() {
        this.indexConfig = new SolrIndexConfig(this.pro);
        this.coreRootDirectory = Paths.get(this.pro.getProperty("coreRootDirectory"), "/");
        this.coreLoadThreadCount = NumberUtils.toInt(this.pro.get("coreLoadThreadCount"), 3);
    }

    private Properties loadProperties(String properties) {
        Properties p = new Properties();
        try {
            InputStream is = this.getClass()
                    .getClassLoader().getResourceAsStream(properties);
            p.load(is);
        } catch (Exception e) {
        }
        return p;
    }

    public Path getCoreRootDirectory() {
        return coreRootDirectory;
    }

    public int getCoreLoadThreadCount() {
        return coreLoadThreadCount;
    }
}
