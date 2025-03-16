package com.example.vidbinge.common.ui

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

abstract class BaseViewModel <STATE: State, INTENT: Intent, SIDE_EFFECT: SideEffect> : SimpleBaseViewModel<STATE, INTENT>(){

    protected var _screenEffects = MutableSharedFlow<SIDE_EFFECT>()
    val screenEffects: SharedFlow<SIDE_EFFECT> = _screenEffects.asSharedFlow()

    fun sendEffect(effect: SIDE_EFFECT) {
        _screenEffects.tryEmit(effect)
    }
}

abstract class SimpleBaseViewModel <STATE: State, INTENT: Intent> : ViewModel(){
    protected var _screenState = MutableStateFlow(initialState())
    val screenState: StateFlow<STATE> = _screenState.asStateFlow()


    abstract fun initialState(): STATE
    abstract fun handleIntent(intent: INTENT)
    protected fun updateState(reducer: (STATE) -> STATE) {
        _screenState.update(reducer)
    }

}