package com.example.currencyconversionapp.presentation

import androidx.lifecycle.Observer
import com.example.currencyconversionapp.domain.entity.CurrencyConversion
import com.example.currencyconversionapp.domain.entity.result.RequestError
import com.example.currencyconversionapp.domain.entity.result.RequestResult
import com.example.currencyconversionapp.domain.usecase.ConversionCurrencyUseCase
import com.example.currencyconversionapp.presentation.state.ErrorEvent
import com.example.currencyconversionapp.presentation.state.ScreenState
import com.example.currencyconversionapp.extension.InstantTaskExecutorExtension
import com.example.currencyconversionapp.extension.TestCoroutineExtension
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.Mockito.mock
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.inOrder
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

@ExtendWith(MockitoExtension::class, InstantTaskExecutorExtension::class, TestCoroutineExtension::class)
class MainViewModelTest {


    private val conversionCurrencyUseCase: ConversionCurrencyUseCase = mock()
    private val viewModel = MainViewModel(conversionCurrencyUseCase)
    private val stateObserver: Observer<ScreenState<CurrencyConversion>> = mock()


    @Test
    fun `view model EXPECT initial state`() {
        viewModel.state.observeForever(stateObserver)

        verify(stateObserver).onChanged(ScreenState.Initial)
    }

    @Test
    fun `view model EXPECT content state`() = runTest {
        val from = "RUB"
        val to = "RUB"
        val amount = 1.0

        val currency = CurrencyConversion(
            to = to,
            from = from,
            amount = amount,
            date = "18.07.2024 23:59",
            result = 100.0
        )

        whenever(conversionCurrencyUseCase(from, to, amount)) doReturn(RequestResult.Success(currency))

        viewModel.state.observeForever(stateObserver)
        viewModel.conversion(from, to, amount)

        verify(stateObserver).onChanged(ScreenState.Content(currency))
    }

    @Test
    fun `view model EXPECT loading state`() {
        val from = "RUB"
        val to = "RUB"
        val amount = 1.0
        viewModel.state.observeForever(stateObserver)
        viewModel.conversion(from, to, amount)

        inOrder(stateObserver) {
            verify(stateObserver).onChanged(ScreenState.Initial)
            verify(stateObserver).onChanged(ScreenState.Loading)
        }
    }

    @Test
    fun `view model EXPECT error network state`() = runTest {
        val from = "RUB"
        val to = "RUB"
        val amount = 1.0
        whenever(conversionCurrencyUseCase(from, to, amount)) doReturn(RequestResult.Error(RequestError.NetworkError))

        viewModel.state.observeForever(stateObserver)
        viewModel.conversion(from, to, amount)

        verify(stateObserver).onChanged(ScreenState.Error(ErrorEvent.NetworkError))

    }

    @Test
    fun `view model EXPECT error server state`() = runTest {
        val from = "RUB"
        val to = "RUB"
        val amount = 1.0
        whenever(conversionCurrencyUseCase(from, to, amount)) doReturn(RequestResult.Error(RequestError.ServerError))

        viewModel.state.observeForever(stateObserver)
        viewModel.conversion(from, to, amount)

        verify(stateObserver).onChanged(ScreenState.Error(ErrorEvent.ServerError))

    }
}