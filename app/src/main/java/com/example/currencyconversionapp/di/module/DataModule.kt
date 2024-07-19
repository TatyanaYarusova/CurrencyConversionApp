package com.example.currencyconversionapp.di.module

import com.example.currencyconversionapp.data.api.ApiFactory
import com.example.currencyconversionapp.data.api.ApiService
import com.example.currencyconversionapp.data.repository.CurrencyRepositoryImpl
import com.example.currencyconversionapp.di.annotation.AppScope
import com.example.currencyconversionapp.domain.repository.CurrencyRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
interface DataModule {

    @Binds
    @AppScope
    fun bindCurrencyRepository(impl: CurrencyRepositoryImpl): CurrencyRepository

    companion object {
        @Provides
        fun provideApiService(): ApiService = ApiFactory.apiService

        @Provides
        fun provideIODispatcher(): CoroutineDispatcher = Dispatchers.IO

        @Provides
        fun provideAccessKey(): String = ACCESS_KEY
        private const val ACCESS_KEY = "cur_live_yVrV510zcUM4qbR2dOs9dg4MvEw0X6V89IaOChA0"

    }
}