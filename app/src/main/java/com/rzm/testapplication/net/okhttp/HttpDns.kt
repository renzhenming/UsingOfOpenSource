package com.rzm.testapplication.net.okhttp

import android.text.TextUtils
import okhttp3.Dns
import java.net.InetAddress

class HttpDns : Dns {
    override fun lookup(hostname: String): List<InetAddress> {
//        val ip = HttpDnsHelper.getIpByHost(hostname)
        val ip = ""
        if (TextUtils.isEmpty(ip)) {
            //返回自己解析的地址列表
            return InetAddress.getAllByName(ip).toList()
        } else {
            // 解析失败，使用系统解析
            return Dns.SYSTEM.lookup(hostname)
        }
    }
}