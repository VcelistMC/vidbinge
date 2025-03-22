package com.example.vidbinge.details.data.dto

import com.google.gson.annotations.SerializedName


data class TvShowDetailsDTO(
    @SerializedName("adult")
    val isAdult: Boolean,

    @SerializedName("backdrop_path")
    val backdropPath: String?,

    @SerializedName("genres")
    val genres: List<GenreDTO>,

    @SerializedName("id")
    val id: Int,

    @SerializedName("overview")
    val overview: String,

    @SerializedName("popularity")
    val popularity: Double,

    @SerializedName("poster_path")
    val posterPath: String?,

    @SerializedName("name")
    val title: String,

    @SerializedName("vote_average")
    val voteAverage: Double,

    @SerializedName("vote_count")
    val voteCount: Int
)