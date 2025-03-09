package com.example.vidbinge.home.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.vidbinge.common.data.models.Movie
import com.example.vidbinge.common.data.models.PillChoices
import com.example.vidbinge.common.network.repo.MovieRepository
import com.example.vidbinge.common.network.repo.TVRepository
import com.example.vidbinge.home.ui.states.HomeScreenMovies
import com.example.vidbinge.home.ui.states.HomeScreenState
import com.example.vidbinge.home.ui.states.HomeScreenTV
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeScreenViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
    private val tvRespository: TVRepository
): ViewModel() {
    var homeScreenState = MutableStateFlow(HomeScreenState())
        private set

    init {
        populateHomeScreen()
    }

    private fun populateHomeScreen() {
        viewModelScope.launch {
            combine(
                getHomeScreenMovies(),
                getHomeScreenTV()
            ){ movies, tv ->
                homeScreenState.value.copy(
                    homeScreenMovies = movies,
                    homescreenTV = tv,
                    isLoading = false
                )
            }.onStart {
                homeScreenState.update { oldState -> oldState.copy(isLoading = true) }
            }.collect { combinedState ->
                homeScreenState.update { oldState ->
                    oldState.copy(
                        homeScreenMovies = combinedState.homeScreenMovies,
                        homescreenTV = combinedState.homescreenTV,
                        isLoading = false
                    )
                }
            }
        }
    }

    private fun getHomeScreenTV(): Flow<HomeScreenTV> {
        return combine(
            tvRespository.getNowAiringShows(),
            tvRespository.getPopularShows(),
            tvRespository.getTopRatedShows()
        ) { nowAiring, popular, topRated ->
            HomeScreenTV(
                nowAiringShows = nowAiring,
                popularShows = popular,
                topRatedShows = topRated
            )
        }
    }

    fun onPillPressed(selectedPill: PillChoices){
        Log.d("412 - ${javaClass.simpleName}", selectedPill.name)
        homeScreenState.update {
            it.copy(selectedPill = selectedPill)
        }
    }

    fun onMoviePressed(movie: Movie){

    }

    private fun getHomeScreenMovies(): Flow<HomeScreenMovies> {
        return combine(
            movieRepository.getNowPlayingMovies(),
            movieRepository.getPopularMovies(),
            movieRepository.getTopRatedMovies(),
            movieRepository.getUpcomingMovies()
        ){ nowPlaying, popular, topRated, upcoming ->
            HomeScreenMovies(
                nowPlayingMovies = nowPlaying,
                popularMovies = popular,
                topRatedMovies = topRated,
                upcomingMovies = upcoming
            )
        }

    }
}