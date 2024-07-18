package com.example.currencyconversionapp.presentation.state

sealed class ErrorEvent {

    object ServerError : ErrorEvent()

    object NetworkError: ErrorEvent()
}