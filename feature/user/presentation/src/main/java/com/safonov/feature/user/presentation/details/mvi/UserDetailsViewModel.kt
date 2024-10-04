package com.safonov.feature.user.presentation.details.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.safonov.core.presentation.mvi.MviViewModel
import com.safonov.github.feature.user.domain.usecase.FindUserUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn

internal class UserDetailsViewModel(
    private val userId: Int,
    private val findUserUseCase: FindUserUseCase
) : ViewModel(), MviViewModel<UserDetailsUiState, UserDetailsIntent> {

    override val uiState: StateFlow<UserDetailsUiState> = flow {
        val state = UserDetailsUiState(
            contentState = UserDetailsUiState.ContentState.Loaded(
                user = findUserUseCase(userId)
            )
        )
        emit(state)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = UserDetailsUiState()
    )

    override fun onIntentTriggered(intent: UserDetailsIntent) {
        // No-op
    }
}