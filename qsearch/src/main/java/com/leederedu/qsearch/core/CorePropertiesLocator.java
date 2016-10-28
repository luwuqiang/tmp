package com.leederedu.qsearch.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.nio.file.Path;
import java.util.List;

/**
 * Created by liuwuqiang on 2016/10/26.
 */
public class CorePropertiesLocator implements CoresLocator {

    public static final String PROPERTIES_FILENAME = "core.properties";
    private static final Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final Path rootDirectory;

    public CorePropertiesLocator(Path coreDiscoveryRoot) {
        this.rootDirectory = coreDiscoveryRoot;
        logger.info("Config-defined core root directory: {}", this.rootDirectory);
    }

    @Override
    public void create(CoreContainer cc, CoreDescriptor... coreDescriptors) {

    }

    @Override
    public void persist(CoreContainer cc, CoreDescriptor... coreDescriptors) {

    }

    @Override
    public void delete(CoreContainer cc, CoreDescriptor... coreDescriptors) {

    }

    @Override
    public void rename(CoreContainer cc, CoreDescriptor oldCD, CoreDescriptor newCD) {

    }

    @Override
    public void swap(CoreContainer cc, CoreDescriptor cd1, CoreDescriptor cd2) {

    }

    @Override
    public List<CoreDescriptor> discover(CoreContainer cc) {
        return null;
    }
}
