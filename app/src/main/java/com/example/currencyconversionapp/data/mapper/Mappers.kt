package com.example.currencyconversionapp.data.mapper

import com.example.currencyconversionapp.data.model.ResponseDto
import com.example.currencyconversionapp.domain.entity.CurrencyConversion

fun ResponseDto.toEntity(amount: Double, from: String) = CurrencyConversion(
    to = data.keys.first(),
    from = from,
    amount = amount,
    date = meta.lastUpdatedAt,
    result = data.values.first().value * amount
)