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
package com.leederedu.qsearch.core;

import java.io.File;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.Locale;

import com.leederedu.qsearch.core.common.SolrException;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.LockFactory;
import org.apache.lucene.store.NRTCachingDirectory;
import org.apache.lucene.store.NativeFSLockFactory;
import org.apache.lucene.store.NoLockFactory;
import org.apache.lucene.store.SimpleFSLockFactory;
import org.apache.lucene.store.SingleInstanceLockFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Directory provider which mimics original Solr
 * {@link FSDirectory} based behavior.
 * <p>
 * File based DirectoryFactory implementations generally extend
 * this class.
 */
public class StandardDirectoryFactory extends CachingDirectoryFactory {

    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    public boolean isPersistent() {
        return true;
    }

    // special hack to work with NRTCachingDirectory
    private Directory getBaseDir(Directory dir) {
        Directory baseDir;
        if (dir instanceof NRTCachingDirectory) {
            baseDir = ((NRTCachingDirectory) dir).getDelegate();
        } else {
            baseDir = dir;
        }

        return baseDir;
    }

    @Override
    public void doneWithDirectory(Directory directory) throws IOException {

    }

    @Override
    public void release(Directory directory) throws IOException {

    }

    @Override
    public boolean exists(String path) throws IOException {
        // we go by the persistent storage ...
        File dirFile = new File(path);
        return dirFile.canRead() && dirFile.list().length > 0;
    }

    @Override
    public String normalize(String path) throws IOException {
        String cpath = new File(path).getCanonicalPath();

        return super.normalize(cpath);
    }

    @Override
    protected LockFactory createLockFactory(String rawLockType) throws IOException {
        if (null == rawLockType) {
            rawLockType = DirectoryFactory.LOCK_TYPE_NATIVE;
            log.warn("No lockType configured, assuming '" + rawLockType + "'.");
        }
        final String lockType = rawLockType.toLowerCase(Locale.ROOT).trim();
        switch (lockType) {
            case DirectoryFactory.LOCK_TYPE_SIMPLE:
                return SimpleFSLockFactory.INSTANCE;
            case DirectoryFactory.LOCK_TYPE_NATIVE:
                return NativeFSLockFactory.INSTANCE;
            case DirectoryFactory.LOCK_TYPE_SINGLE:
                return new SingleInstanceLockFactory();
            case DirectoryFactory.LOCK_TYPE_NONE:
                return NoLockFactory.INSTANCE;
            default:
                throw new SolrException(SolrException.ErrorCode.SERVER_ERROR,
                        "Unrecognized lockType: " + rawLockType);
        }
    }

    @Override
    protected Directory create(String path, LockFactory lockFactory, DirContext dirContext) throws IOException {
        // we pass NoLockFactory, because the real lock factory is set later by injectLockFactory:
        return FSDirectory.open(new File(path).toPath(), lockFactory);
    }
}
