package com.hcdisat.week_three.data.network

import okhttp3.Interceptor
import okhttp3.Response

class RequestInterceptor: Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request().newBuilder()
            .addHeader("HEADER_1", "HEADER_1")
            .build()

        return chain.proceed(request)
    }
}