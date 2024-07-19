package com.example.currencyconversionapp

import android.app.Application
import com.example.currencyconversionapp.di.component.DaggerCurrencyConversionAppComponent

class CurrencyConversionApp: Application() {

    val component by lazy {
        DaggerCurrencyConversionAppComponent.factory()
            .create(this)
    }

}