package ru.chemarev.andrey.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.chemarev.andrey.annotation.After;
import ru.chemarev.andrey.annotation.Before;
import ru.chemarev.andrey.annotation.Test;

public class TestClass1 {

    @Before
    public void setStringBeforeTest() {
        logger.trace("Call setStringBeforeTest()");
    }

    @Test
    public void checkLowerCase() {
        logger.trace("Call checkLowerCase()");
    }

    @Test
    public void checkUpperCase() {
        logger.trace("Call checkUpperCase()");
    }

    @After
    public void setStringAfterTest() {
        logger.trace("Call setStringAfterTest()");
    }

    private Logger logger = LoggerFactory.getLogger(TestClass1.class);
}
