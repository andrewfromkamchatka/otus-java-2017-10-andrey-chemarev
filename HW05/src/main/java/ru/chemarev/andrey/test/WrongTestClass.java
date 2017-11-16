package ru.chemarev.andrey.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.chemarev.andrey.annotation.After;
import ru.chemarev.andrey.annotation.Before;
import ru.chemarev.andrey.annotation.Test;

public class WrongTestClass {

    private WrongTestClass() {
    }

    @Before
    public void before() {
        logger.trace("Call before()");
    }

    @Test
    @After
    public void test1() {
        logger.trace("Call test1()");
    }

    @After
    public void test2() {
        logger.trace("Call test2()");
    }

    @After
    public void after() {
        logger.trace("Call after()");
    }

    private Logger logger = LoggerFactory.getLogger(WrongTestClass.class);
}
