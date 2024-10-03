package com.safonov.core.presentation.mvi

interface MviViewModel<U: UiState, I: Intent> {
    val uiState: U

    fun onIntentTriggered(intent: I)
}