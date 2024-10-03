package com.safonov.feature.user.data.di

import com.safonov.feature.user.data.repository.UserRepositoryImpl
import com.safonov.feature.user.data.source.remote.UserApiService
import com.safonov.github.feature.user.domain.repository.UserRepository
import com.safonov.github.feature.user.domain.usecase.GetUsersUseCase
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module

val userDataModule = module {
    factory { GetUsersUseCase(get()) }

    single<UserRepository> { UserRepositoryImpl(inject()) }

    single {
        HttpClient(CIO) {
            install(ContentNegotiation) {
                json(Json { ignoreUnknownKeys = true })
            }
        }
    }
    factory { UserApiService(get()) }
}