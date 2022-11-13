package com.dsankovsky.exchangerates.presentation

import androidx.recyclerview.widget.RecyclerView
import com.dsankovsky.exchangerates.databinding.ItemCurrencyInfoBinding

class CurrencyListViewHolder(binding: ItemCurrencyInfoBinding) :
    RecyclerView.ViewHolder(binding.root) {

    val name = binding.itemCurrencyName
    val amount = binding.itemCurrencyAmount
    val isFavorite = binding.itemCurrencyFavorites
}