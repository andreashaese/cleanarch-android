package com.example.quoteviewerandroid.quote.domain

import com.example.quoteviewerandroid.quote.drivers.URLConnectionHttpFetcher
import com.example.quoteviewerandroid.quote.entities.Quote
import com.example.quoteviewerandroid.quote.entities.QuoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import java.net.URL

class ApiQuoteRepository(private val fetcher: HttpFetcher = URLConnectionHttpFetcher()) :
    QuoteRepository {
    @Serializable
    private data class QuoteResponse(val quote: String, val author: String) {
        fun toQuote() = Quote(text = quote, author = author)
    }

    @OptIn(ExperimentalSerializationApi::class)
    override suspend fun getQuote(): Quote = withContext(Dispatchers.IO) {
        val url = URL("https://dummyjson.com/quotes/random")

        val response = fetcher.get(url)

        if (!(200..299).contains(response.statusCode)) {
            throw Exception("Invalid response")
        }

        val json = Json { ignoreUnknownKeys = true }
        val result: QuoteResponse = json.decodeFromStream(response.data)

        result.toQuote()
    }
}