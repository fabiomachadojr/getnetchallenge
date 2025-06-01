package com.challenge.petnet

import android.app.Application
import com.challenge.petnet.di.appModule
import com.challenge.petnet.di.networkModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class PetnetApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@PetnetApplication)
            modules(listOf(appModule, networkModule))
        }
    }
}