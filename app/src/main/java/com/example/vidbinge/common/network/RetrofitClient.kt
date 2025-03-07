package com.example.vidbinge.common.network

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

abstract class RetrofitClient<T> {
    val service: T

    init {
        service = buildRetrofitClient()
    }

    abstract fun buildRetrofitClient(): T


    open fun createHttpClient(): OkHttpClient {
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        val headersInterceptor = HeadersInterceptor()
        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor(headersInterceptor)
            .build()
    }


    fun <REQUEST, RESULT> doRequest(
        request: suspend () -> Response<REQUEST>,
        mapResponse: (REQUEST) -> RESULT
    ): Flow<RESULT> {
        return flow{
            val response = request()
            if(response.isSuccessful){
                val returningData: RESULT? = response.body()?.let(mapResponse)
                if(returningData != null){
                    emit(returningData)
                    return@flow
                }else{
                    error("Unknown Error")
                }
            }else{

                val errorBody = response.errorBody()
                if(errorBody != null){
                    error(errorBody)
                }else{
                    error("Unknown Error")
                }
            }
        }.flowOn(Dispatchers.IO)

    }
}