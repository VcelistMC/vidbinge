package com.example.vidbinge.common.data.models

data class Genre(
    val id: Int,
    val genre: String
){
    companion object {
        val mockList = listOf(
            Genre(1, "Action"),
            Genre(2, "Sci-Fi"),
            Genre(3, "Romance"),
            Genre(4, "Thriller")
        )
    }
}
