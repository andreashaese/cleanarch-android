package com.example.quoteviewerandroid.quote.usecases

import com.example.quoteviewerandroid.quote.domain.ApiQuoteRepository
import com.example.quoteviewerandroid.quote.entities.Quote
import com.example.quoteviewerandroid.quote.entities.QuoteRepository

interface LoadQuoteUseCase {
    suspend operator fun invoke(): Quote
}

class LoadQuote(private val quoteRepository: QuoteRepository = ApiQuoteRepository()) :
    LoadQuoteUseCase {
    override suspend fun invoke(): Quote = quoteRepository.getQuote()
}