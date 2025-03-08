package com.example.vidbinge.home.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vidbinge.common.data.models.Movie
import com.example.vidbinge.common.data.models.PillChoices
import com.example.vidbinge.common.network.repo.MovieRepository
import com.example.vidbinge.home.ui.states.HomeScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val movieRepository: MovieRepository
): ViewModel() {
    var homeScreenState = MutableStateFlow(HomeScreenState())
        private set

    init {
        getHomeScreenMovies()
    }

    fun onPillPressed(selectedPill: PillChoices){
        Log.d("412 - ${javaClass.simpleName}", selectedPill.name)
        homeScreenState.update {
            it.copy(selectedPill = selectedPill)
        }
    }

    fun onMoviePressed(movie: Movie){

    }

    fun getHomeScreenMovies(){
        viewModelScope.launch(Dispatchers.IO) {
            delay(2000)
            combine(
                movieRepository.getNowPlayingMovies(),
                movieRepository.getPopularMovies(),
                movieRepository.getTopRatedMovies(),
                movieRepository.getUpcomingMovies()
            ){ nowPlaying, popular, topRated, upcoming ->
                homeScreenState.value.copy(
                    nowPlayingMovies = nowPlaying,
                    popularMovies = popular,
                    topRatedMovies = topRated,
                    upcomingMovies = upcoming,
                    isLoading = false
                )
            }.onStart {
                homeScreenState.update {
                    it.copy(isLoading = true)
                }
            }.collect { combinedState ->
                homeScreenState.update {
                    combinedState
                }
            }
        }
    }
}