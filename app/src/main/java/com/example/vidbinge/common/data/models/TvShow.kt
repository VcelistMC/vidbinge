package com.example.vidbinge.common.data.models

import com.example.vidbinge.common.Constants
import com.example.vidbinge.common.data.coil.TMDbImageSize
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
){
    fun backdropFullUrl(size: TMDbImageSize): String{
        return "${Constants.TMDb_IMAGE_BASE_URL}/${size.stringSize}${backdropPath}"
    }

    fun posterFullPath(size: TMDbImageSize): String{
        return "${Constants.TMDb_IMAGE_BASE_URL}/${size.stringSize}${posterPath}"
    }
}
