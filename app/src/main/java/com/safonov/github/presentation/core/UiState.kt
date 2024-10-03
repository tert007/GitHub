package com.safonov.github.presentation.core

interface Intent

interface UiState

interface MviViewModel<U: UiState, I: Intent> {
    val uiState: U

    fun onIntentTriggered(intent: I)
}