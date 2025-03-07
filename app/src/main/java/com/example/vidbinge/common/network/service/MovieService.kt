package com.example.vidbinge.common.network.service

import com.example.vidbinge.common.network.dtos.NowPlayingDto
import retrofit2.Response
import retrofit2.http.GET


interface MovieService {
    @GET("now_playing")
    suspend fun getNowPlayingMovies(): Response<NowPlayingDto>

}