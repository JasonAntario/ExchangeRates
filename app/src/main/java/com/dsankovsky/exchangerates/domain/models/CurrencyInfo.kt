package com.dsankovsky.exchangerates.domain.models

import com.dsankovsky.exchangerates.data.mapper.Constants.UNDEFINED_ID

data class CurrencyInfo(
    val id: Int = UNDEFINED_ID,
    val name: String,
    val amount: Float,
    val isFavourite: Boolean = false
)