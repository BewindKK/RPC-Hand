package com.bewind.rpc.utils;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

/**
 * 反射工具类
 */
public class ReflectionUtils {

    /**
     * 根据class来创建对象
     * @param clazz 待创建对象
     * @param <T> 对象类型
     * @return  创建好的对象
     */
    public static <T> T newInstance(Class<T> clazz){
        try {
            return clazz.newInstance();
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }

    /**
     * 获取某个class的公共方法
     * @param clazz
     * @return
     */
    public static Method[] getPublicMethods(Class clazz) {
        Method[] methods = clazz.getDeclaredMethods();
        List<Method> pMethods = new ArrayList<Method>();
        //过滤出公共方法
        for (Method method : methods) {
            if (Modifier.isPublic(method.getModifiers())) {
                pMethods.add(method);
            }
        }
        return pMethods.toArray(new Method[0]);
    }

    /**
     * 调用指定对象的指定方法
     * @param obj 被调用方法的对象
     * @param method 被调用的方法
     * @param args 方法的参数
     * @return  返回结果
     */
    public static Object invoke(Object obj, Method method, Object... args) {
        try {
            return method.invoke(obj, args);
        }  catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }


}
