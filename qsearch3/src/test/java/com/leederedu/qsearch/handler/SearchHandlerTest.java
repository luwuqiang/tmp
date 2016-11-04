package com.leederedu.qsearch.handler;

import com.leederedu.qsearch.core.QSUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * Created by liuwuqiang on 2016/11/4.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:applicationContext.xml")
public class SearchHandlerTest {
    @Test
    public void queryForList() throws Exception {

    }

    @Test
    public void queryForObject() throws Exception {

        QSUtils.searchHandler().queryForObject(10001);
    }
}