package ru.chemarev.andrey.test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NoTestClass {

    public void noTestMethod() {
        logger.trace("Call noTestMethod()");
    }

    private Logger logger = LoggerFactory.getLogger(NoTestClass.class);
}
