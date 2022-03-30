package com.bewind.server;

import com.bewind.rpc.Request;
import com.bewind.rpc.ServiceDescriptor;
import com.bewind.rpc.utils.ReflectionUtils;
import org.junit.Before;
import org.junit.Test;

import java.lang.reflect.Method;

import static org.junit.Assert.*;

public class ServiceManagerTest {
    ServiceManager sm;

    @Before
    public void init(){
        sm=new ServiceManager();

        TestInterface bean=new TestClass();
        sm.register(TestInterface.class,bean);
    }
    @Test
    public void register() {
        TestInterface bean=new TestClass();
        sm.register(TestInterface.class,bean);
    }

    @Test
    public void lookup() {
        Method method = ReflectionUtils.getPublicMethods(TestInterface.class)[0];
        ServiceDescriptor serviceDescriptor = ServiceDescriptor.from(TestInterface.class,method);
        Request request = new Request();
        request.setService(serviceDescriptor);
        ServiceInstance lookup = sm.lookup(request);
        assertNotNull(lookup);
    }
}
