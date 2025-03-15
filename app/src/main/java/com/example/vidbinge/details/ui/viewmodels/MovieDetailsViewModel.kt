package com.example.vidbinge.details.ui.viewmodels

import android.graphics.drawable.Drawable
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.vidbinge.MovieDetailsDestination
import com.example.vidbinge.common.data.repo.MovieRepository
import com.example.vidbinge.common.ext.getDominantColor
import com.example.vidbinge.details.ui.states.MovieDetailsScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val movieId = savedStateHandle.toRoute<MovieDetailsDestination>().movieId


    var movieDetailsScreenState =
        MutableStateFlow(MovieDetailsScreenState())
        private set

    init {
        getMovieDetails()
    }

    fun getMovieDetails() {
        viewModelScope.launch {
            movieRepository.getMovieDetails(movieId)
                .onStart {
                    movieDetailsScreenState.update { it.copy(isCastListLoading = true, isLoading = true) }
                }
                .collect { details ->
                    movieDetailsScreenState.update {
                        it.copy(
                            isLoading = false,
                            extraMovieDetails = details
                        )
                    }
                }

        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun onImageLoadedForAmbientColor(drawable: Drawable) {
        val ambientColor = drawable.getDominantColor()
        movieDetailsScreenState.update { it.copy(ambientScreenColor = ambientColor) }

    }
}