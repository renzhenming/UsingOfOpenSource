package com.zhangke.websocket;

import com.zhangke.websocket.response.ErrorResponse;

import org.java_websocket.framing.Framedata;

import java.nio.ByteBuffer;

public abstract class SimpleListener implements SocketListener {

    private final String TAG = "SimpleListener";

    @Override
    public void onConnected() {
        //to override
    }

    @Override
    public void onConnectFailed(Throwable e) {
        //to override
    }

    @Override
    public void onDisconnect() {
        //to override
    }

    @Override
    public <T> void onMessage(String message, T data) {
        //to override
    }

    @Override
    public void onSendDataError(ErrorResponse errorResponse) {
        //to override
    }

    @Override
    public <T> void onMessage(ByteBuffer bytes, T data) {
        //to override
    }

    @Override
    public void onPing(Framedata framedata) {
        //to override
    }

    @Override
    public void onPong(Framedata framedata) {
        //to override
    }
}
