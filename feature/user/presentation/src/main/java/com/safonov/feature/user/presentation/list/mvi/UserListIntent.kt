package com.safonov.feature.user.presentation.list.mvi

import com.safonov.core.presentation.mvi.Intent

internal sealed class UserListIntent : Intent {

    data class ConsumeNavigation(
        val navigationTarget: UserListUiState.NavigationTarget
    ) : UserListIntent()

    data class ConsumedMessage(
        val message: UserListUiState.SnackBarMessage
    ) : UserListIntent()

    data class UserClick(val userId: Int) : UserListIntent()

    data object Refresh : UserListIntent()
}