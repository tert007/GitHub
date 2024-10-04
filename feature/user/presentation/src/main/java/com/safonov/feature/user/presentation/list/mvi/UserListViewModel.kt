package com.safonov.feature.user.presentation.list.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.safonov.core.presentation.mvi.MviViewModel
import com.safonov.github.feature.user.domain.usecase.GetUsersStreamUseCase
import com.safonov.github.feature.user.domain.usecase.RequestUpdateUsersUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber

internal class UserListViewModel(
    getUsersStreamUseCase: GetUsersStreamUseCase,
    private val requestUpdateUsersUseCase: RequestUpdateUsersUseCase,
) : ViewModel(), MviViewModel<UserListUiState, UserListIntent> {

    private val navigationTargetFlow = MutableStateFlow<UserListUiState.NavigationTarget?>(null)
    private val snackBarMessageFlow = MutableStateFlow<UserListUiState.SnackBarMessage?>(null)
    private val refreshingFlow = MutableStateFlow(false)

    private val contentFlow: Flow<UserListUiState.ContentState> =
        combine(refreshingFlow, getUsersStreamUseCase()) { isRefreshing, users ->
            UserListUiState.ContentState.Loaded(users, isRefreshing)
        }.onStart {
            requestUpdateUsers()
        }

    override val uiState: StateFlow<UserListUiState> = combine(
        navigationTargetFlow,
        snackBarMessageFlow,
        contentFlow
    ) { navigationTarget, snackBarMessage, contentState ->
        UserListUiState(
            navigationTarget = navigationTarget,
            message = snackBarMessage,
            contentState = contentState
        )
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = UserListUiState()
    )

    override fun onIntentTriggered(intent: UserListIntent) {
        when (intent) {
            UserListIntent.Refresh -> viewModelScope.launch {
                requestUpdateUsers()
            }

            is UserListIntent.ConsumeNavigation -> navigationTargetFlow.update {
                return@update null
            }

            is UserListIntent.ConsumedMessage -> snackBarMessageFlow.update {
                return@update null
            }

            is UserListIntent.UserClick -> navigationTargetFlow.update {
                return@update UserListUiState.NavigationTarget.UserDetails(intent.userId)
            }
        }
    }

    private suspend fun requestUpdateUsers() {
        refreshingFlow.tryEmit(true)
        requestUpdateUsersUseCase().onFailure { e ->
            Timber.e(e)
            snackBarMessageFlow.update {
                return@update UserListUiState.SnackBarMessage.UnableFetchData
            }
        }
        refreshingFlow.tryEmit(false)
    }
}