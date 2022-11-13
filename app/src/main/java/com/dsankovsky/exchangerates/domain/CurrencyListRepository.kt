package com.dsankovsky.exchangerates.domain

import com.dsankovsky.exchangerates.domain.models.CurrencyInfo
import kotlinx.coroutines.flow.Flow

interface CurrencyListRepository {

    fun getCurrencyList(): Flow<List<CurrencyInfo>>
    suspend fun updateCurrencyInfo(currencyInfo: CurrencyInfo)
    suspend fun fetchCurrencyList(baseCurrency: String)

}