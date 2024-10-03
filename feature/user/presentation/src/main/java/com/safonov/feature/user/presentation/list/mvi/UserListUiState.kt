package com.safonov.feature.user.presentation.list.mvi

import androidx.compose.runtime.Immutable
import com.safonov.core.presentation.mvi.UiState
import com.safonov.github.feature.user.domain.model.User

@Immutable
internal data class UserListUiState(
    val navigationTarget: NavigationTarget? = null,
    val message: SnackBarMessage? = null,
    val contentState: ContentState = ContentState.Loading,
) : UiState {

    sealed class NavigationTarget {
        data class UserDetails(val userId: Int) : NavigationTarget()
    }

    sealed class SnackBarMessage {
        data object UnableFetchData : SnackBarMessage()
    }

    sealed class ContentState {
        data object Loading : ContentState()

        @Immutable
        data class Loaded(val items: List<User>) : ContentState()
    }
}
