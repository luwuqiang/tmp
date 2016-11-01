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

import org.apache.lucene.store.Directory;

import java.io.IOException;

public abstract class CachingDirectoryFactory extends DirectoryFactory {


    /*
     * (non-Javadoc)
     *
     * @see
     * org.apache.solr.core.DirectoryFactory#release(org.apache.lucene.store.Directory
     * )
     */
    @Override
    public void release(Directory directory) throws IOException {
        if (directory == null) {
            throw new NullPointerException();
        }
        synchronized (this) {
            // don't check if already closed here - we need to able to release
            // while #close() waits.
            //todo
        }
    }

    @Override
    public Directory get(String path, DirContext dirContext, String rawLockType) throws IOException {
        String fullPath = normalize(path);
        Directory directory = create(fullPath, createLockFactory(rawLockType), dirContext);
        return directory;
    }
}
