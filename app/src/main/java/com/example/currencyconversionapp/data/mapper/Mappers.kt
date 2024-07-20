package com.example.currencyconversionapp.data.mapper

import android.os.Parcelable
import com.example.currencyconversionapp.data.model.ResponseDto
import com.example.currencyconversionapp.domain.entity.CurrencyConversion
import kotlinx.parcelize.Parcelize
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun ResponseDto.toEntity(amount: Double, from: String) = CurrencyConversion(
    to = data.keys.first(),
    from = from,
    amount = amount,
    date = meta.lastUpdatedAt.convertFormatDate(),
    result = data.values.first().value * amount
)

fun String.convertFormatDate(): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
    val outputFormat = SimpleDateFormat("dd.MM.yyyy HH:mm", Locale.getDefault())
    val date: Date = inputFormat.parse(this) ?: return ""
    return outputFormat.format(date)
}

@Parcelize
data class CurrencyConversionParcelableModel(
    val to: String,
    val from: String,
    val amount: Double,
    val date: String,
    val result: Double
) : Parcelable

fun CurrencyConversionParcelableModel.toEntity() = CurrencyConversion(
    to = to,
    from = from,
    amount = amount,
    date = date,
    result = result
)

fun CurrencyConversion.toParcelableModel() = CurrencyConversionParcelableModel(
    to = to,
    from = from,
    amount = amount,
    date = date,
    result = result
)