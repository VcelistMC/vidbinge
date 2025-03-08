package com.example.vidbinge.home.ui.states

import com.example.vidbinge.common.data.models.Movie
import com.example.vidbinge.common.data.models.PillChoices

data class HomeScreenState(
    val isLoading: Boolean = true,
    val errorMessage: String? = null,
    val nowPlayingMovies: List<Movie> = emptyList(),
    val popularMovies: List<Movie> = emptyList(),
    val topRatedMovies: List<Movie> = emptyList(),
    val upcomingMovies: List<Movie> = emptyList(),
    val selectedPill: PillChoices = PillChoices.ALL
)
