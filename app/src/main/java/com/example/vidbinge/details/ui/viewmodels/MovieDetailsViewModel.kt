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
import com.example.vidbinge.common.ext.takeAtMost
import com.example.vidbinge.common.ui.SimpleBaseViewModel
import com.example.vidbinge.details.ui.intents.MovieDetailsScreenIntent
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
) : SimpleBaseViewModel<MovieDetailsScreenState, MovieDetailsScreenIntent>() {

    private val movieId = savedStateHandle.toRoute<MovieDetailsDestination>().movieId

    init {
        handleIntent(MovieDetailsScreenIntent.LoadMovieDetails)
    }
    private fun getMovieDetails() {
        viewModelScope.launch {
            movieRepository.getMovieDetails(movieId)
                .onStart {
                    updateState { it.copy(isLoading = true) }
                }
                .collect { details ->
                    updateState {
                        it.copy(
                            isLoading = false,
                            extraMovieDetails = details
                        )
                    }
                }

        }
    }

    private fun getMovieCast(){
        viewModelScope.launch {
            movieRepository.getMovieCredits(movieId)
                .onStart { updateState { it.copy(isCastListLoading = true) } }
                .collect{ cast ->
                    updateState {
                        it.copy(
                            isCastListLoading = false,
                            castList = cast.takeAtMost(4)
                        )
                    }
                }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun onImageLoadedForAmbientColor(drawable: Drawable) {
        val ambientColor = drawable.getDominantColor()
        updateState { it.copy(ambientScreenColor = ambientColor) }
    }

    override fun initialState(): MovieDetailsScreenState {
        return MovieDetailsScreenState(
            isLoading = true
        )
    }

    override fun handleIntent(intent: MovieDetailsScreenIntent) {
        when(intent){
            is MovieDetailsScreenIntent.LoadMovieDetails -> {
                getMovieDetails()
                getMovieCast()
            }
        }
    }
}