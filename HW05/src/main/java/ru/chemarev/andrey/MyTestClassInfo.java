package ru.chemarev.andrey;

import ru.chemarev.andrey.annotation.After;
import ru.chemarev.andrey.annotation.Before;
import ru.chemarev.andrey.annotation.Test;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public class MyTestClassInfo {

    private MyTestClassInfo(Class<?> infoClass) {
        this.infoClass = infoClass;
        tests = new ArrayList<>();
        errors = new ArrayList<>();
    }

    public static MyTestClassInfo analyze(Class<?> clazz) {
        MyTestClassInfo result = new MyTestClassInfo(clazz);
        analyzeConstructors(clazz, result);
        analyzeMethods(clazz, result);
        return result;
    }

    private static void analyzeConstructors(Class<?> clazz, MyTestClassInfo info) {
        try {
            Constructor defaultConstructor = clazz.getDeclaredConstructor();
            if ( ! Modifier.isPublic( defaultConstructor.getModifiers() ) ) {
                info.addError( new Exception( String.format("%s must have default public constructor", clazz.getName() ) ));
            }
        } catch (NoSuchMethodException e) {
            info.addError(e);
        }
    }

    private static void analyzeMethods(Class<?> clazz, MyTestClassInfo info) {
        for (Method method : clazz.getDeclaredMethods()) {

            boolean isAnnotationProcessed = false;

            for ( Annotation annotation : method.getDeclaredAnnotations() ) {
                Class type = annotation.annotationType();

                if ( type == Before.class || type == After.class || type == Test.class ) {
                    if ( isAnnotationProcessed ) {
                        info.addError(new Exception(String.format("%s @Before, @After, @Test cannot crossed", clazz.getName())));
                    } else {
                        analyzeAnnotation(type, method, info);
                        isAnnotationProcessed = true;
                    }
                }
            }
        }
    }

    private static void analyzeAnnotation(Class<?> type, Method method, MyTestClassInfo info) {
        if ( !Modifier.isPublic(method.getModifiers()) ) {
            info.addError(new Exception(String.format("Method %s() must be public", method.getName())));
            return;
        }

        if (type == Before.class && info.getBefore() == null)
            info.setBefore(method);
        else if (type == After.class && info.getAfter() == null)
            info.setAfter(method);
        else if (type == Test.class)
            info.addTest(method);
        else
            info.addError(new Exception(String.format("%s %s more then one",
                    info.getInfoClass().getName(), "@" + type.getSimpleName())));

    }

    public Class getInfoClass() {
        return infoClass;
    }

    public Method getBefore() {
        return before;
    }

    private void setBefore(Method m) {
        before = m;
    }

    public Method getAfter() {
        return after;
    }

    private void setAfter(Method m) {
        after = m;
    }

    private boolean addError(Exception e) {
        return errors.add(e);
    }

    public boolean hasErrors() {
        return errors.size() > 0;
    }

    public List<Exception> getErrors() {
        return errors;
    }

    private boolean addTest(Method m) {
        return tests.add(m);
    }

    public List<Method> getTests() {
        return tests;
    }

    private Class infoClass;
    private Method before;
    private Method after;
    private List<Method> tests;
    private List<Exception> errors;
}
