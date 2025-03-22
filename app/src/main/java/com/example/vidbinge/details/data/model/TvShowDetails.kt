package com.example.vidbinge.details.data.model

import com.example.vidbinge.common.Constants
import com.example.vidbinge.common.data.coil.TMDbImageSize
import com.example.vidbinge.common.data.models.Genre

data class TvShowDetails (
    val genres: List<Genre>,
    val id: Int,
    val isAdult: Boolean,
    val backdropPath: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String,
    val title: String,
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
