package com.example.currencyconversionapp.presentation

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.currencyconversionapp.data.repository.CurrencyRepositoryImpl
import com.example.currencyconversionapp.domain.usecase.ConversionCurrencyUseCase
import kotlinx.coroutines.launch

class MainViewModel(application: Application): AndroidViewModel(application) {

    private val repo = CurrencyRepositoryImpl()

    private val getCurrencyRate = ConversionCurrencyUseCase(repo)

    fun conversion(to: String, from: String, amount: Double) {
        viewModelScope.launch {
           val res = getCurrencyRate(from, to, amount)
            Log.d("TAG", res.toString())
        }

    }
}