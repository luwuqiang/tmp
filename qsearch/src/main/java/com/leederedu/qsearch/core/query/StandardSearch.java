package com.leederedu.qsearch.core.query;

import com.leederedu.qsearch.core.SolrCore;
import com.leederedu.qsearch.handler.component.HighlightComponent;
import com.leederedu.qsearch.handler.component.SearchComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by liuwuqiang on 2016/10/25.
 */
public class StandardSearch extends SearchBase {
    private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    static final String INIT_COMPONENTS = "components";
    protected volatile List<SearchComponent> components;
    private List<String> componentsName;
    private SolrCore core;

    protected List<String> getDefaultComponents() {
        ArrayList<String> names = new ArrayList<>(1);
        names.add(HighlightComponent.COMPONENT_NAME);
        return names;
    }

    private void initComponents() {
        if (this.componentsName == null || this.componentsName.isEmpty()) {
            // TODO: 2016/10/28
        }
        // Build the component list
        List<SearchComponent> components = new ArrayList<>(componentsName.size());
        for (String cn : componentsName) {
            SearchComponent comp = core.getSearchComponent(cn);
            components.add(comp);
            log.debug("Adding  component:" + comp);
        }
        this.components = components;
    }

    @Override
    public void init(List<String> argsList) {
        this.componentsName = argsList;
    }

    @Override
    public void inform(SolrCore core) {
        this.core = core;
    }

    @Override
    public void handleBody() {

        log.info("excute handleBody..");
    }

    @Override
    public boolean queryForList() {

        log.info("queryForList..");
        return false;
    }

    @Override
    public Object queryForObject() {
        return null;
    }

    public List<SearchComponent> getComponents() {
        List<SearchComponent> result = components;  // volatile read
        if (result == null) {
            synchronized (this) {
                if (components == null) {
                    initComponents();
                }
                result = components;
            }
        }
        return result;
    }
}
