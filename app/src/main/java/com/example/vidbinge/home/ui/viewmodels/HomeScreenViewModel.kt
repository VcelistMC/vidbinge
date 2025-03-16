package com.example.vidbinge.home.ui.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vidbinge.common.data.models.movie.Movie
import com.example.vidbinge.common.data.models.PillChoices
import com.example.vidbinge.common.data.repo.MovieRepository
import com.example.vidbinge.common.data.repo.TVRepository
import com.example.vidbinge.common.ui.BaseViewModel
import com.example.vidbinge.common.ui.SideEffect
import com.example.vidbinge.common.ui.SimpleBaseViewModel
import com.example.vidbinge.home.ui.intents.HomeScreenIntent
import com.example.vidbinge.home.ui.states.HomeScreenMovies
import com.example.vidbinge.home.ui.states.HomeScreenState
import com.example.vidbinge.home.ui.states.HomeScreenTV
import dagger.hilt.android.lifecycle.HiltViewModel
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
    private val tvRepository: TVRepository
): SimpleBaseViewModel<HomeScreenState, HomeScreenIntent>() {

    init {
        handleIntent(HomeScreenIntent.LoadAllData)
    }

    private fun populateHomeScreen() {
        viewModelScope.launch {
            combine(
                getHomeScreenMovies(),
                getHomeScreenTV()
            ){ movies, tv ->
                _screenState.value.copy(
                    homeScreenMovies = movies,
                    homescreenTV = tv,
                    isLoading = false
                )
            }.onStart {
                updateState { oldState -> oldState.copy(isLoading = true) }
            }.collect { combinedState ->
                updateState { oldState ->
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
            tvRepository.getNowAiringShows(),
            tvRepository.getPopularShows(),
            tvRepository.getTopRatedShows()
        ) { nowAiring, popular, topRated ->
            HomeScreenTV(
                nowAiringShows = nowAiring,
                popularShows = popular,
                topRatedShows = topRated
            )
        }
    }

    fun onPillPressed(selectedPill: PillChoices){
        updateState {
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

    override fun initialState(): HomeScreenState {
        return HomeScreenState()
    }

    override fun handleIntent(intent: HomeScreenIntent) {
        when(intent){
            is HomeScreenIntent.LoadAllData -> populateHomeScreen()
        }
    }
}