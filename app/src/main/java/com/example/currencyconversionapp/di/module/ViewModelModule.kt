package com.example.currencyconversionapp.di.module

import androidx.lifecycle.ViewModel
import com.example.currencyconversionapp.di.annotation.ViewModelKey
import com.example.currencyconversionapp.presentation.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
interface ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    fun bindMainViewModel(viewModel: MainViewModel): ViewModel
}