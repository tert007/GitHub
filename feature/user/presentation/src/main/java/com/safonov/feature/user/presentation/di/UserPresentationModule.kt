package com.safonov.feature.user.presentation.di

import com.safonov.feature.user.presentation.details.mvi.UserDetailsViewModel
import com.safonov.feature.user.presentation.list.mvi.UserListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val userPresentationModule = module {
    viewModel { UserListViewModel(getUsersStreamUseCase = get(), requestUpdateUsersUseCase = get()) }
    viewModel { (userId: Int) -> UserDetailsViewModel(userId = userId, findUserUseCase = get()) }
}