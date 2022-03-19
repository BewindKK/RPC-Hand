package com.bewind.rpc;

import lombok.Data;

/**
 * 表示RPC的返回
 */
@Data
public class Response {
    /**
     * 服务返回编码
     * 0-成功，非0失败
     */
    private int code;
    //错误消息
    private String message="ok";
    //返回的数据
    private Object data;
}
