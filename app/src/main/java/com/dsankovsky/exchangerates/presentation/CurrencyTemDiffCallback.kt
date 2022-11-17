package com.dsankovsky.exchangerates.presentation

import androidx.recyclerview.widget.DiffUtil
import com.dsankovsky.exchangerates.domain.models.CurrencyInfo

object CurrencyInfoDiffCallback : DiffUtil.ItemCallback<CurrencyInfo>() {

    override fun areItemsTheSame(oldItem: CurrencyInfo, newItem: CurrencyInfo): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: CurrencyInfo, newItem: CurrencyInfo): Boolean {
        return oldItem == newItem
    }
}