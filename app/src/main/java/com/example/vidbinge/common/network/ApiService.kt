package com.example.vidbinge.common.network

import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("movie/now_playing")
    suspend fun getNowPlayingMovies(): Response<String>

}