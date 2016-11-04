package com.leederedu.qsearch.handler;

import com.leederedu.qsearch.core.QSUtils;
import com.leederedu.qsearch.core.schema.ISchema;
import com.leederedu.qsearch.core.schema.SimpleSchema;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * Created by liuwuqiang on 2016/11/3.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class UpdateHandlerTest {

    @Test
    public void deleteDoc() throws Exception {

    }

    @Test
    public void deleteDocs() throws Exception {

    }

    @Test
    public void addDoc2() throws Exception {

    }

    @Test
    public void updateDoc() throws Exception {

    }

    @Test
    public void deleteAll() throws Exception {
        System.out.println(SimpleSchema.class.isAssignableFrom(ISchema.class));
    }

    @Test
    public void addDoc() throws Exception {
        SimpleSchema simpleSchema = new SimpleSchema();
        simpleSchema.setId(10001);
        simpleSchema.setText("IK Analyzer是一个结合词典分词和文法分词的中文分词开源工具包。它使用了全新的正向迭代最细粒度切分算法。");
        QSUtils.updateHandler().addDoc(simpleSchema);
    }

}