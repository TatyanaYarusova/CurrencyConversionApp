package com.example.currencyconversionapp.utils

fun checkNumberFormat(input: String): Boolean {
    val intRegex = Regex("\\d+")
    val doubleRegex = Regex("\\d+\\.\\d+")

    return input.matches(intRegex) || input.matches(doubleRegex)
}
