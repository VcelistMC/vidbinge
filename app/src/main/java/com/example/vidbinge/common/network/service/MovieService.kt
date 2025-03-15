package com.example.vidbinge.common.network.service

import com.example.vidbinge.common.network.dtos.ListResponse
import com.example.vidbinge.common.network.dtos.MovieDto
import com.example.vidbinge.details.data.dto.MovieDetailsDTO
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path


interface MovieService {
    @GET("now_playing")
    suspend fun getNowPlayingMovies(): Response<ListResponse<MovieDto>>

    @GET("popular")
    suspend fun getPopularMovies(): Response<ListResponse<MovieDto>>

    @GET("top_rated")
    suspend fun getTopRatedMovies(): Response<ListResponse<MovieDto>>

    @GET("upcoming")
    suspend fun getUpcomingMovies(): Response<ListResponse<MovieDto>>

    @GET("{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int
    ): Response<MovieDetailsDTO>
}