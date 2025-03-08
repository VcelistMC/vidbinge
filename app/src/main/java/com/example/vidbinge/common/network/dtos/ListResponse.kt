package com.example.vidbinge.common.network.dtos

import com.google.gson.annotations.SerializedName
import retrofit2.Response

data class ListResponse<T> (
    val page: Int,
    val results: List<T>,
    @SerializedName("total_pages") val totalPages: Int,
    @SerializedName("total_results")val totalResults: Int
)