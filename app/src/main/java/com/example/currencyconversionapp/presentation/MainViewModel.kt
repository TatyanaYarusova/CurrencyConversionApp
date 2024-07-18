package com.example.currencyconversionapp.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.currencyconversionapp.data.repository.CurrencyRepositoryImpl
import com.example.currencyconversionapp.domain.entity.CurrencyConversion
import com.example.currencyconversionapp.domain.entity.result.RequestError
import com.example.currencyconversionapp.domain.entity.result.RequestResult
import com.example.currencyconversionapp.domain.usecase.ConversionCurrencyUseCase
import com.example.currencyconversionapp.presentation.state.ErrorEvent
import com.example.currencyconversionapp.presentation.state.ScreenState
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repo = CurrencyRepositoryImpl()

    private val getCurrencyRate = ConversionCurrencyUseCase(repo)

    private val _state: MutableLiveData<ScreenState<CurrencyConversion>> =
        MutableLiveData(ScreenState.Initial)
    val state: LiveData<ScreenState<CurrencyConversion>> = _state


    fun conversion(to: String, from: String, amount: Double) {
        viewModelScope.launch {
            _state.value = ScreenState.Loading
            try {
                when (val currencyConversion = getCurrencyRate(from, to, amount)) {
                    is RequestResult.Success -> {
                        _state.value = ScreenState.Content(currencyConversion.content)
                        _state.value = ScreenState.Initial
                    }

                    is RequestResult.Error -> handleError(currencyConversion.requestError)
                }
            } catch (e: Exception) {
                _state.value = ScreenState.Error(ErrorEvent.ServerError)
            }
        }
    }

    private fun handleError(errorType: RequestError) {
        _state.value = when (errorType) {
            is RequestError.NetworkError -> ScreenState.Error(ErrorEvent.NetworkError)
            is RequestError.ServerError -> ScreenState.Error(ErrorEvent.ServerError)
        }
    }
}