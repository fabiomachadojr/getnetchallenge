package com.challenge.petnet.di

import com.challenge.petnet.data.repository.PetRepositoryImpl
import com.challenge.petnet.domain.repository.PetRepository
import com.challenge.petnet.domain.usecase.GetDetailItemUseCase
import com.challenge.petnet.domain.usecase.GetItemsUseCase
import com.challenge.petnet.presentation.cart.viewmodel.CartViewModel
import com.challenge.petnet.presentation.detail.viewmodel.ItemDetailViewModel
import com.challenge.petnet.presentation.home.viewmodel.HomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {

    single<PetRepository> { PetRepositoryImpl(get()) }

    factory { GetItemsUseCase(get()) }
    factory { GetDetailItemUseCase(get()) }

    viewModel { HomeViewModel(get()) }
    viewModel { ItemDetailViewModel(get()) }
    viewModel { CartViewModel() }
}