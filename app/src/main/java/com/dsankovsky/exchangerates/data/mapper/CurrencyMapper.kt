package com.dsankovsky.exchangerates.data.mapper

import android.util.Log
import com.dsankovsky.exchangerates.data.mapper.Constants.UNDEFINED_ID
import com.dsankovsky.exchangerates.data.models.CurrencyInfoDbModel
import com.dsankovsky.exchangerates.data.models.NetworkResponseDto
import com.dsankovsky.exchangerates.domain.models.CurrencyInfo

class CurrencyMapper {

    companion object{
        private const val TAG = "Mapper"
    }

    fun mapDtoToListDbModel(responseDto: NetworkResponseDto): List<CurrencyInfoDbModel> {
        val rates = responseDto.rates.toString()
        val listOfRates = rates.substring(1, rates.length - 1).split(",")
        val currencyInfoList = listOfRates.map {
            val item = it.split(":")
            CurrencyInfoDbModel(
                id = UNDEFINED_ID,
                name = item[0].substring(1, item[0].length - 1),
                amount = item[1].toFloatOrNull() ?: 0f
            )
        }
        Log.i(TAG, "mapDtoToListDbModel: $currencyInfoList")
        return currencyInfoList
    }

    fun mapDbModelToEntity(dbModel: CurrencyInfoDbModel): CurrencyInfo {
        return CurrencyInfo(
            name = dbModel.name,
            id = dbModel.id,
            amount = dbModel.amount,
            isFavourite = dbModel.isFavourite
        )
    }

    fun mapEntityToDbModel(entity: CurrencyInfo): CurrencyInfoDbModel {
        return CurrencyInfoDbModel(
            name = entity.name,
            id = entity.id,
            amount = entity.amount,
            isFavourite = entity.isFavourite
        )
    }
}