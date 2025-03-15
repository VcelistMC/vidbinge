package com.example.vidbinge.common.network.dtos

import com.google.gson.annotations.SerializedName

data class TVDto(
    val id: Int,
    val overview: String,
    val name: String,
    @SerializedName("adult") val isAdult: Boolean,
    @SerializedName("backdrop_path") val backdropPath: String?,
    @SerializedName("genre_ids") val genreIds: List<Int>,
    @SerializedName("origin_country") val originCountries: List<String>,
    @SerializedName("original_language") val originalLanguage: String,
    @SerializedName("original_name") val originalName: String,
    @SerializedName("popularity") val popularity: Double,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("first_air_date") val firstAirDate: String,
    @SerializedName("vote_average") val voteAverage: Double,
    @SerializedName("vote_count") val voteCount: Int
)
