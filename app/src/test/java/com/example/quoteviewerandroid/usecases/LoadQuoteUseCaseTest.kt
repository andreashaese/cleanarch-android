package com.example.quoteviewerandroid.usecases

import com.example.quoteviewerandroid.quote.entities.Quote
import com.example.quoteviewerandroid.quote.entities.QuoteRepository
import com.example.quoteviewerandroid.quote.usecases.LoadQuote
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.fail
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class LoadQuoteUseCaseTest {

    class SucceedingTestQuoteRepository(private val text: String, private val author: String) :
        QuoteRepository {
        override suspend fun getQuote() = Quote(text = text, author = author)
    }

    class FailingTestQuoteRepository() : QuoteRepository {
        override suspend fun getQuote() = throw Exception("Some exception")
    }

    @Test
    fun loadQuoteUseCaseReturnsQuoteFromRepository() = runTest {
        val repository = SucceedingTestQuoteRepository(text = "Some text", author = "Some author")
        val loadQuoteUseCase = LoadQuote(quoteRepository = repository)

        val quote = loadQuoteUseCase()

        assertEquals(quote.text, "Some text")
        assertEquals(quote.author, "Some author")
    }

    // TODO: Use `assertThrows<Exception>` from JUnit5
    @Test
    fun loadQuoteUseCaseThrowsWhenRepositoryThrows() = runTest {
        val loadQuoteUseCase = LoadQuote(quoteRepository = FailingTestQuoteRepository())

        try {
            val quote = loadQuoteUseCase()
            fail("Should throw exception")
        } catch (_: Exception) {}
    }

}