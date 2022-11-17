package com.dsankovsky.exchangerates.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dsankovsky.exchangerates.data.CurrencyListRepositoryImpl
import com.dsankovsky.exchangerates.data.mapper.Constants
import com.dsankovsky.exchangerates.domain.models.*
import com.dsankovsky.exchangerates.domain.usecases.FetchCurrencyListUseCase
import com.dsankovsky.exchangerates.domain.usecases.UpdateCurrencyInfoUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val repository: CurrencyListRepositoryImpl,
    private val fetchCurrencyListUseCase: FetchCurrencyListUseCase,
    private val updateCurrencyInfoUseCase: UpdateCurrencyInfoUseCase
) : ViewModel() {

    private val _currencyList = MutableStateFlow<List<CurrencyInfo>>(emptyList())
    val currencyList = _currencyList.asStateFlow()

    init {
        fetchCurrencyList(Constants.DEFAULT_CURRENCY)
        viewModelScope.launch {
            repository.getList().collectLatest {
                _currencyList.tryEmit(it)
            }
        }
    }

    fun updateFilter(filter: Filter) {
        repository.setFilter(filter)
    }

    fun updateFilterSorting(filter: String) {
        repository.setFilterSorting(filter)
    }

    fun fetchCurrencyList(baseCurrency: String) {
        viewModelScope.launch(Dispatchers.IO) {
            fetchCurrencyListUseCase(baseCurrency)
        }
    }

    fun updateCurrencyInfo(currencyInfo: CurrencyInfo) {
        viewModelScope.launch(Dispatchers.IO) {
            updateCurrencyInfoUseCase(currencyInfo.copy(isFavourite = !currencyInfo.isFavourite))
        }
    }
}