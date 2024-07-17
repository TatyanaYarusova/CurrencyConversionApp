package com.example.currencyconversionapp.data.model

import com.google.gson.annotations.SerializedName

data class CurrencyDto(
    @SerializedName("code")
    val code: String,

    @SerializedName("value")
    val value: Double
)
