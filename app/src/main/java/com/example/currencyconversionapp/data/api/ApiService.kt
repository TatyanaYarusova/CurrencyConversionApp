package com.example.currencyconversionapp.data.api

import com.example.currencyconversionapp.data.model.ResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ApiService {

    @GET("latest")
    suspend fun getCurrencyRate (
        @Header("apikey") apiKey: String,
        @Query(QUERY_FROM) from: String,
        @Query(QUERY_TO) to: String,
    ): Response<ResponseDto>

    companion object {
        private const val QUERY_FROM = "base_currency"
        private const val QUERY_TO = "currencies"
    }
}