package com.bewind.example;

import com.bewind.server.RpcServer;
import com.bewind.server.RPCServerConfig;

public class Server {
    public static void main(String[] args) {
        RpcServer server = new RpcServer(new RPCServerConfig());
        server.register(CalcService.class, new CalcServiceImpl());
        server.start();
    }
}
