package com.agusw.test_list_games.api

import com.agusw.test_list_games.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class RequestInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val url = request.url.newBuilder()
            .addQueryParameter("key", BuildConfig.API_KEY)
            .build()

        return chain.proceed(request.newBuilder().url(url).build())
    }
}