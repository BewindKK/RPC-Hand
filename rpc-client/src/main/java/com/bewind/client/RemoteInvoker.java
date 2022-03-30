package com.bewind.client;

import com.bewind.codec.Decoder;
import com.bewind.codec.Encoder;
import com.bewind.rpc.Request;
import com.bewind.rpc.Response;
import com.bewind.rpc.ServiceDescriptor;
import com.bewind.transport.TransportClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 调用远程服务的代理类
 */
@Slf4j
public class RemoteInvoker implements InvocationHandler {

    private Class clazz;
    private Encoder encoder;
    private Decoder decoder;
    private TransportSelector selector;
    public RemoteInvoker(Class clazz,
                         Encoder encoder,
                         Decoder decoder,
                         TransportSelector selector){
        this.clazz=clazz;
        this.decoder=decoder;
        this.encoder=encoder;
        this.selector=selector;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        /**
         * 调用远程服务
         * 1、构造一个请求
         * 2、通过网络把请求发送给server，之后等待server的响应
         * 3、从响应中拿到返回的数据
         */
        //请求
        Request request=new Request();
        request.setService(ServiceDescriptor.from(clazz,method));
        request.setParameters(args);

        //响应
        Response resp=invokeRemote(request);
        if (resp==null||resp.getCode()!=0){
            throw new IllegalStateException("fail to invoke remote: "+resp);
        }
        return resp.getData();
    }

    private Response invokeRemote(Request request) {
        Response resp = null;
        TransportClient client = null;
        try{
            client = selector.select();
            byte[] outBytes = encoder.encode(request);
            InputStream recive = client.write(new ByteArrayInputStream(outBytes));
            byte[] inBytes = IOUtils.readFully(recive, recive.available());
            //response赋值
            resp = decoder.decode(inBytes, Response.class);

        } catch (IOException e) {
            log.warn(e.getMessage(), e);
            resp.setCode(1);
            resp.setMessage("RpcClient got error :" + e.getMessage() + " ： ");
        } finally {
            if (client != null){
                selector.release(client);
            }
        }
        return resp;
    }
}
