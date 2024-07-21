package com.example.currencyconversionapp.data.mapper

import com.example.currencyconversionapp.data.model.CurrencyDto
import com.example.currencyconversionapp.data.model.MetaDto
import com.example.currencyconversionapp.data.model.ResponseDto
import com.example.currencyconversionapp.domain.entity.CurrencyConversion
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class MappersTest {

    @Test
    fun `convert response model EXPECT currency entity`() {
        val amount = 100.0
        val from = "RUB"

        val currencyDto = CurrencyDto(
            code = "RUB",
            value = 1.0
        )
        val responseDto = ResponseDto(
            meta = MetaDto("2024-07-18T23:59:59Z"),
            data = mapOf("RUB" to currencyDto)
        )

        val expectedCurrency = CurrencyConversion(
            to = "RUB",
            from = from,
            amount = amount,
            date = "18.07.2024 23:59",
            result = 100.0
        )

        val actualCurrency = responseDto.toEntity(amount, from)

        assertEquals(expectedCurrency, actualCurrency)
    }


    @Test
    fun `convert currency parcelable model EXPECT currency entity`() {
        val amount = 100.0
        val from = "RUB"

        val currencyParcelableModel = CurrencyConversionParcelableModel(
            to = "RUB",
            from = from,
            amount = amount,
            date = "2024-07-18T23:59:59Z",
            result = 100.0
        )

        val expectedCurrency = CurrencyConversion(
            to = "RUB",
            from = from,
            amount = amount,
            date = "2024-07-18T23:59:59Z",
            result = 100.0
        )

        val actualCurrency = currencyParcelableModel.toEntity()

        assertEquals(expectedCurrency, actualCurrency)
    }

    @Test
    fun `convert currency entity EXPECT currency parcelable model`() {
        val amount = 100.0
        val from = "RUB"


        val currency = CurrencyConversion(
            to = "RUB",
            from = from,
            amount = amount,
            date = "2024-07-18T23:59:59Z",
            result = 100.0
        )
        val expectedCurrencyParcelableModel = CurrencyConversionParcelableModel(
            to = "RUB",
            from = from,
            amount = amount,
            date = "2024-07-18T23:59:59Z",
            result = 100.0
        )

        val actualCurrencyPM = currency.toParcelableModel()

        assertEquals(expectedCurrencyParcelableModel, actualCurrencyPM)
    }
}