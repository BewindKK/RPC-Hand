package com.bewind.server;

import com.bewind.rpc.Request;
import com.bewind.rpc.utils.ReflectionUtils;

/**
 * 调用service示例的类
 * 调用具体的服务
 */
public class ServiceInvoker {

    public Object invoke(ServiceInstance serviceInstance, Request request){
        return ReflectionUtils.invoke(
                serviceInstance.getTarget(),
                serviceInstance.getMethod(),
                request.getParameters()
        );
    }
}
