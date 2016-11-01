package com.leederedu.qsearch.core;

import org.apache.lucene.store.Directory;
import org.apache.lucene.store.LockFactory;

import java.io.IOException;

/**
 * Created by liuwuqiang on 2016/10/26.
 */
public abstract class DirectoryFactory {
    // Available lock types
    public final static String LOCK_TYPE_SIMPLE = "simple";
    public final static String LOCK_TYPE_NATIVE = "native";
    public final static String LOCK_TYPE_SINGLE = "single";
    public final static String LOCK_TYPE_NONE = "none";

    // hint about what the directory contains - default is index directory
    public enum DirContext {
        DEFAULT, META_DATA
    }

    /**
     * Returns the Directory for a given path, using the specified rawLockType.
     * Will return the same Directory instance for the same path.
     *
     * @throws IOException If there is a low-level I/O error.
     */
    public abstract Directory get(String path, DirContext dirContext, String rawLockType)
            throws IOException;

    /**
     * Indicates a Directory will no longer be used, and when its ref count
     * hits 0, it can be closed. On close all directories will be closed
     * whether this has been called or not. This is simply to allow early cleanup.
     *
     * @throws IOException If there is a low-level I/O error.
     */
    public abstract void doneWithDirectory(Directory directory) throws IOException;

    /**
     * Releases the Directory so that it may be closed when it is no longer
     * referenced.
     *
     * @throws IOException If there is a low-level I/O error.
     */
    public abstract void release(Directory directory) throws IOException;

    /**
     * Returns true if a Directory exists for a given path.
     * @throws IOException If there is a low-level I/O error.
     *
     */
    public abstract boolean exists(String path) throws IOException;

    /**
     * Normalize a given path.
     *
     * @param path to normalize
     * @return normalized path
     * @throws IOException on io error
     */
    public String normalize(String path) throws IOException {
        return path;
    }

    /**
     * Creates a new LockFactory for a given path.
     * @param rawLockType A string value as passed in config. Every factory should at least support 'none' to disable locking.
     * @throws IOException If there is a low-level I/O error.
     */
    protected abstract LockFactory createLockFactory(String rawLockType) throws IOException;

    /**
     * Creates a new Directory for a given path.
     *
     * @throws IOException If there is a low-level I/O error.
     */
    protected abstract Directory create(String path, LockFactory lockFactory, DirContext dirContext) throws IOException;
}
