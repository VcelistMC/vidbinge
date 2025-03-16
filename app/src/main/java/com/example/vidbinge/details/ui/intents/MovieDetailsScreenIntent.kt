package com.example.vidbinge.details.ui.intents

import com.example.vidbinge.common.ui.Intent

sealed class MovieDetailsScreenIntent: Intent {
    data object LoadMovieDetails: MovieDetailsScreenIntent()
}