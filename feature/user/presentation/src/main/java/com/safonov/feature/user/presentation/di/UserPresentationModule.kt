package com.safonov.feature.user.presentation.di

import com.safonov.feature.user.presentation.list.UserListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val userPresentationModule = module {
    viewModel { UserListViewModel(getUsersUseCase = get(), requestUpdateUsersUseCase = get()) }
}