package com.example.vidbinge.details.ui.states

import androidx.compose.ui.graphics.Color
import com.example.vidbinge.details.data.model.Cast
import com.example.vidbinge.common.data.models.movie.MovieDetails
import com.example.vidbinge.common.ui.State

data class MovieDetailsScreenState(
    val castList: List<Cast> = emptyList(),
    val isAddedToWatchList: Boolean = false,
    val isLoading: Boolean = false,
    val isCastListLoading: Boolean = false,
    val errorMessage: String? = null,
    val extraMovieDetails: MovieDetails? = null,
    val ambientScreenColor: Color = Color.White
): State
