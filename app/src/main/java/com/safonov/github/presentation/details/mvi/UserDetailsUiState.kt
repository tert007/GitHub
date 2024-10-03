package com.safonov.github.presentation.details.mvi

import androidx.compose.runtime.Stable

@Stable
data class UserDetailsUiState(
    val login: String,
    val avatarUrl: String,
    val url: String,
)