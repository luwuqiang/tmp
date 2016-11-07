package com.leederedu.qsearch.core.schema;

/**
 * Created by liuwuqiang on 2016/11/2.
 */
public class SimpleSchema implements ISchema {

    @Id
    private long id;

    @IndexField(request = true, stored = true)
    private String text;

    @IndexField(request = false)
    private float boost = 1.0f;

    public long getId() {
        return id;
    }

    public SimpleSchema setId(long id) {
        this.id = id;
        return this;
    }

    public String getText() {
        return text;
    }

    public SimpleSchema setText(String text) {
        this.text = text;
        return this;
    }

    public float getBoost() {
        return boost;
    }

    public SimpleSchema setBoost(float boost) {
        this.boost = boost;
        return this;
    }
}
