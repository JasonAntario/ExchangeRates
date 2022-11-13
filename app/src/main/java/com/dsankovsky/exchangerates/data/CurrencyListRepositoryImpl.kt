package com.dsankovsky.exchangerates.data

import android.app.Application
import com.dsankovsky.exchangerates.BuildConfig
import com.dsankovsky.exchangerates.data.database.AppDatabase
import com.dsankovsky.exchangerates.data.mapper.CurrencyMapper
import com.dsankovsky.exchangerates.data.network.ApiFactory
import com.dsankovsky.exchangerates.domain.CurrencyListRepository
import com.dsankovsky.exchangerates.domain.models.CurrencyInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CurrencyListRepositoryImpl(
    application: Application
) : CurrencyListRepository {

    private val currencyInfoDao = AppDatabase.getInstance(application).currencyInfoDao()
    private val apiService = ApiFactory.apiService
    private val mapper = CurrencyMapper()

    override suspend fun fetchCurrencyList(baseCurrency: String) {
        try {
            val result = apiService.getLatestExchangeRates(BuildConfig.API_KEY, baseCurrency)
            currencyInfoDao.insertCurrencyList(mapper.mapDtoToListDbModel(result))
        } catch (e: Exception){
            e.printStackTrace()
        }
    }

    override fun getCurrencyList(): Flow<List<CurrencyInfo>> {
        return currencyInfoDao.getCurrencyList().map {
            it.map { item ->
                mapper.mapDbModelToEntity(item)
            }
        }
    }

    override suspend fun updateCurrencyInfo(currencyInfo: CurrencyInfo) {
        currencyInfoDao.updateCurrencyInfo(mapper.mapEntityToDbModel(currencyInfo))
    }

}