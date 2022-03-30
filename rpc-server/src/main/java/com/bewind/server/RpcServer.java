package com.bewind.server;

import com.bewind.codec.Decoder;
import com.bewind.codec.Encoder;
import com.bewind.rpc.Request;
import com.bewind.rpc.Response;
import com.bewind.rpc.utils.ReflectionUtils;
import com.bewind.transport.RequestHandler;
import com.bewind.transport.TransportServer;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

@Slf4j
public class RpcServer {
    //配置
    private RPCServerConfig config;
    //网络模块
    private TransportServer net;
    //序列化
    private Encoder encoder;
    private Decoder decoder;
    private ServiceManager serviceManager;
    private ServiceInvoker serviceInvoker;
    public RpcServer() {
    }
    public RpcServer(RPCServerConfig config){
        this.config=config;
        //net
        this.net= ReflectionUtils.newInstance(
                config.getTransportClass());
        this.net.init(config.getPort(),this.handler);
        //序列化
        this.encoder=ReflectionUtils.newInstance(
                config.getEncoderClass()
        );
        this.decoder=ReflectionUtils.newInstance(
                config.getDecoderClass()
        );
        //service
        this.serviceManager= new ServiceManager();
        this.serviceInvoker=new ServiceInvoker();
    }

    public <T> void register(Class<T> interfaceClass,T bean){
        serviceManager.register(interfaceClass,bean);
    }

    public void start(){
        //实质为网络模块的启动
        this.net.start();
    }

    public void stop(){
        this.net.stop();
    }

    private RequestHandler handler=new RequestHandler() {
        @Override
        public void onRequest(InputStream receive, OutputStream toResp) {
            /**
             * 首先从receive中读到request请求二进制数据的数据体，
             * 读完之后通过serviceInvoker调用服务
             * 拿到数据再通过toResp把数据写回去
             */
            Response response = new Response();
            try {
                byte[] bytes = IOUtils.readFully(receive, receive.available());
                Request request = decoder.decode(bytes, Request.class);
                log.info("get request:{}",request);
                ServiceInstance lookup = serviceManager.lookup(request);
                Object invoke = serviceInvoker.invoke(lookup, request);
                response.setData(invoke);
            } catch (Exception e) {
                log.warn(e.getMessage(),e);
                //返回值
                response.setCode(1);
                response.setMessage("RpcServer got error:"+
                        e.getClass().getName()+":"+e.getMessage());
            }finally {
                try {
                    byte[] outBytes = encoder.encode(response);
                    toResp.write(outBytes);
                    log.info("response client");
                } catch (IOException e) {
                    log.warn(e.getMessage(),e);
                }
            }
        }
    };
}
