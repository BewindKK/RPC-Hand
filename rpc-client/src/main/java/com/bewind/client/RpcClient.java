package com.bewind.client;

import com.bewind.codec.Decoder;
import com.bewind.codec.Encoder;
import com.bewind.rpc.utils.ReflectionUtils;

import java.lang.reflect.Proxy;

public class RpcClient {
    private RpcClientConfig config;
    private final Encoder encoder;
    private final Decoder decoder;
    private TransportSelector selector;

    //直接用默认
    public RpcClient(){
        this(new RpcClientConfig());
    }
    public RpcClient(RpcClientConfig config){
        this.config=config;
        this.encoder= ReflectionUtils.newInstance(this.config.getEncoderClass());
        this.decoder= ReflectionUtils.newInstance(this.config.getDecoderClass());
        this.selector= ReflectionUtils.newInstance(this.config.getSelectorClass());
        this.selector.init(
                this.config.getServers(),
                this.config.getConnectCount(),
                this.config.getTranportClass()
        );
    }

    public <T> T getProxy(Class<T> clazz) {
        return (T) Proxy.newProxyInstance(
                getClass().getClassLoader(),
                new Class[]{clazz},
                new RemoteInvoker(clazz, encoder, decoder, selector)
        );
    }
}
