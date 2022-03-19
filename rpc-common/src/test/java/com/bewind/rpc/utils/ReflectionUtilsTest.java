package com.bewind.rpc.utils;

import org.junit.Test;

import java.lang.reflect.Method;

import static org.junit.Assert.*;

public class ReflectionUtilsTest {

    @Test
    public void newInstance() {
       TestClass t= ReflectionUtils.newInstance(TestClass.class);
       assertNotNull(t);
    }

    @Test
    public void getPublicMethods() {
        Method[] publicMethods = ReflectionUtils.getPublicMethods(TestClass.class);
        assertEquals(1,publicMethods.length);
        String name = publicMethods[0].getName();
        assertEquals("c",name);
    }

    @Test
    public void invoke() {
        Method[] publicMethods = ReflectionUtils.getPublicMethods(TestClass.class);
        Method c=publicMethods[0];
        TestClass testClass = new TestClass();
        Object invoke = ReflectionUtils.invoke(testClass, c);
        assertEquals("c",invoke);
    }
}
