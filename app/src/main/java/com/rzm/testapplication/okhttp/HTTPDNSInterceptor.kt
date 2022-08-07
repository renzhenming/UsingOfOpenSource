package com.rzm.testapplication.okhttp

import okhttp3.Interceptor
import okhttp3.Response

/**
 *  通过拦截器，在发送请求之前，将域名替换为 IP 地址。
 */
class HTTPDNSInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originRequest = chain.request()
        val httpUrl = originRequest.url

        val url = httpUrl.toString()
        val host = httpUrl.host

//        val hostIP = HttpDNS.getIpByHost(host)
        val builder = originRequest.newBuilder()

//        if (hostIP != null) {
//            builder.url(HttpDNS.getIpUrl(url, host, hostIP))
//            builder.header("host", hostIP)
//        }
        val newRequest = builder.build()
        val newResponse = chain.proceed(newRequest)
        return newResponse
    }
}