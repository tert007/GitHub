package com.safonov.github.app

import android.app.Application
import com.safonov.core.data.di.coreDataModule
import com.safonov.feature.user.data.di.userDataModule
import com.safonov.feature.user.presentation.di.userPresentationModule
import com.safonov.github.BuildConfig
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin
import timber.log.Timber

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        startKoin {
            androidContext(this@App)
            modules(coreDataModule)
            modules(userDataModule)
            modules(userPresentationModule)
        }
    }
}