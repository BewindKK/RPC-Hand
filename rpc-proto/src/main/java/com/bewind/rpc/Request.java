package com.bewind.rpc;

import lombok.Data;

/**
 * 表示RPC的一个请求
 *
 */

@Data
public class Request {
    //请求的服务
    private ServiceDescriptor service;
    private Object[] parameters;
}
