package com.example.vidbinge.common.network.client

import com.example.vidbinge.common.network.RetrofitClient
import com.example.vidbinge.common.network.service.MovieService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Inject

class MovieRetrofitClient @Inject constructor() :
    RetrofitClient<MovieService>() {
    override fun buildRetrofitClient(): MovieService {
        return Retrofit.Builder()
            .baseUrl("https://api.themoviedb.org/3/movie/")
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(createHttpClient())
            .build()
            .create(MovieService::class.java)
    }
}