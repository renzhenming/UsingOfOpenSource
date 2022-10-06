package com.zhangke.websocket.dispatcher;

import com.zhangke.websocket.SocketListener;

public interface ResponseDelivery extends SocketListener {

    void addListener(SocketListener listener);

    void removeListener(SocketListener listener);

    void clear();

    boolean isEmpty();
}
