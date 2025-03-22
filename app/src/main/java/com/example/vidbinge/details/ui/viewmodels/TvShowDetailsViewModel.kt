package com.example.vidbinge.details.ui.viewmodels

import android.app.Application
import android.graphics.drawable.Drawable
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.example.vidbinge.common.data.repo.TVRepository
import com.example.vidbinge.common.ext.getDominantColor
import com.example.vidbinge.common.network.connectivity.ConnectivityObserver
import com.example.vidbinge.common.network.connectivity.NetworkConnectivityObserver
import com.example.vidbinge.common.ui.SimpleBaseViewModel
import com.example.vidbinge.details.ui.intents.MovieDetailsScreenIntent
import com.example.vidbinge.details.ui.intents.TvShowDetailsScreenIntent
import com.example.vidbinge.details.ui.screens.TvShowDetailsDestination
import com.example.vidbinge.details.ui.states.TvShowDetailsScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TvShowDetailsViewModel @Inject constructor(
    private val tvRepository: TVRepository,
    savedStateHandle: SavedStateHandle,
    application: Application
): SimpleBaseViewModel<TvShowDetailsScreenState, TvShowDetailsScreenIntent>(){

    private val tvShowId = savedStateHandle.toRoute<TvShowDetailsDestination>().tvShowId
    private val networkConnectivityObserver = NetworkConnectivityObserver(application.applicationContext)

    init {
        observeNetworkConnectivity()
        handleIntent(TvShowDetailsScreenIntent.LoadTvShowDetails)
    }


    private fun observeNetworkConnectivity() {
        viewModelScope.launch {
            networkConnectivityObserver.observe().collect { state ->
                when(state){
                    ConnectivityObserver.ConnectivityStatus.AVAILABLE -> {
                        handleIntent(TvShowDetailsScreenIntent.LoadTvShowDetails)
                    }
                    ConnectivityObserver.ConnectivityStatus.UNAVAILABLE -> {
                        updateState { it.copy(errorMessage = "No internet connection") }
                    }
                    ConnectivityObserver.ConnectivityStatus.LOST -> {}
                }
            }
        }
    }

    override fun initialState(): TvShowDetailsScreenState {
        return TvShowDetailsScreenState()
    }

    override fun handleIntent(intent: TvShowDetailsScreenIntent) {
        when(intent){
            is TvShowDetailsScreenIntent.LoadTvShowDetails -> loadShowDetails()
        }
    }

    private fun loadShowDetails() {
        viewModelScope.launch {
            tvRepository.getTvShowDetails(tvShowId)
                .onStart { updateState { it.copy(isLoading = true) } }
                .catch { updateState { it.copy(isLoading = false, errorMessage = "Something went wrong !") } }
                .collect { details ->
                    updateState { it.copy(isLoading = false, tvShowDetails = details) }
                }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun onImageLoadedForAmbientColor(drawable: Drawable) {
        val dominantColor = drawable.getDominantColor()
        updateState { it.copy(ambientScreenColor = dominantColor) }
    }

}