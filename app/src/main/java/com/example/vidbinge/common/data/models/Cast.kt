package com.example.vidbinge.common.data.models

import com.example.vidbinge.common.Constants
import com.example.vidbinge.common.data.coil.TMDbImageSize

data class Cast(
    val name: String,
    val character: String,
    val profilePath: String
) {
    fun profileFullPath(size: TMDbImageSize): String {
        return "${Constants.TMDb_IMAGE_BASE_URL}/${size.stringSize}${profilePath}"
    }

    companion object {
        val mockList = listOf(
            Cast(
                name = "Miles Teller",
                character = "Levi",
                profilePath = "/cg3LW0xX6RKr8dmescxq1bepcb5.jpg"
            ),
            Cast(
                name = "Anya Taylor-Joy",
                character = "Drasa",
                profilePath = "/qYNofOjlRke2MlJVihmJmEdQI4v.jpg"
            ),
            Cast(
                name = "Sigourney Weaver",
                character = "Bartholomew",
                profilePath = "/wTSnfktNBLd6kwQxgvkqYw6vEon.jpg"
            ),
            Cast(
                name = "Sope Dirisu",
                character = "J.D.",
                profilePath = "/24Se9voPxrO200Ae8GQRbMkE55B.jpg"
            ),

            )
    }
}
