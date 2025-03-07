package com.example.vidbinge.common.network.dtos

import com.google.gson.annotations.SerializedName

data class NowPlayingDto(
    val dates: NowPlayingDatesDto,
    val page: Int,
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("total_results") val totalResults: Int,
    val results: List<MovieDto>
)

data class NowPlayingDatesDto internal constructor(
    val maximum: String,
    val minimum: String
)


// TODO: Replace with original