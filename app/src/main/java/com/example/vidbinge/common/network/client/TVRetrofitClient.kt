package com.example.vidbinge.common.network.client

import com.example.vidbinge.common.network.RetrofitClient
import com.example.vidbinge.common.network.service.MovieService
import com.example.vidbinge.common.network.service.TVService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Inject

class TVRetrofitClient @Inject constructor(): RetrofitClient<TVService>() {
    override fun buildRetrofitClient(): TVService {
        return Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/tv/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(createHttpClient())
            .build()
            .create(TVService::class.java)
    }
}