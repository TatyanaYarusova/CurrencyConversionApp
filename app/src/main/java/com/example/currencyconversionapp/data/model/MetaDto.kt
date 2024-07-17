package com.example.currencyconversionapp.data.model

import com.google.gson.annotations.SerializedName

data class MetaDto(
    @SerializedName("last_updated_at")
    val lastUpdatedAt: String
)
