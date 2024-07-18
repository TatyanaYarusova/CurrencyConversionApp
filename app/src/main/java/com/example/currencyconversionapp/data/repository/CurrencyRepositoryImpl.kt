package com.example.currencyconversionapp.data.repository

import com.example.currencyconversionapp.data.api.ApiService
import com.example.currencyconversionapp.data.mapper.toEntity
import com.example.currencyconversionapp.domain.entity.CurrencyConversion
import com.example.currencyconversionapp.domain.entity.result.RequestError
import com.example.currencyconversionapp.domain.entity.result.RequestResult
import com.example.currencyconversionapp.domain.repository.CurrencyRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import javax.inject.Inject
import javax.net.ssl.SSLHandshakeException

class CurrencyRepositoryImpl @Inject constructor(
    private val apiService: ApiService,
    private val ioDispatcher: CoroutineDispatcher,
    private val key: String
): CurrencyRepository {

    override suspend fun conversion(
        from: String,
        to: String,
        amount: Double
    ): RequestResult<CurrencyConversion> =
        withContext(ioDispatcher) {
            val response = try {
                apiService.getCurrencyRate(key, from, to)
            } catch (e: Exception) {
                return@withContext exceptionRequest(e)
            }
            return@withContext if (response.isSuccessful) {
                val currencyConversion = response.body() ?: throw RuntimeException("Response body is null")
                RequestResult.Success(currencyConversion.toEntity(amount, from))
            } else when (val code = response.code()) {
                in 500..599 -> RequestResult.Error(RequestError.ServerError)
                else -> throw RuntimeException("Unknown error code: $code")
            }
        }


    private fun <T> exceptionRequest(e: Exception): RequestResult<T> =
        when (e) {
            is UnknownHostException -> RequestResult.Error(RequestError.NetworkError)
            is SocketTimeoutException -> RequestResult.Error(RequestError.NetworkError)
            is SSLHandshakeException -> RequestResult.Error(RequestError.ServerError)
            else -> throw e
        }

}