package com.example.vidbinge.details.ui.viewmodels

import android.app.Application
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.toRoute
import com.example.vidbinge.MovieDetailsDestination
import com.example.vidbinge.common.data.models.movie.MovieDetails
import com.example.vidbinge.common.data.repo.MovieRepository
import com.example.vidbinge.common.ext.getDominantColor
import com.example.vidbinge.common.ext.takeAtMost
import com.example.vidbinge.common.network.connectivity.ConnectivityObserver
import com.example.vidbinge.common.network.connectivity.NetworkConnectivityObserver
import com.example.vidbinge.common.ui.SimpleBaseViewModel
import com.example.vidbinge.details.data.model.Cast
import com.example.vidbinge.details.ui.intents.MovieDetailsScreenIntent
import com.example.vidbinge.details.ui.states.MovieDetailsScreenState
import com.example.vidbinge.home.ui.effects.HomeScreenEffect
import com.example.vidbinge.home.ui.intents.HomeScreenIntent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieDetailsViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
    savedStateHandle: SavedStateHandle,
    application: Application
) : SimpleBaseViewModel<MovieDetailsScreenState, MovieDetailsScreenIntent>() {

    private val movieId = savedStateHandle.toRoute<MovieDetailsDestination>().movieId
    private val networkConnectivityObserver = NetworkConnectivityObserver(application.applicationContext)
    private var latestNetworkState: ConnectivityObserver.ConnectivityStatus = ConnectivityObserver.ConnectivityStatus.UNAVAILABLE


    init {
        observeNetworkConnectivity()
        handleIntent(MovieDetailsScreenIntent.LoadMovieDetails)
    }


    private fun observeNetworkConnectivity() {
        viewModelScope.launch {
            networkConnectivityObserver.observe().collect { state ->
                when(state){
                    ConnectivityObserver.ConnectivityStatus.AVAILABLE -> {
                        handleIntent(MovieDetailsScreenIntent.LoadMovieDetails)
                    }
                    ConnectivityObserver.ConnectivityStatus.UNAVAILABLE -> {
                        updateState { it.copy(errorMessage = "No internet connection") }
                    }
                    ConnectivityObserver.ConnectivityStatus.LOST -> {}
                }
            }
        }
    }
    private fun getMovieDetails(): Flow<MovieDetails> {
        return movieRepository.getMovieDetails(movieId)
    }

    private fun getMovieCast(): Flow<List<Cast>>{
        return movieRepository.getMovieCredits(movieId)
    }

    private fun getMovieData(){
        viewModelScope.launch {
            combine(
                getMovieDetails(),
                getMovieCast()
            ) { movieDetails, movieCast ->
                screenState.value.copy(
                    extraMovieDetails = movieDetails,
                    castList = movieCast.takeAtMost(4),
                    isLoading = false
                )
            }.onStart {
                updateState {
                    it.copy(
                        isLoading = true,
                        isCastListLoading = true
                    )
                }
            }.catch {
                updateState { oldState ->
                    oldState.copy(
                        isLoading = false,
                        isCastListLoading = false,
                        errorMessage = "Something went wrong !"
                    )
                }
            }.collect{ combinedState ->
                updateState {
                    it.copy(
                        extraMovieDetails = combinedState.extraMovieDetails,
                        castList = combinedState.castList,
                        isLoading = false,
                        isCastListLoading = false
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
                getMovieData()
            }
        }
    }
}