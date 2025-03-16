package com.example.vidbinge.home.ui.states

import com.example.vidbinge.common.data.models.movie.Movie
import com.example.vidbinge.common.data.models.PillChoices
import com.example.vidbinge.common.data.models.tvshow.TvShow
import com.example.vidbinge.common.ui.State

data class HomeScreenState(
    val isLoading: Boolean = true,
    val errorMessage: String? = null,
    val homeScreenMovies: HomeScreenMovies = HomeScreenMovies(),
    val homescreenTV: HomeScreenTV = HomeScreenTV(),
    val selectedPill: PillChoices = PillChoices.ALL
): State

data class HomeScreenMovies (
    val nowPlayingMovies: List<Movie> = emptyList(),
    val popularMovies: List<Movie> = emptyList(),
    val topRatedMovies: List<Movie> = emptyList(),
    val upcomingMovies: List<Movie> = emptyList()
)

data class HomeScreenTV(
    val nowAiringShows: List<TvShow> = emptyList(),
    val popularShows: List<TvShow> = emptyList(),
    val topRatedShows: List<TvShow> = emptyList()
)