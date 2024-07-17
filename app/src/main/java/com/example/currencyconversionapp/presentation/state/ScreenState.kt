package com.example.currencyconversionapp.presentation.state

sealed class ScreenState<out T> {

    object Initial : ScreenState<Nothing>()

    object Loading : ScreenState<Nothing>()

    data class Content<T>(val content: T) : ScreenState<T>()

    data class Error<T>(val errorType: ErrorEvent) : ScreenState<T>()

}