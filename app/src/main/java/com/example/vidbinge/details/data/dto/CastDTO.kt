package com.example.vidbinge.details.data.dto

import com.google.gson.annotations.SerializedName

data class CastDTO(

    val id: Int,
    val name: String,

    @SerializedName("profile_path")
    val profilePath: String?,

    val character: String?,

)

