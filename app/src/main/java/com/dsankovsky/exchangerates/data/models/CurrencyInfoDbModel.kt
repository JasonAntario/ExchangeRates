package com.dsankovsky.exchangerates.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "currency_info_list")
data class CurrencyInfoDbModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val name: String,
    val amount: Float,
    val isFavourite: Boolean = false
)
