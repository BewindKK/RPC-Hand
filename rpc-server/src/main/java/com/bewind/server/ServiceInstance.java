package com.bewind.server;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.lang.reflect.Method;

/**
 * 表示一个具体服务
 */
@Data
@AllArgsConstructor
public class ServiceInstance {
    //这个服务由那个对象提供
    private Object target;
    //对象的某个方法
    private Method method;
}
