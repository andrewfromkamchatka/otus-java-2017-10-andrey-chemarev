package ru.chemarev.andrey.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.chemarev.andrey.MyAsserts;
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
        String test = "asdaDsd1";
        MyAsserts.assertTrue( test.matches("[a-z]+") );
    }

    @Test
    public void checkUpperCase() {
        logger.trace("Call checkLowerCase()");
        String test = "QWERTY";
        MyAsserts.assertTrue( test.matches("[A-Z]+") );
    }

    @Test
    public void checkEqualsString() {
        logger.trace("Call checkLowerCase()");
        MyAsserts.assertEquals( "abc", "ABC" );
    }

    @After
    public void setStringAfterTest() {
        logger.trace("Call setStringAfterTest()");
    }

    private Logger logger = LoggerFactory.getLogger(TestClass1.class);
}
