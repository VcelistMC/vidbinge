package com.example.vidbinge.details.ui.intents

import com.example.vidbinge.common.ui.Intent

sealed class TvShowDetailsScreenIntent: Intent {
    data object LoadTvShowDetails: TvShowDetailsScreenIntent()
}