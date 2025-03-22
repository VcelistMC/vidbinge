package com.example.vidbinge.details.domain.mapper

import com.example.vidbinge.common.data.models.Genre
import com.example.vidbinge.common.domain.mapper.Mapper
import com.example.vidbinge.details.data.model.MovieDetails
import com.example.vidbinge.details.data.dto.MovieDetailsDTO
import javax.inject.Inject

class MovieDetailsMapper @Inject constructor(): Mapper<MovieDetails, MovieDetailsDTO> {
    override fun mapToDTo(model: MovieDetails): MovieDetailsDTO {
        TODO("Not yet implemented")
    }

    override fun mapToModel(dto: MovieDetailsDTO): MovieDetails {
        return MovieDetails(
            genres = dto.genres.map { Genre(it.id, it.name) },
            id = dto.id,
            runtime = dto.runtime,
            isAdult = dto.isAdult,
            backdropPath = dto.backdropPath?: "",
            originalLanguage = dto.originalLanguage,
            originalTitle = dto.originalTitle,
            overview = dto.overview,
            popularity = dto.popularity,
            posterPath = dto.posterPath?:"",
            releaseDate = dto.releaseDate,
            title = dto.title,
            voteAverage = dto.voteAverage,
            voteCount = dto.voteCount
        )
    }
}