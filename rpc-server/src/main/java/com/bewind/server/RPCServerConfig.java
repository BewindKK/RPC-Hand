package com.bewind.server;

import com.bewind.codec.Decoder;
import com.bewind.codec.Encoder;
import com.bewind.codec.JSONDecoder;
import com.bewind.codec.JSONEncoder;
import com.bewind.transport.Impl.HTTPTransportServer;
import com.bewind.transport.TransportServer;
import lombok.Data;

/**
 * server配置
 */
@Data
public class RPCServerConfig {
    //使用的网络模块
    private Class<? extends TransportServer> transportClass= HTTPTransportServer.class;
    //序列化实现
    private Class<? extends Encoder> encoderClass= JSONEncoder.class;
    private Class<? extends Decoder> decoderClass= JSONDecoder.class;
    //启动之后监听什么端口
    private int port=3000;
}
