package ru.chemarev.andrey;

import com.google.common.reflect.ClassPath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class MyTestFramework {

    private static List<Exception> performTest(Class<?> clazz, Method test, Method before, Method after) {
        List<Exception> result = new ArrayList<>();

        Object object;
        try {
            object = clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            result.add(e);
            return result;
        }

        boolean isTestPossible = true;
        if ( before != null )
            isTestPossible = performTestMethod(object, before, result);

        if ( isTestPossible )
            performTestMethod(object, test, result);

        if ( after != null )
            performTestMethod(object, after, result);

        return result;
    }

    private static boolean performTestMethod(Object object, Method method, List<Exception> exceptions) {
        try {
            method.invoke(object);
        } catch (IllegalAccessException | InvocationTargetException e) {
            exceptions.add(e);
            return false;
        }

        return true;
    }

    public static void testClass(String className) throws ClassNotFoundException {
        Class clazz = Class.forName(className);

        MyTestClassInfo info = MyTestClassInfo.analyze(clazz);

        if ( info.hasErrors() ) {
            logger.info("{} has syntax errors", clazz.getName());
            for ( Exception e : info.getErrors() )
                logger.error( e.getMessage() );
        } else {
            for (Method test : info.getTests()) {
                List<Exception> testErrors = performTest(clazz, test, info.getBefore(), info.getAfter());

                if ( testErrors.size() == 0 )
                    logger.info("Test {} passed successfully", test.getName());
                else {
                    logger.info("Test {} failed", test.getName());
                    for (Exception e : testErrors) {
                        if ( e.getClass() == InvocationTargetException.class && e.getCause() != null && e.getCause().getStackTrace().length > 1 )
                            logger.error("{} {} ", e.getCause().getClass().getName(), e.getCause().getStackTrace()[1]);
                        else
                            logger.error("{} {} ", e.getClass().getName(), e.getMessage());
                    }
                }
            }
        }
    }

    public static void testPackage(String packageName) throws IOException, ClassNotFoundException {
        ClassPath classPath = ClassPath.from(ClassLoader.getSystemClassLoader());
        for (ClassPath.ClassInfo classInfo : classPath.getTopLevelClasses(packageName)) {
            testClass(classInfo.getName());
        }
    }

    private static Logger logger = LoggerFactory.getLogger(MyTestFramework.class);
}
