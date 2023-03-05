package com.example.quoteviewerandroid.quote.entities

interface QuoteRepository {
    suspend fun getQuote(): Quote
}