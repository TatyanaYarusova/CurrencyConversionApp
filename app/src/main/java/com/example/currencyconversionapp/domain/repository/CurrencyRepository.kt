package com.example.currencyconversionapp.domain.repository

import com.example.currencyconversionapp.domain.entity.CurrencyConversion
import com.example.currencyconversionapp.domain.entity.result.RequestResult

interface CurrencyRepository {

    suspend fun conversion(from: String, to: String, amount: Double): RequestResult<CurrencyConversion>

}