/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.leederedu.qsearch.core.update;

import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.concurrent.atomic.AtomicLong;

import com.leederedu.qsearch.analysis.lucene.IKAnalyzer;
import com.leederedu.qsearch.core.DirectoryFactory;
import com.leederedu.qsearch.core.IndexSchema;
import com.leederedu.qsearch.core.SolrCore;
import com.leederedu.qsearch.utils.IOUtils;
import org.apache.lucene.codecs.Codec;
import org.apache.lucene.index.IndexDeletionPolicy;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.util.InfoStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * An IndexWriter that is configured via Solr config mechanisms.
 *
 * @since solr 0.9
 */

public class SolrIndexWriter extends IndexWriter {
    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    // These should *only* be used for debugging or monitoring purposes
    public static final AtomicLong numOpens = new AtomicLong();
    public static final AtomicLong numCloses = new AtomicLong();

    private final Object CLOSE_LOCK = new Object();

    String name;
    private DirectoryFactory directoryFactory;
    private InfoStream infoStream;
    private Directory directory;

    public static SolrIndexWriter create(SolrCore core, String name, String path,
                                         DirectoryFactory directoryFactory, boolean create,
                                         IndexSchema schema, IndexDeletionPolicy delPolicy, Codec codec) throws IOException {
        SolrIndexWriter w = null;
        final Directory d = directoryFactory.get(path,
                DirectoryFactory.DirContext.DEFAULT, DirectoryFactory.LOCK_TYPE_NATIVE);
        try {
            IKAnalyzer analyzer = new IKAnalyzer(true);
            IndexWriterConfig iwconf = new IndexWriterConfig(analyzer);
            iwconf.setOpenMode(create ? IndexWriterConfig.OpenMode.CREATE : IndexWriterConfig.OpenMode.APPEND).
                    setIndexDeletionPolicy(delPolicy).setCodec(codec);

            w = new SolrIndexWriter(core, name, d, iwconf);
            w.setDirectoryFactory(directoryFactory);
            return w;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            if (null == w && null != d) {
                directoryFactory.doneWithDirectory(d);
                directoryFactory.release(d);
            }
        }
    }

    private SolrIndexWriter(SolrCore core, String name, Directory directory, IndexWriterConfig iwconf) throws IOException {
        super(directory, iwconf);
        log.debug("Opened Writer " + name);
        this.name = name;
        infoStream = getConfig().getInfoStream();
        this.directory = directory;
        numOpens.incrementAndGet();
    }

    private void setDirectoryFactory(DirectoryFactory factory) {
        this.directoryFactory = factory;
    }

    private volatile boolean isClosed = false;

    @Override
    public void close() throws IOException {
        log.debug("Closing Writer " + name);
        try {
            super.close();
        } catch (Throwable t) {
            if (t instanceof OutOfMemoryError) {
                throw (OutOfMemoryError) t;
            }
            log.error("Error closing IndexWriter", t);
        } finally {
            cleanup();
        }
    }

    @Override
    public void rollback() throws IOException {
        log.debug("Rollback Writer " + name);
        try {
            super.rollback();
        } catch (Throwable t) {
            if (t instanceof OutOfMemoryError) {
                throw (OutOfMemoryError) t;
            }
            log.error("Exception rolling back IndexWriter", t);
        } finally {
            cleanup();
        }
    }

    private void cleanup() throws IOException {
        // It's kind of an implementation detail whether
        // or not IndexWriter#close calls rollback, so
        // we assume it may or may not
        boolean doClose = false;
        synchronized (CLOSE_LOCK) {
            if (!isClosed) {
                doClose = true;
                isClosed = true;
            }
        }
        if (doClose) {

            if (infoStream != null) {
                IOUtils.closeQuietly(infoStream);
            }
            numCloses.incrementAndGet();
            directoryFactory.release(directory);
        }
    }

    @Override
    protected void finalize() throws Throwable {
        try {
            if (!isClosed) {
                assert false : "SolrIndexWriter was not closed prior to finalize()";
                log.error("SolrIndexWriter was not closed prior to finalize(), indicates a bug -- POSSIBLE RESOURCE LEAK!!!");
                close();
            }
        } finally {
            super.finalize();
        }

    }
}