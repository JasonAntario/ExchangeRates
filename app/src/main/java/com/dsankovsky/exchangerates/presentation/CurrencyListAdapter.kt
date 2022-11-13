package com.dsankovsky.exchangerates.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.dsankovsky.exchangerates.databinding.ItemCurrencyInfoBinding
import com.dsankovsky.exchangerates.domain.models.CurrencyInfo

class CurrencyListAdapter :
    ListAdapter<CurrencyInfo, CurrencyListViewHolder>(CurrencyInfoDiffCallback) {

    var onFavoriteClickListener: ((CurrencyInfo) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyListViewHolder {
        val binding = ItemCurrencyInfoBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CurrencyListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CurrencyListViewHolder, position: Int) {
        val item = getItem(position)
        holder.name.text = item.name
        holder.amount.text = item.amount.toString()
        holder.isFavorite.isChecked = item.isFavourite
        holder.isFavorite.setOnCheckedChangeListener(null)
        holder.isFavorite.setOnClickListener {
            onFavoriteClickListener?.invoke(item)
        }
    }
}