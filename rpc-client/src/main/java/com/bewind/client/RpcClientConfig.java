package com.bewind.client;

import com.bewind.codec.Decoder;
import com.bewind.codec.Encoder;
import com.bewind.codec.JSONDecoder;
import com.bewind.codec.JSONEncoder;
import com.bewind.rpc.Peer;
import com.bewind.transport.Impl.HTTPTransportClient;
import com.bewind.transport.TransportClient;
import lombok.Data;

import java.util.Arrays;
import java.util.List;

@Data
public class RpcClientConfig {
    private Class<? extends TransportClient> tranportClass= HTTPTransportClient.class;
    private Class<? extends Encoder> encoderClass= JSONEncoder.class;
    private Class<? extends Decoder> decoderClass= JSONDecoder.class;
    private Class<? extends TransportSelector> selectorClass=RandomTransportSelector.class;
    //连接数量
    private int connectCount=1;
    //网络端点
    private List<Peer> servers= Arrays.asList(
        new Peer("127.0.0.1",3000)
    );
}
