package com.dsankovsky.exchangerates.domain

import com.dsankovsky.exchangerates.domain.models.CurrencyInfo

interface CurrencyListRepository {

    suspend fun updateCurrencyInfo(currencyInfo: CurrencyInfo)
    suspend fun fetchCurrencyList(baseCurrency: String)

}