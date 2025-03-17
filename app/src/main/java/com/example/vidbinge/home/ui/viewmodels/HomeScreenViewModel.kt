package com.example.vidbinge.home.ui.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vidbinge.common.data.models.movie.Movie
import com.example.vidbinge.common.data.models.PillChoices
import com.example.vidbinge.common.data.repo.MovieRepository
import com.example.vidbinge.common.data.repo.TVRepository
import com.example.vidbinge.common.network.connectivity.ConnectivityObserver
import com.example.vidbinge.common.network.connectivity.NetworkConnectivityObserver
import com.example.vidbinge.common.ui.BaseViewModel
import com.example.vidbinge.common.ui.SideEffect
import com.example.vidbinge.common.ui.SimpleBaseViewModel
import com.example.vidbinge.home.ui.effects.HomeScreenEffect
import com.example.vidbinge.home.ui.intents.HomeScreenIntent
import com.example.vidbinge.home.ui.states.HomeScreenMovies
import com.example.vidbinge.home.ui.states.HomeScreenState
import com.example.vidbinge.home.ui.states.HomeScreenTV
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
class HomeScreenViewModel @Inject constructor(
    private val movieRepository: MovieRepository,
    private val tvRepository: TVRepository,
    application: Application
): BaseViewModel<HomeScreenState, HomeScreenIntent, HomeScreenEffect>() {

    private val networkConnectivityObserver = NetworkConnectivityObserver(application.applicationContext)

    init {
        handleIntent(HomeScreenIntent.LoadAllData)
        observeNetworkConnectivity()
    }

    private fun observeNetworkConnectivity() {
        viewModelScope.launch {
            networkConnectivityObserver.observe().collect { state ->
                when(state){
                    ConnectivityObserver.ConnectivityStatus.AVAILABLE -> {
                        handleIntent(HomeScreenIntent.LoadAllData)
                    }
                    ConnectivityObserver.ConnectivityStatus.UNAVAILABLE -> {
                        updateState { it.copy(errorMessage = "No internet connection") }
                    }
                    ConnectivityObserver.ConnectivityStatus.LOST -> {
                        sendEffect(HomeScreenEffect.ShowToast("Internet Connection Lost"))
                    }
                }
            }
        }
    }

    private fun populateHomeScreen() {
        viewModelScope.launch {
            combine(
                getHomeScreenMovies(),
                getHomeScreenTV()
            ){ movies, tv ->
                screenState.value.copy(
                    homeScreenMovies = movies,
                    homescreenTV = tv,
                    isLoading = false
                )
            }.onStart {
                updateState { oldState -> oldState.copy(isLoading = true) }
            }.catch{
                updateState { oldState -> oldState.copy(isLoading = false, errorMessage = "No Internet Connection") }
            }.collect { combinedState ->
                updateState { oldState ->
                    oldState.copy(
                        homeScreenMovies = combinedState.homeScreenMovies,
                        homescreenTV = combinedState.homescreenTV,
                        isLoading = false,
                        errorMessage = null
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

    private fun onPillPressed(selectedPill: PillChoices){
        updateState {
            it.copy(selectedPill = selectedPill)
        }
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
            is HomeScreenIntent.SwitchPill -> onPillPressed(intent.pill)
        }
    }
}