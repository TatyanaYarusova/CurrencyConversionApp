package com.example.currencyconversionapp.data.repository

import com.example.currencyconversionapp.data.api.ApiService
import com.example.currencyconversionapp.data.model.CurrencyDto
import com.example.currencyconversionapp.data.model.MetaDto
import com.example.currencyconversionapp.data.model.ResponseDto
import com.example.currencyconversionapp.domain.entity.CurrencyConversion
import com.example.currencyconversionapp.domain.entity.result.RequestResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.whenever
import retrofit2.Response

class CurrencyRepositoryImplTest {

    private val apiService: ApiService = mock()

    private val dispatcher = Dispatchers.Unconfined

    private val key = "test_key"

    private val currencyRepository = CurrencyRepositoryImpl(apiService, dispatcher, key)


    @Test
    fun `conversion should return success when API call is successful`() = runTest {
        val amount = 100.0
        val from = "RUB"
        val to = "RUB"

        val currencyDto = CurrencyDto(
            code = "RUB",
            value = 1.0
        )
        val responseDto = ResponseDto(
            meta = MetaDto("2024-07-18T23:59:59Z"),
            data = mapOf("RUB" to currencyDto)
        )

        whenever(apiService.getCurrencyRate(key, from, to)).thenReturn(Response.success(responseDto))

        val expectedCurrency = CurrencyConversion(
            to = to,
            from = from,
            amount = amount,
            date = "2024-07-18T23:59:59Z",
            result = 100.0
        )

        val result = currencyRepository.conversion(from, to, amount)

        assertEquals(RequestResult.Success(expectedCurrency), result)
    }

}