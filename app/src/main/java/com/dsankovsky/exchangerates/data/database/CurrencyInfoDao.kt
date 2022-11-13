package com.dsankovsky.exchangerates.data.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.dsankovsky.exchangerates.data.models.CurrencyInfoDbModel
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrencyInfoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCurrencyList(currencyList: List<CurrencyInfoDbModel>)

    @Query("SELECT * FROM currency_info_list")
    suspend fun getCurrencyList(): Flow<List<CurrencyInfoDbModel>>

    @Update
    fun updateCurrencyInfo(currencyInfoDbModel: CurrencyInfoDbModel)

}