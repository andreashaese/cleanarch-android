package com.example.quoteviewerandroid.quote.domain

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import java.net.URL

@OptIn(ExperimentalCoroutinesApi::class)
class ApiQuoteRepositoryUnitTest {

    private class SucceedingTestHttpFetcher(private val response: HttpResponse): HttpFetcher {
        private var _fetchedUrl: URL? = null
        val fetchedURL: URL?
            get() = _fetchedUrl

        override fun get(url: URL): HttpResponse {
            _fetchedUrl = url
            return response
        }
    }

    private class FailingTestHttpFetcher: HttpFetcher {
        override fun get(url: URL): HttpResponse {
            throw Exception("Fetching failed")
        }
    }

    @Test
    fun repositoryReturnsQuoteFromApi() = runTest {
        val jsonData =
            """{"quote": "This is a quote", "author": "This is an author"}""".byteInputStream()
        val response = HttpResponse(statusCode = 200, data = jsonData)
        val testFetcher = SucceedingTestHttpFetcher(response)
        val repository = ApiQuoteRepository(fetcher = testFetcher)

        val quote = repository.getQuote()

        assertEquals("This is a quote", quote.text)
        assertEquals("This is an author", quote.author)
    }

    @Test
    fun repositoryFetchesCorrectUrl() = runTest {
        val jsonData = """{"quote": "", "author": ""}""".byteInputStream()
        val response = HttpResponse(statusCode = 200, data = jsonData)
        val testFetcher = SucceedingTestHttpFetcher(response)
        val repository = ApiQuoteRepository(fetcher = testFetcher)

        repository.getQuote()

        assertEquals("https://dummyjson.com/quotes/random", testFetcher.fetchedURL?.toString())
    }

    // TODO: Use `@ParameterizedTest` and `assertThrow<Exception>` from JUnit5
    @Test
    fun repositoryThrowsExceptionForInvalidStatusCodes() = runTest {
        val statusCodes = listOf(100, 199, 300, 400, 401, 402, 403, 500)
        val jsonData = """{"quote": "", "author": ""}""".byteInputStream()

        for (statusCode in statusCodes) {
            val response = HttpResponse(statusCode = statusCode, data = jsonData)
            val testFetcher = SucceedingTestHttpFetcher(response)
            val repository = ApiQuoteRepository(fetcher = testFetcher)

            try {
                repository.getQuote()
                fail("Should throw exception for status code $statusCode")
            } catch (_: Exception) {}
        }
    }

    // TODO: Use `assertThrow<Exception>` from JUnit5 instead of try/catch
    @Test
    fun repositoryThrowsExceptionForInvalidResponse() = runTest {
        val jsonData = """{"error": "This response is malformed"}""".byteInputStream()
        val response = HttpResponse(statusCode = 200, data = jsonData)
        val testFetcher = SucceedingTestHttpFetcher(response)
        val repository = ApiQuoteRepository(fetcher = testFetcher)

        try {
            repository.getQuote()
            fail("Should throw exception")
        } catch (_: Exception) {}
    }

    // TODO: Use `assertThrow<Exception>` from JUnit5 instead of try/catch
    @Test
    fun repositoryThrowsErrorWhenFetcherThrowsException() = runTest {
        val repository = ApiQuoteRepository(fetcher = FailingTestHttpFetcher())

        try {
            repository.getQuote()
            fail("Should throw exception")
        } catch (_: Exception) {}
    }
}