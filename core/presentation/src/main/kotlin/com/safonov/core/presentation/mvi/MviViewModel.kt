package com.safonov.core.presentation.mvi

import kotlinx.coroutines.flow.StateFlow

interface MviViewModel<U: UiState, I: Intent> {
    val uiState: StateFlow<U>

    fun onIntentTriggered(intent: I)
}