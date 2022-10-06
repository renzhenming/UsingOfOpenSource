package com.zhangke.websocket.response;

import android.text.TextUtils;

import com.zhangke.websocket.dispatcher.IResponseDispatcher;
import com.zhangke.websocket.dispatcher.ResponseDelivery;

public class TextResponse implements Response<String> {

    private String responseText;

    TextResponse() {
    }

    @Override
    public String getResponseData() {
        return responseText;
    }

    @Override
    public void setResponseData(String responseData) {
        this.responseText = responseData;
    }

    @Override
    public void onResponse(IResponseDispatcher dispatcher, ResponseDelivery delivery) {
        dispatcher.onMessage(responseText, delivery);
        release();
    }

    @Override
    public String toString() {
        return String.format("[@TextResponse%s->responseText:%s]",
                hashCode(),
                TextUtils.isEmpty(responseText) ?
                        "null" :
                        responseText);
    }

    @Override
    public void release() {
        ResponseFactory.releaseTextResponse(this);
    }
}
