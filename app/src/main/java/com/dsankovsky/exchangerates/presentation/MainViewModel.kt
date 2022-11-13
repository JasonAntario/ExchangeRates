package com.dsankovsky.exchangerates.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.dsankovsky.exchangerates.data.CurrencyListRepositoryImpl
import com.dsankovsky.exchangerates.domain.models.CurrencyInfo
import com.dsankovsky.exchangerates.domain.usecases.FetchCurrencyListUseCase
import com.dsankovsky.exchangerates.domain.usecases.GetCurrencyListUseCase
import com.dsankovsky.exchangerates.domain.usecases.UpdateCurrencyInfoUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class MainViewModel(application: Application): AndroidViewModel(application) {

    private val repository = CurrencyListRepositoryImpl(application)

    private val fetchCurrencyListUseCase = FetchCurrencyListUseCase(repository)
    private val getCurrencyListUseCase = GetCurrencyListUseCase(repository)
    private val updateCurrencyInfoUseCase = UpdateCurrencyInfoUseCase(repository)

    val currencyList = getCurrencyListUseCase()

    val stateF = MutableStateFlow<List<CurrencyInfo>>(emptyList())

    init {
//        fetchCurrencyList("usd")
        viewModelScope.launch {
            getCurrencyListUseCase().collectLatest {
                stateF.value = it
            }
        }
    }

    private fun fetchCurrencyList(baseCurrency: String){
        viewModelScope.launch(Dispatchers.IO) {
            fetchCurrencyListUseCase(baseCurrency)
        }
    }

    fun updateCurrencyInfo(currencyInfo: CurrencyInfo){
        viewModelScope.launch(Dispatchers.IO) {
            updateCurrencyInfoUseCase(currencyInfo.copy(isFavourite = !currencyInfo.isFavourite))
        }
    }
}