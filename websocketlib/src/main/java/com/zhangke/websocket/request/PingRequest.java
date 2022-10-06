package com.zhangke.websocket.request;

import org.java_websocket.client.WebSocketClient;

public class PingRequest implements Request<Object> {

    PingRequest() {
    }

    @Override
    public void setRequestData(Object data) {

    }

    @Override
    public Object getRequestData() {
        return null;
    }

    @Override
    public void send(WebSocketClient client) {
        client.sendPing();
    }

    @Override
    public void release() {
        RequestFactory.releasePingRequest(this);
    }
}
