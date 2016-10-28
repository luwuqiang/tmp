package com.leederedu.qsearch.core.cfg;

import org.apache.lucene.analysis.util.ResourceLoader;
import org.apache.lucene.util.IOUtils;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLClassLoader;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by liuwuqiang on 2016/10/24.
 */
public class QSearchResourceLoader implements ResourceLoader, Closeable {

    public static String LOCATE_QSEARCH_HOME = "qsearch_index";
    protected URLClassLoader classLoader;

    @Override
    public InputStream openResource(String resource) throws IOException {
        return null;
    }

    @Override
    public <T> Class<? extends T> findClass(String cname, Class<T> expectedType) {
        return null;
    }

    @Override
    public <T> T newInstance(String cname, Class<T> expectedType) {
        return null;
    }

    @Override
    public void close() throws IOException {
        IOUtils.close(classLoader);
    }

    public static Path locateSolrHome() {
        return Paths.get(QSearchResourceLoader.LOCATE_QSEARCH_HOME);
    }
}
