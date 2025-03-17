package com.example.vidbinge.home.ui.intents

import com.example.vidbinge.common.data.models.PillChoices
import com.example.vidbinge.common.ui.Intent

sealed class HomeScreenIntent: Intent {
    data object LoadAllData: HomeScreenIntent()
    data class SwitchPill(val pill: PillChoices): HomeScreenIntent()
}