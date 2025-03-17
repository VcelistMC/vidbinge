package com.example.vidbinge.home.ui.effects

import com.example.vidbinge.common.ui.SideEffect

sealed class HomeScreenEffect: SideEffect {
    data class ShowToast(val message: String): HomeScreenEffect()
}