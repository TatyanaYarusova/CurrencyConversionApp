package com.example.currencyconversionapp.di.component

import android.app.Application
import com.example.currencyconversionapp.di.annotation.AppScope
import com.example.currencyconversionapp.di.module.DataModule
import com.example.currencyconversionapp.di.module.ViewModelModule
import com.example.currencyconversionapp.ui.MainFragment
import dagger.BindsInstance
import dagger.Component

@Component(
    modules = [
        DataModule::class,
        ViewModelModule::class
    ]
)
@AppScope
interface CurrencyConversionAppComponent {

    @Component.Factory
    interface CurrencyConversionAppComponentFactory {
        fun create(
            @BindsInstance application: Application
        ): CurrencyConversionAppComponent
    }

    fun inject(fragment: MainFragment)
}