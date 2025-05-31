package com.challenge.petnet

import android.app.Application
import com.challenge.petnet.presentation.cart.viewmodel.CartViewModel
import com.challenge.petnet.presentation.detail.viewmodel.ItemDetailViewModel
import com.challenge.petnet.presentation.home.viewmodel.HomeViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.GlobalContext.startKoin
import org.koin.dsl.module

class PetnetApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@PetnetApplication)
            modules(appModule)
        }
    }
}

val appModule = module {
    viewModel { HomeViewModel() }
    viewModel { ItemDetailViewModel() }
    viewModel { CartViewModel() }
}