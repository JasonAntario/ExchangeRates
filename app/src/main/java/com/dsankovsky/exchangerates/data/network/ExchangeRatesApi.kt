package com.dsankovsky.exchangerates.data.network

import com.dsankovsky.exchangerates.data.models.NetworkResponseDto
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface ExchangeRatesApi {
    @GET("latest")
    suspend fun getLatestExchangeRates(
        @Header(HEADER_API_KEY) apikey: String = "",
        @Query(QUERY_BASE) baseCurrency: String = DEFAULT_BASE
    ): NetworkResponseDto

    companion object {
        private const val HEADER_API_KEY = "apikey"
        private const val QUERY_BASE = "base"
        private const val DEFAULT_BASE = "usd"
    }
}