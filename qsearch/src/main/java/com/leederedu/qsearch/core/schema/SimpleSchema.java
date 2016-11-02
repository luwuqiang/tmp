package com.leederedu.qsearch.core.schema;

/**
 * Created by liuwuqiang on 2016/11/2.
 */
public class SimpleSchema implements ISchema{

    @Id
    private long id;

    @Field(request = true, stored = true)
    private String title;

    @Field(request = false)
    private int sortNum;

    public int getSortNum() {
        return sortNum;
    }

    public SimpleSchema setSortNum(int sortNum) {
        this.sortNum = sortNum;
        return this;
    }

    public long getId() {
        return id;
    }

    public SimpleSchema setId(long id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public SimpleSchema setTitle(String title) {
        this.title = title;
        return this;
    }
}
