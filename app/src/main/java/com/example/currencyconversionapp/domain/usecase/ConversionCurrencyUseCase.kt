package com.example.currencyconversionapp.domain.usecase

import com.example.currencyconversionapp.domain.repository.CurrencyRepository

class ConversionCurrencyUseCase (private val repo: CurrencyRepository) {
    suspend operator fun invoke(from: String, to: String, amount: Double) = repo.conversion(from, to, amount)
}