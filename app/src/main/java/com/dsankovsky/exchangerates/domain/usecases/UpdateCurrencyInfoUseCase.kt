package com.dsankovsky.exchangerates.domain.usecases

import com.dsankovsky.exchangerates.domain.CurrencyListRepository
import com.dsankovsky.exchangerates.domain.models.CurrencyInfo

class UpdateCurrencyInfoUseCase(
    private val repository: CurrencyListRepository
) {
    operator fun invoke(currencyInfo: CurrencyInfo) = repository.updateCurrencyInfo(currencyInfo)
}