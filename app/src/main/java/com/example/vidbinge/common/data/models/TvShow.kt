package com.example.vidbinge.common.data.models

import com.google.gson.annotations.SerializedName

data class TvShow(
    val id: Int,
    val overview: String,
    val name: String,
    val isAdult: Boolean,
    val backdropPath: String,
    val genreIds: List<Int>,
    val originCountries: List<String>,
    val originalLanguage: String,
    val originalName: String,
    val popularity: Double,
    val posterPath: String,
    val firstAirDate: String,
    val voteAverage: Double,
    val voteCount: Int
)
