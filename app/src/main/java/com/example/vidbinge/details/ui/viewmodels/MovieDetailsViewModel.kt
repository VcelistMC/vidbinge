package com.example.vidbinge.details.ui.viewmodels

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.vidbinge.MovieDetailsDestination
import com.example.vidbinge.common.data.repo.MovieRepository
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

    val movieId = savedStateHandle.toRoute<MovieDetailsDestination>().movieId


    var _movieDetailsScreenState =
        MutableStateFlow(MovieDetailsScreenState())
        private set

    init {
        getMovieDetails()
    }

    fun getMovieDetails() {
        viewModelScope.launch {
            movieRepository.getMovieDetails(movieId)
                .onStart {
                    _movieDetailsScreenState.update { it.copy(isCastListLoading = true, isLoading = true) }
                }
                .collect { details ->
                    _movieDetailsScreenState.update {
                        it.copy(
                            isLoading = false,

                            extraMovieDetails = details
                        )
                    }
                }

        }
    }
}