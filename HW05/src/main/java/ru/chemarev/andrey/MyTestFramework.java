package ru.chemarev.andrey;

import com.google.common.reflect.ClassPath;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.chemarev.andrey.annotation.After;
import ru.chemarev.andrey.annotation.Before;
import ru.chemarev.andrey.annotation.Test;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

public class MyTestFramework {

    public static void testClass(String className) throws ClassNotFoundException, IllegalAccessException, InstantiationException, InvocationTargetException {
        Class clazz = Class.forName(className);

        Method after = null;
        Method before = null;
        ArrayList<Method> tests = new ArrayList<>();

        for (Method method : clazz.getDeclaredMethods()) {
            for (Annotation annotation : method.getDeclaredAnnotations()) {

                if (annotation.annotationType() == After.class) {
                    if (after == null)
                        after = method;
                    else
                        throw new UnsupportedOperationException(String.format("%s @After more then one", className));
                }

                else if (annotation.annotationType() == Before.class) {
                    if (before == null)
                        before = method;
                    else
                        throw new UnsupportedOperationException(String.format("%s @Before more then one", className));
                }

                else if (annotation.annotationType() == Test.class) {
                    tests.add(method);
                }

            }
        }

        for (Method test : tests) {
            logger.trace("Performing {}()", test.getName());
            Object obj = clazz.newInstance();

            if (after != null)
                after.invoke(obj);

            test.invoke(obj);

            if (before != null)
                before.invoke(obj);

            logger.trace("{}() finished\n", test.getName());
        }

    }

    public static void testPackage(String packageName) throws IOException, ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException {
        ClassPath classPath = ClassPath.from(ClassLoader.getSystemClassLoader());
        for (ClassPath.ClassInfo classInfo : classPath.getTopLevelClasses(packageName)) {
            testClass(classInfo.getName());
        }
    }

    private static Logger logger = LoggerFactory.getLogger(MyTestFramework.class);
}
