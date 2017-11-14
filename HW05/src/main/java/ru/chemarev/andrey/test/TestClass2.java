package ru.chemarev.andrey.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.chemarev.andrey.annotation.Test;

public class TestClass2 {

    @Test
    public void testSomething() {
        logger.trace("Call testSomething()");
    }

    private Logger logger = LoggerFactory.getLogger(TestClass2.class);
}
