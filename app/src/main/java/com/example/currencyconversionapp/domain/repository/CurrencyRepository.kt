package com.example.currencyconversionapp.domain.repository

interface CurrencyRepository {

    suspend fun conversion(from: String, to: String, amount: Double)

}