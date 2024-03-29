package com.zhangke.websocket.request;

import org.java_websocket.client.WebSocketClient;

public interface Request<T> {

    /**
     * 设置要发送的数据
     */
    void setRequestData(T data);

    /**
     * 获取要发送的数据
     */
    T getRequestData();

    /**
     * 发送数据
     */
    void send(WebSocketClient client);

    /**
     * 释放改资源
     */
    void release();
}
