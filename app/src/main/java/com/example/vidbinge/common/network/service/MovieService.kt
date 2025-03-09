package com.example.vidbinge.common.network.service

import com.example.vidbinge.common.network.dtos.ListResponse
import com.example.vidbinge.common.network.dtos.MovieDto
import retrofit2.Response
import retrofit2.http.GET


interface MovieService {
    @GET("now_playing")
    suspend fun getNowPlayingMovies(): Response<ListResponse<MovieDto>>

    @GET("popular")
    suspend fun getPopularMovies(): Response<ListResponse<MovieDto>>

    @GET("top_rated")
    suspend fun getTopRatedMovies(): Response<ListResponse<MovieDto>>

    @GET("upcoming")
    suspend fun getUpcomingMovies(): Response<ListResponse<MovieDto>>
}