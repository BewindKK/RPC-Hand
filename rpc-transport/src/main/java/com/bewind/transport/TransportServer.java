package com.bewind.transport;

/**
 * 1、启动，监听端口
 * 2、接受请求
 * 3、关闭监听
 */
public interface TransportServer {
    //在初始化的时候使用处理网络请求
    void init(int port,RequestHandler handler);

    void start();

    void stop();
}
