package com.example.vidbinge.details.ui.states

import com.example.vidbinge.common.data.models.Cast
import com.example.vidbinge.common.data.models.movie.Movie
import com.example.vidbinge.common.data.models.movie.MovieDetails

data class MovieDetailsScreenState(
    val castList: List<Cast> = emptyList(),
    val isAddedToWatchList: Boolean = false,
    val isLoading: Boolean = false,
    val isCastListLoading: Boolean = false,
    val error: String? = null,
    val extraMovieDetails: MovieDetails? = null
)
