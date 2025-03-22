package com.example.vidbinge.details.data.model

import com.example.vidbinge.common.Constants
import com.example.vidbinge.common.data.coil.TMDbImageSize
import com.example.vidbinge.common.data.models.Genre

data class MovieDetails(
    val genres: List<Genre>,
    val runtime: Int?,
    val id: Int,
    val isAdult: Boolean,
    val backdropPath: String,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String,
    val releaseDate: String,
    val title: String,
    val voteAverage: Double,
    val voteCount: Int
) {
    fun backdropFullUrl(size: TMDbImageSize): String{
        return "${Constants.TMDb_IMAGE_BASE_URL}/${size.stringSize}${backdropPath}"
    }

    fun posterFullPath(size: TMDbImageSize): String{
        return "${Constants.TMDb_IMAGE_BASE_URL}/${size.stringSize}${posterPath}"
    }

    fun runtimeAsString(): String {
        return if(runtime == 0) ""
        else{
            val hours = runtime!! / 60
            val minutes = runtime % 60
            "${hours}h ${minutes}m"
        }
    }

    val releaseYear: String
        get() {
            return releaseDate.split("-").first()
        }
}

