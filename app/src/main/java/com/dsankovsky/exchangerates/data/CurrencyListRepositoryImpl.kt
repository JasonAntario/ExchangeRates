package com.dsankovsky.exchangerates.data

import android.util.Log
import com.dsankovsky.exchangerates.BuildConfig
import com.dsankovsky.exchangerates.data.database.CurrencyInfoDao
import com.dsankovsky.exchangerates.data.mapper.Constants
import com.dsankovsky.exchangerates.data.mapper.CurrencyMapper
import com.dsankovsky.exchangerates.data.network.ApiFactory
import com.dsankovsky.exchangerates.domain.CurrencyListRepository
import com.dsankovsky.exchangerates.domain.models.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class CurrencyListRepositoryImpl @Inject constructor(
    private val currencyInfoDao: CurrencyInfoDao,
    private val mapper: CurrencyMapper
) : CurrencyListRepository {

    companion object {
        private const val TAG = "RepositoryImpl"
    }

    private val apiService = ApiFactory.apiService

    private var _filterCategory = MutableStateFlow<Filter>(FilterPopular())

    fun setFilter(filter: Filter) {
        val currentFilter = _filterCategory.value
        filter.sortType = currentFilter.sortType
        _filterCategory.tryEmit(filter)
    }

    fun setFilterSorting(sorting: String) {
        val currentFilter = _filterCategory.value.copy()
        currentFilter.sortType = sorting
        Log.i(TAG, "setFilterSorting: ${currentFilter.sortType}")
        _filterCategory.tryEmit(currentFilter)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun getList(): Flow<List<CurrencyInfo>> {
        return _filterCategory.flatMapLatest { filter ->
            when (filter) {
                is FilterFavorite -> {
                    Log.i(TAG, "getList: FAV ${filter.sortType}")
                    when (filter.sortType) {
                        Constants.FILTER_NAME_ASC -> currencyInfoDao.getFavoriteCurrencyListSortedName()
                        Constants.FILTER_NAME_DESC -> currencyInfoDao.getFavoriteCurrencyListSortedNameDesc()
                        Constants.FILTER_AMOUNT_ASC -> currencyInfoDao.getFavoriteCurrencyListSortedAmount()
                        Constants.FILTER_AMOUNT_DESC -> currencyInfoDao.getFavoriteCurrencyListSortedAmountDesc()
                        else -> {
                            throw RuntimeException("Unexpected sortType in Filter")
                        }
                    }
                }
                is FilterPopular -> {
                    Log.i(TAG, "getList: POP ${filter.sortType}")
                    when (filter.sortType) {
                        Constants.FILTER_NAME_ASC -> currencyInfoDao.getCurrencyListSortedName()
                        Constants.FILTER_NAME_DESC -> currencyInfoDao.getCurrencyListSortedNameDesc()
                        Constants.FILTER_AMOUNT_ASC -> currencyInfoDao.getCurrencyListSortedAmount()
                        Constants.FILTER_AMOUNT_DESC -> currencyInfoDao.getCurrencyListSortedAmountDesc()
                        else -> {
                            throw RuntimeException("Unexpected sortType in Filter")
                        }
                    }
                }
            }
        }.map {
            Log.e(TAG, "getList: $it")
            it.map { item ->
                mapper.mapDbModelToEntity(item)
            }
        }.catch { e ->
            e.printStackTrace()
        }
    }

    override suspend fun fetchCurrencyList(baseCurrency: String) {
        try {
            val result = apiService.getLatestExchangeRates(BuildConfig.API_KEY, baseCurrency)
            val currencies = mapper.mapDtoToListDbModel(result)
            currencies.mapIndexed { index, currencyInfoDbModel ->
                currencyInfoDbModel.id = index
                val existedItem =
                    currencyInfoDao.findExistedCurrency(currencyInfoDbModel.name, index)
                existedItem?.let {
                    currencyInfoDbModel.isFavourite = it.isFavourite
                }
            }
            currencyInfoDao.insertCurrencyList(currencies)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override suspend fun updateCurrencyInfo(currencyInfo: CurrencyInfo) {
        currencyInfoDao.updateCurrencyInfo(mapper.mapEntityToDbModel(currencyInfo))
    }
}