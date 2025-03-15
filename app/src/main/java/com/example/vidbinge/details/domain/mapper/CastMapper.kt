package com.example.vidbinge.details.domain.mapper

import com.example.vidbinge.common.domain.mapper.Mapper
import com.example.vidbinge.details.data.dto.CastDTO
import com.example.vidbinge.details.data.model.Cast
import javax.inject.Inject

class CastMapper @Inject constructor() : Mapper<Cast, CastDTO> {
    override fun mapToDTo(model: Cast): CastDTO {
        TODO("Not yet implemented")
    }

    override fun mapToModel(dto: CastDTO): Cast {
        return Cast(
            id = dto.id,
            name = dto.name,
            character = dto.character ?: "",
            profilePath = dto.profilePath ?: ""
        )
    }

}