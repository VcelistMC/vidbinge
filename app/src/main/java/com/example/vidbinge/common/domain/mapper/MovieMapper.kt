package com.example.vidbinge.common.domain.mapper

import com.example.vidbinge.common.data.models.Movie
import com.example.vidbinge.common.network.dtos.MovieDto
import javax.inject.Inject

class MovieMapper @Inject constructor(): Mapper<Movie, MovieDto>{
    override fun mapToDTo(model: Movie): MovieDto {
        TODO("Not yet implemented")
    }

    override fun mapToModel(dto: MovieDto): Movie {
        return Movie(
            id = dto.id,
            isAdult = dto.isAdult,
            backdropPath = dto.backdropPath,
            originalLanguage = dto.backdropPath,
            originalTitle = dto.originalTitle,
            overview = dto.overview,
            popularity = dto.popularity,
            posterPath = dto.posterPath,
            releaseDate = dto.releaseDate,
            title = dto.title,
            voteAverage =  String.format("%.1f", dto.voteAverage).toDouble(),
            voteCount = dto.voteCount
        )
    }

}