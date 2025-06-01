package com.challenge.petnet.di

import com.challenge.petnet.data.remote.PetApiService
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {

    single {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    single {
        OkHttpClient.Builder()
            .addInterceptor(get<HttpLoggingInterceptor>())
            .build()
    }

    single {
        Retrofit.Builder()
            .baseUrl("https://7e0505b1-6e9a-42ee-9ad3-463bb1f9c8f8.mock.pstmn.io")
            .client(get())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single<PetApiService> {
        get<Retrofit>().create(PetApiService::class.java)
    }
}