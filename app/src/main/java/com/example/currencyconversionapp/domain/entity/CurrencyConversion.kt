package com.example.currencyconversionapp.domain.entity

data class CurrencyConversion(
    val to: String,
    val from: String,
    val amount: Double,
    val date: String,
    val result: Double
)
