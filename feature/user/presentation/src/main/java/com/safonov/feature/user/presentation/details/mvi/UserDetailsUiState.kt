package com.safonov.feature.user.presentation.details.mvi

import com.safonov.core.presentation.mvi.UiState
import com.safonov.github.feature.user.domain.model.User

internal data class UserDetailsUiState(
    val contentState: ContentState = ContentState.Loading
) : UiState {
    sealed class ContentState {
        data object Loading : ContentState()
        data class Loaded(val user: User?) : ContentState()
    }
}
