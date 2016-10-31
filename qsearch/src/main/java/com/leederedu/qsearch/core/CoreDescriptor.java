package com.leederedu.qsearch.core;


import com.leederedu.qsearch.utils.PropertiesUtil;

import java.nio.file.Paths;
import java.util.Properties;

import java.nio.file.Path;

/**
 * Created by liuwuqiang on 2016/10/26.
 */
public class CoreDescriptor {

    // Properties file name constants
    public static final String CORE_NAME = "name";
    public static final String CORE_CONFIG = "config";
    public static final String CORE_TRANSIENT = "transient";
    public static final String CORE_LOADONSTARTUP = "loadOnStartup";

    private final Path instanceDir;
    /**
     * The properties for this core, as available through getProperty()
     */
    protected final Properties coreProperties = new Properties();

    /**
     * Create a new CoreDescriptor using the properties of an existing one
     *
     * @param coreName the new CoreDescriptor's name
     * @param p    the CoreDescriptor to copy
     */
    public CoreDescriptor(String coreName, Properties p) {
        this.instanceDir = Paths.get(p.getProperty("instanceDir", "/"));
        this.coreProperties.putAll(p);
        this.coreProperties.setProperty(CORE_NAME, coreName);
    }

    public String getName() {
        return coreProperties.getProperty(CORE_NAME);
    }

    /**
     * @return the core instance directory
     */
    public Path getInstanceDir() {
        return instanceDir;
    }

    public boolean isTransient() {
        String tmp = coreProperties.getProperty(CORE_TRANSIENT, "false");
        return PropertiesUtil.toBoolean(tmp);
    }

    public boolean isLoadOnStartup() {
        String tmp = coreProperties.getProperty(CORE_LOADONSTARTUP, "false");
        return Boolean.parseBoolean(tmp);
    }
}
