package com.dsankovsky.exchangerates.domain.usecases

import com.dsankovsky.exchangerates.domain.CurrencyListRepository
import com.dsankovsky.exchangerates.domain.models.CurrencyInfo
import javax.inject.Inject

class UpdateCurrencyInfoUseCase @Inject constructor(
    private val repository: CurrencyListRepository
) {
    suspend operator fun invoke(currencyInfo: CurrencyInfo) =
        repository.updateCurrencyInfo(currencyInfo)
}