package com.dsankovsky.exchangerates.domain.usecases

import com.dsankovsky.exchangerates.domain.CurrencyListRepository
import javax.inject.Inject

class FetchCurrencyListUseCase @Inject constructor(
    private val repository: CurrencyListRepository
) {
    suspend operator fun invoke(baseCurrency: String) {
        repository.fetchCurrencyList(baseCurrency)
    }
}