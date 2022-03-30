package com.bewind.example;

import com.bewind.client.RpcClient;

public class Client {
    public static void main(String[] args) {
        RpcClient rpcClient = new RpcClient();
        //拿到远程代理对象
        CalcService proxy = rpcClient.getProxy(CalcService.class);
        int add=proxy.add(1,1);
        int minu=proxy.minus(1,1);
        System.out.println(add);
        System.out.println(minu);
    }
}
