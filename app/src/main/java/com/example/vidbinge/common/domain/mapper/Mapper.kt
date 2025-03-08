package com.example.vidbinge.common.domain.mapper

interface Mapper<MODEL, DTO> {
    fun mapToDTo(model: MODEL): DTO
    fun mapToModel(dto: DTO): MODEL
}