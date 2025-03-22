package com.example.vidbinge.details.domain.mapper

import com.example.vidbinge.common.data.models.Genre
import com.example.vidbinge.common.domain.mapper.Mapper
import com.example.vidbinge.details.data.model.MovieDetails
import com.example.vidbinge.details.data.dto.MovieDetailsDTO
import com.example.vidbinge.details.data.dto.TvShowDetailsDTO
import com.example.vidbinge.details.data.model.TvShowDetails
import javax.inject.Inject

class TvShowDetailsMapper @Inject constructor(): Mapper<TvShowDetails, TvShowDetailsDTO> {
    override fun mapToDTo(model: TvShowDetails): TvShowDetailsDTO {
        TODO("Not yet implemented")
    }

    override fun mapToModel(dto: TvShowDetailsDTO): TvShowDetails {
        return TvShowDetails(
            genres = dto.genres.map { Genre(it.id, it.name) },
            id = dto.id,
            isAdult = dto.isAdult,
            backdropPath = dto.backdropPath?: "",
            overview = dto.overview,
            popularity = dto.popularity,
            posterPath = dto.posterPath?:"",
            title = dto.title,
            voteAverage = dto.voteAverage,
            voteCount = dto.voteCount
        )
    }
}