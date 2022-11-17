package com.dsankovsky.exchangerates.data.database

import androidx.room.*
import com.dsankovsky.exchangerates.data.models.CurrencyInfoDbModel
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrencyInfoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCurrencyList(currencyList: List<CurrencyInfoDbModel>)

    @Query("SELECT * FROM currency_info_list WHERE name=:name AND id=:id")
    fun findExistedCurrency(name: String, id: Int): CurrencyInfoDbModel?

    @Query("SELECT * FROM currency_info_list ORDER BY name ASC")
    fun getCurrencyListSortedName(): Flow<List<CurrencyInfoDbModel>>

    @Query("SELECT * FROM currency_info_list ORDER BY name DESC")
    fun getCurrencyListSortedNameDesc(): Flow<List<CurrencyInfoDbModel>>

    @Query("SELECT * FROM currency_info_list ORDER BY amount ASC")
    fun getCurrencyListSortedAmount(): Flow<List<CurrencyInfoDbModel>>

    @Query("SELECT * FROM currency_info_list ORDER BY amount DESC")
    fun getCurrencyListSortedAmountDesc(): Flow<List<CurrencyInfoDbModel>>

    @Query("SELECT * FROM currency_info_list WHERE isFavourite=1 ORDER BY name ASC")
    fun getFavoriteCurrencyListSortedName(): Flow<List<CurrencyInfoDbModel>>

    @Query("SELECT * FROM currency_info_list WHERE isFavourite=1 ORDER BY name DESC")
    fun getFavoriteCurrencyListSortedNameDesc(): Flow<List<CurrencyInfoDbModel>>

    @Query("SELECT * FROM currency_info_list WHERE isFavourite=1 ORDER BY amount ASC")
    fun getFavoriteCurrencyListSortedAmount(): Flow<List<CurrencyInfoDbModel>>

    @Query("SELECT * FROM currency_info_list WHERE isFavourite=1 ORDER BY amount DESC")
    fun getFavoriteCurrencyListSortedAmountDesc(): Flow<List<CurrencyInfoDbModel>>

    @Update
    fun updateCurrencyInfo(currencyInfoDbModel: CurrencyInfoDbModel)

}