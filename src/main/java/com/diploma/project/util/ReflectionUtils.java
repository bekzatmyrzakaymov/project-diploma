package com.diploma.project.util;

public class ReflectionUtils {

    public <E> E createInstance(Class clazz) {
        E t = null;
        try {
            t = (E) clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return t;
    }

    public String getClassName(Class clazz) {
        return clazz.getSimpleName();
    }
}
