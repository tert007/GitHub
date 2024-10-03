package com.safonov.github.presentation.list

sealed class UserListUiState {
    data object Loading : UserListUiState()
    // TODO showing state
}