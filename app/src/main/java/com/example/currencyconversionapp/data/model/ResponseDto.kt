package com.example.currencyconversionapp.data.model

import com.google.gson.annotations.SerializedName

data class ResponseDto(
    @SerializedName("meta")
    val meta: MetaDto,

    @SerializedName("data")
    val data: Map<String, CurrencyDto>
)
