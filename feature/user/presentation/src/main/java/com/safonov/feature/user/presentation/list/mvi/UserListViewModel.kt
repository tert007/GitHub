package com.safonov.feature.user.presentation.list.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.safonov.core.presentation.mvi.MviViewModel
import com.safonov.github.feature.user.domain.usecase.GetUsersUseCase
import com.safonov.github.feature.user.domain.usecase.RequestUpdateUsersUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber

internal class UserListViewModel(
    getUsersUseCase: GetUsersUseCase,
    private val requestUpdateUsersUseCase: RequestUpdateUsersUseCase,
) : ViewModel(), MviViewModel<UserListUiState, UserListIntent> {

    private val navigationTargetFlow: MutableStateFlow<UserListUiState.NavigationTarget?> =
        MutableStateFlow(null)
    private val snackBarMessageFlow: MutableStateFlow<UserListUiState.SnackBarMessage?> =
        MutableStateFlow(null)
    private val refreshingFlow: MutableStateFlow<Boolean> = MutableStateFlow(false)
    private val contentFlow: Flow<UserListUiState.ContentState> = getUsersUseCase()
        .onStart {
            requestUpdateUsers()
        }
        .map { users ->
            UserListUiState.ContentState.Loaded(users)
        }

    override val uiState: StateFlow<UserListUiState> = combine(
        navigationTargetFlow,
        snackBarMessageFlow,
        refreshingFlow,
        contentFlow
    ) { navigationTarget, snackBarMessage, isRefreshing, contentState ->
        UserListUiState(
            navigationTarget = navigationTarget,
            message = snackBarMessage,
            contentState = when (contentState) {
                is UserListUiState.ContentState.Loaded -> contentState.copy(
                    isRefreshing = isRefreshing
                )

                UserListUiState.ContentState.Loading -> contentState
            }
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