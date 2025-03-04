package com.example.vidbinge.common.network

import android.os.Build
import com.example.vidbinge.common.Secrets
import okhttp3.Interceptor
import okhttp3.Response

class HeadersInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request().newBuilder()
            .addHeader("Content-Type", "application/json")
            .addHeader("OS", "Android")
            .addHeader("OS-Version", "${Build.VERSION.SDK_INT}")
            .addHeader("Accept-Language", "en")

        request = request.addHeader("Authorization", "Bearer ${Secrets.TOKEN}")
        val success = kotlin.random.Random.nextBoolean()
        if(success){
        }
        return chain.proceed(request.build())
    }
}

