package com.safonov.github.app

import android.app.Application
import com.safonov.feature.user.data.di.userDataModule
import com.safonov.feature.user.presentation.di.userPresentationModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(userDataModule)
            modules(userPresentationModule)
        }
    }
}