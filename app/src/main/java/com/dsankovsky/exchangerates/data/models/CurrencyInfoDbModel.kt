package com.dsankovsky.exchangerates.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "currency_info_list")
data class CurrencyInfoDbModel(
    @PrimaryKey
    var id: Int,
    var name: String,
    var amount: Float,
    var isFavourite: Boolean = false
)