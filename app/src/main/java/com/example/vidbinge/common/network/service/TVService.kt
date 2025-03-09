package com.example.vidbinge.common.network.service

import com.example.vidbinge.common.network.dtos.ListResponse
import com.example.vidbinge.common.network.dtos.TVDto
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import retrofit2.http.GET

interface TVService {
    @GET("airing_today")
    suspend fun getAiringTodayShows(): Response<ListResponse<TVDto>>

    @GET("popular")
    suspend fun getPopularShows(): Response<ListResponse<TVDto>>

    @GET("top_rated")
    suspend fun getTopRatedShows(): Response<ListResponse<TVDto>>
}