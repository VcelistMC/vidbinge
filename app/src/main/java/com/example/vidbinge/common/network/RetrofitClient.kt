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
import javax.inject.Inject

class RetrofitClient @Inject constructor() {
    val service: ApiService

    private val baseUrl = "https://api.themoviedb.org/3/"
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
    private val headersInterceptor = HeadersInterceptor()
    private val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .addInterceptor(headersInterceptor)
        .build()


    init {
        service = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
            .create(ApiService::class.java)
    }

    suspend fun <REQUEST, RESULT> doRequest(
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