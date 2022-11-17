package com.dsankovsky.exchangerates.domain.models

import com.dsankovsky.exchangerates.data.mapper.Constants

sealed class Filter{
    abstract var sortType: String
    abstract fun copy(): Filter
}

data class FilterPopular(override var sortType: String = Constants.FILTER_NAME_ASC) : Filter(){
    override fun copy(): Filter {
        return FilterPopular(sortType = sortType)
    }
}

data class FilterFavorite(override var sortType: String = Constants.FILTER_NAME_ASC) : Filter() {
    override fun copy(): Filter {
        return FilterFavorite(sortType = sortType)
    }
}