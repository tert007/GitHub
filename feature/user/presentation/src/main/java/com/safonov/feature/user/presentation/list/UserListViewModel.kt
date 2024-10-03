package com.safonov.feature.user.presentation.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.safonov.github.feature.user.domain.model.User
import com.safonov.github.feature.user.domain.usecase.GetUsersUseCase
import com.safonov.github.feature.user.domain.usecase.RequestUpdateUsersUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn

class UserListViewModel(
    getUsersUseCase: GetUsersUseCase,
    private val requestUpdateUsersUseCase: RequestUpdateUsersUseCase,
) : ViewModel() {
    val flow: StateFlow<List<User>> = getUsersUseCase()
        .onStart {
            requestUpdateUsersUseCase()
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())
}