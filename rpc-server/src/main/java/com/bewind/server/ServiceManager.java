package com.bewind.server;

import com.bewind.rpc.Request;
import com.bewind.rpc.ServiceDescriptor;
import com.bewind.rpc.utils.ReflectionUtils;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 管理rpc暴露的服务
 */
@Slf4j
public class ServiceManager {
    //注册服务、查找服务
    private Map<ServiceDescriptor,ServiceInstance> services;

    public ServiceManager(){
        this.services=new ConcurrentHashMap<>();
    }

    /**
     *  所有服务注册到serviceManager中
     * @param interfaceClass 接口class
     * @param bean 服务具体提供者,接口具体实现的一个对象
     */
    public <T> void register(Class<T> interfaceClass,T bean){
        //需要把接口中的所有方法扫描出来，和bean绑定起来形成一个ServiceInstance放到Map中
        Method[] publicMethods = ReflectionUtils.getPublicMethods(interfaceClass);
        for (Method method:publicMethods){
            ServiceInstance serviceInstance = new ServiceInstance(bean, method);
            ServiceDescriptor from = ServiceDescriptor.from(interfaceClass, method);
            services.put(from,serviceInstance);
            log.info("register service:{} {}" ,from.getClazz(),serviceInstance.getMethod());
        }
    }

    //查找
    public ServiceInstance lookup(Request request){
        ServiceDescriptor service = request.getService();
        return services.get(service);//get的时候是用equals方法判断，应该重写ServiceDescriptor
                                    //的equals方法
    }
}
