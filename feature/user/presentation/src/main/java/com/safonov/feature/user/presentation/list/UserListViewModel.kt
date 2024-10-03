package com.safonov.feature.user.presentation.list

import androidx.lifecycle.ViewModel
import com.safonov.github.feature.user.domain.usecase.GetUsersUseCase

class UserListViewModel(
    private val getUsersUseCase: GetUsersUseCase
) : ViewModel() {
    suspend fun getUser() = getUsersUseCase()
}