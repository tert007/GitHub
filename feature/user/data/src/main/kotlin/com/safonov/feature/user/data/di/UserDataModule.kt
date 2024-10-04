package com.safonov.feature.user.data.di

import com.safonov.core.data.source.local.AppDatabase
import com.safonov.core.data.source.local.dao.UserDao
import com.safonov.feature.user.data.repository.UserRepositoryImpl
import com.safonov.feature.user.data.source.remote.UserApi
import com.safonov.github.feature.user.domain.repository.UserRepository
import com.safonov.github.feature.user.domain.usecase.FindUserUseCase
import com.safonov.github.feature.user.domain.usecase.GetUsersStreamUseCase
import com.safonov.github.feature.user.domain.usecase.RequestUpdateUsersUseCase
import org.koin.dsl.module

val userDataModule = module {
    factory { FindUserUseCase(repository = get()) }
    factory { GetUsersStreamUseCase(repository = get()) }
    factory { RequestUpdateUsersUseCase(repository = get()) }

    single<UserRepository> {
        UserRepositoryImpl(
            userApiService = inject(),
            userDao = inject()
        )
    }
    single<UserApi> { UserApi(client = get()) }
    single<UserDao> { get<AppDatabase>().userDao }
}