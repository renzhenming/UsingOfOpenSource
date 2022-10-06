package com.rzm.testapplication.web_socket;

import android.content.Context;
import android.os.Looper;

import com.rzm.testapplication.LogUtils;
import com.rzm.testapplication.startup.my_startup.startup.AndroidStartup;
import com.zhangke.websocket.WebSocketHandler;
import com.zhangke.websocket.WebSocketManager;
import com.zhangke.websocket.WebSocketSetting;

public class WebSocketTask extends AndroidStartup<Void> {


    @Override
    public boolean callCreateOnMainThread() {
        return false;
    }

    @Override
    public boolean waitOnMainThread() {
        return false;
    }

    @Override
    public Void create(Context context) {
        String t = Looper.myLooper() == Looper.getMainLooper()
                ? "主线程: " : "子线程: ";
        LogUtils.log(t + " WebSocketTask：start");
        WebSocketSetting setting = new WebSocketSetting();
        setting.setConnectUrl("ws://121.40.165.18:8800");//必填
        //设置连接超时时间
        setting.setConnectTimeout(15 * 1000);
        //设置心跳间隔时间
        setting.setConnectionLostTimeout(Integer.MAX_VALUE);
        //设置断开后的重连次数，可以设置的很大，不会有什么性能上的影响
        setting.setReconnectFrequency(1);
        //网络状态发生变化后是否重连，
        //需要调用 WebSocketHandler.registerNetworkChangedReceiver(context) 方法注册网络监听广播
        setting.setReconnectWithNetworkChanged(true);
        //通过 init 方法初始化默认的 WebSocketManager 对象
        WebSocketManager manager = WebSocketHandler.init(setting);
        //启动连接
        manager.start();
        //注意，需要在 AndroidManifest 中配置网络状态获取权限
        //注册网路连接状态变化广播
        WebSocketHandler.registerNetworkChangedReceiver(context);
        LogUtils.log(t + " WebSocketTask：end");
        return null;
    }
}
