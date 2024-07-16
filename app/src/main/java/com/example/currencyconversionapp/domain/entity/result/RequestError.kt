package com.example.currencyconversionapp.domain.entity.result

sealed class RequestError {

    object ServerError : RequestError()

    object NetworkError: RequestError()
}