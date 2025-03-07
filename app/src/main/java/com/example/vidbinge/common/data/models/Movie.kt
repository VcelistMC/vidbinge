package com.example.vidbinge.common.data.models

import com.example.vidbinge.common.Constants
import com.example.vidbinge.common.data.coil.TMDbImageSize


data class Movie(
    val id: Int,
    val isAdult: Boolean,
    val backdropPath: String,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String,
    val releaseDate: String,
    val title: String,
    val voteAverage: Double,
    val voteCount: Int
){
    fun backdropFullUrl(size: TMDbImageSize): String{
        return "${Constants.TMDb_IMAGE_BASE_URL}/${size.stringSize}${backdropPath}"
    }

    fun posterFullPath(size: TMDbImageSize): String{
        return "${Constants.TMDb_IMAGE_BASE_URL}/${size.stringSize}${posterPath}"
    }

    companion object {
        val mock1 = Movie(
            id = 950396,
            isAdult = false,
            backdropPath = "/9nhjGaFLKtddDPtPaX5EmKqsWdH.jpg",
            originalLanguage = "en",
            originalTitle = "The Gorge",
            overview = "Two highly trained operatives grow close from a distance after being sent to guard opposite sides of a mysterious gorge. When an evil below emerges, they must work together to survive what lies within.",
            popularity = 2192.325,
            posterPath = "/7iMBZzVZtG0oBug4TfqDb9ZxAOa.jpg",
            releaseDate = "2025-02-13",
            title = "The Gorge",
            voteAverage = 7.8,
            voteCount = 1656,
        )
        val mock2 = Movie(
            id = 950397,
            isAdult = false,
            backdropPath = "/gFFqWsjLjRfipKzlzaYPD097FNC.jpg",
            originalLanguage = "en",
            originalTitle = "Flight Risk",
            overview = "A U.S. Marshal escorts a government witness to trial after he's accused of getting involved with a mob boss, only to discover that the pilot who is transporting them is also a hitman sent to assassinate the informant. After they subdue him, they're forced to fly together after discovering that there are others attempting to eliminate them.",
            popularity = 2192.325,
            posterPath = "/4cR3hImKd78dSs652PAkSAyJ5Cx.jpg",
            releaseDate = "2025-02-13",
            title = "Flight Risk",
            voteAverage = 6.0,
            voteCount = 1656,
        )
        val mock3 = Movie(
            id = 950398,
            isAdult = false,
            backdropPath = "/kEYWal656zP5Q2Tohm91aw6orlT.jpg",
            originalLanguage = "en",
            originalTitle = "Anora",
            overview = "A young sex worker from Brooklyn gets her chance at a Cinderella story when she meets and impulsively marries the son of an oligarch. Once the news reaches Russia, her fairytale is threatened as his parents set out to get the marriage annulled.",
            popularity = 2192.325,
            posterPath = "/qh8m8Udz0sCa5gy9VaqfHPh0yPM.jpg",
            releaseDate = "2025-02-13",
            title = "Anora",
            voteAverage = 7.0,
            voteCount = 1656,
        )
        val mock4 = Movie(
            id = 95039,
            isAdult = false,
            backdropPath = "/qfAfE5auxsuxhxPpnETRAyTP5ff.jpg",
            originalLanguage = "en",
            originalTitle = "Captain America: Brave New World",
            overview = "After meeting with newly elected U.S. President Thaddeus Ross, Sam finds himself in the middle of an international incident. He must discover the reason behind a nefarious global plot before the true mastermind has the entire world seeing red.",
            popularity = 2192.325,
            posterPath = "/pzIddUEMWhWzfvLI3TwxUG2wGoi.jpg",
            releaseDate = "2025-02-13",
            title = "Captain America: Brave New World",
            voteAverage = 6.2,
            voteCount = 1656,
        )
        val mockList = listOf(mock1, mock2, mock3, mock4)
    }
}
