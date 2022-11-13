package com.dsankovsky.exchangerates.domain.usecases

import com.dsankovsky.exchangerates.domain.CurrencyListRepository

class FetchCurrencyListUseCase(
    private val repository: CurrencyListRepository
) {
    suspend operator fun invoke(baseCurrency: String) {
        repository.fetchCurrencyList(baseCurrency)
    }
}