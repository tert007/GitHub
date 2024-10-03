package com.safonov.core.data.di

import androidx.room.Room
import com.safonov.core.data.source.local.AppDatabase
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module

val coreDataModule = module {
    single<AppDatabase> {
        Room.databaseBuilder(get(), AppDatabase::class.java, "github.db")
            .fallbackToDestructiveMigration()
            .build()
    }

    single<Json> { Json { ignoreUnknownKeys = true } }

    // Shared HttpClient for all feature/[name]/data modules
    single {
        HttpClient(CIO) {
            install(ContentNegotiation) {
                json(json = get())
            }
        }
    }
}