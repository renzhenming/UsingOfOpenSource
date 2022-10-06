package com.zhangke.websocket.request;

import android.text.TextUtils;

import org.java_websocket.client.WebSocketClient;

public class StringRequest implements Request<String> {

    private String requestText;

    StringRequest() {
    }

    @Override
    public void setRequestData(String data) {
        this.requestText = data;
    }

    @Override
    public String getRequestData() {
        return requestText;
    }

    @Override
    public void send(WebSocketClient client) {
        client.send(requestText);
    }

    @Override
    public void release() {
        RequestFactory.releaseStringRequest(this);
    }

    @Override
    public String toString() {
        return String.format("@StringRequest%s,requestText:%s",
                hashCode(),
                TextUtils.isEmpty(requestText) ?
                        "null" :
                        requestText);
    }
}
