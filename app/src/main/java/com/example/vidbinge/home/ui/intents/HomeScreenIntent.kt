package com.example.vidbinge.home.ui.intents

import com.example.vidbinge.common.ui.Intent

sealed class HomeScreenIntent: Intent {
    data object LoadAllData: HomeScreenIntent()
}