package com.example.currencyconversionapp.domain.usecase

import com.example.currencyconversionapp.domain.entity.CurrencyConversion
import com.example.currencyconversionapp.domain.entity.result.RequestResult
import com.example.currencyconversionapp.domain.repository.CurrencyRepository
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito.mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.whenever

@ExtendWith(MockitoExtension::class)
class ConversionCurrencyUseCaseTest {
    private val repo: CurrencyRepository = mock()

    private val useCase = ConversionCurrencyUseCase(repo)

    @Test
    fun `invoke EXPECT conversion currency`() = runTest {
        val amount = 100.0
        val from = "RUB"
        val to = "RUB"

        val expectedCurrency = CurrencyConversion(
            to = to,
            from = from,
            amount = amount,
            date = "18.07.2024 23:59",
            result = 100.0
        )

        whenever(repo.conversion(from, to, amount)) doReturn RequestResult.Success(expectedCurrency)

        val actualCurrency = useCase(from, to, amount)

        assertEquals(RequestResult.Success(expectedCurrency), actualCurrency)
    }

}