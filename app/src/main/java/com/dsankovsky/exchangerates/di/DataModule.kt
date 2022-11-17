package com.dsankovsky.exchangerates.di

import android.app.Application
import com.dsankovsky.exchangerates.data.CurrencyListRepositoryImpl
import com.dsankovsky.exchangerates.data.database.AppDatabase
import com.dsankovsky.exchangerates.data.database.CurrencyInfoDao
import com.dsankovsky.exchangerates.domain.CurrencyListRepository
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
interface DataModule {

    @Binds
    fun bindCurrencyListRepository(impl: CurrencyListRepositoryImpl): CurrencyListRepository

    companion object{

        @Provides
        fun provideCurrencyInfoDao(application: Application): CurrencyInfoDao{
            return AppDatabase.getInstance(application).currencyInfoDao()
        }
    }
}