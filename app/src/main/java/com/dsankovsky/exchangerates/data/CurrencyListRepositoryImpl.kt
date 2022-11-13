package com.dsankovsky.exchangerates.data

import android.content.Context
import com.dsankovsky.exchangerates.BuildConfig
import com.dsankovsky.exchangerates.data.database.AppDatabase
import com.dsankovsky.exchangerates.data.mapper.CurrencyMapper
import com.dsankovsky.exchangerates.data.network.ApiFactory
import com.dsankovsky.exchangerates.domain.CurrencyListRepository
import com.dsankovsky.exchangerates.domain.models.CurrencyInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CurrencyListRepositoryImpl(
    private val mapper: CurrencyMapper,
    private val context: Context
) : CurrencyListRepository {

    private val currencyInfoDao = AppDatabase.getInstance(context).currencyInfoDao()
    private val apiService = ApiFactory.apiService

    override suspend fun fetchCurrencyList(baseCurrency: String) {
        val result = apiService.getLatestExchangeRates(BuildConfig.API_KEY, baseCurrency)
        currencyInfoDao.insertCurrencyList(mapper.mapDtoToListDbModel(result))
    }

    override suspend fun getCurrencyList(): Flow<List<CurrencyInfo>> {
        return currencyInfoDao.getCurrencyList().map {
            it.map { item ->
                mapper.mapDbModelToEntity(item)
            }
        }
    }

    override fun updateCurrencyInfo(currencyInfo: CurrencyInfo) {
        currencyInfoDao.updateCurrencyInfo(mapper.mapEntityToDbModel(currencyInfo))
    }

}