package com.example.vidbinge.common.domain.mapper

import com.example.vidbinge.common.data.models.tvshow.TvShow
import com.example.vidbinge.common.network.dtos.TVDto
import javax.inject.Inject

class TvMapper @Inject constructor(): Mapper<TvShow, TVDto> {
    override fun mapToDTo(model: TvShow): TVDto {
        TODO("Not yet implemented")
    }

    override fun mapToModel(dto: TVDto): TvShow {
        return TvShow(
            id = dto.id,
            overview = dto.overview,
            name = dto.name,
            isAdult = dto.isAdult,
            backdropPath = dto.backdropPath?: "",
            genreIds = dto.genreIds,
            originCountries = dto.originCountries,
            originalLanguage = dto.originalLanguage,
            originalName = dto.originalName,
            popularity = dto.popularity,
            posterPath = dto.posterPath?: "",
            firstAirDate = dto.firstAirDate,
            voteAverage = dto.voteAverage,
            voteCount = dto.voteCount
        )
    }
}