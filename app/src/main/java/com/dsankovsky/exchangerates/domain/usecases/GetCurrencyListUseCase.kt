package com.dsankovsky.exchangerates.domain.usecases

import com.dsankovsky.exchangerates.domain.CurrencyListRepository

class GetCurrencyListUseCase(
    private val repository: CurrencyListRepository
) {
    operator fun invoke() = repository.getCurrencyList()
}