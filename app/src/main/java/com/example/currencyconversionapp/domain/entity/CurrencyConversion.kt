package com.example.currencyconversionapp.domain.entity

data class CurrencyConversion(
    val from: String,
    val to: String,
    val amount: String,
    val date: String,
    val result: Double
)
