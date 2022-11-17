package com.dsankovsky.exchangerates.data.mapper

object Constants {

    const val UNDEFINED_ID = 0

    const val DEFAULT_CURRENCY = "usd"

    const val FILTER_NAME_ASC = "Name ASC"
    const val FILTER_NAME_DESC = "Name DESC"
    const val FILTER_AMOUNT_ASC = "Amount ASC"
    const val FILTER_AMOUNT_DESC = "Amount DESC"
    val filters =
        arrayListOf(FILTER_NAME_ASC, FILTER_NAME_DESC, FILTER_AMOUNT_ASC, FILTER_AMOUNT_DESC)
}