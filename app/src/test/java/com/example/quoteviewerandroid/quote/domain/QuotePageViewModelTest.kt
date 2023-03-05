package com.example.quoteviewerandroid.quote.domain

import com.example.quoteviewerandroid.quote.entities.Quote
import com.example.quoteviewerandroid.quote.usecases.LoadQuoteUseCase
import kotlinx.coroutines.*
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class QuotePageViewModelTest {

    private class SucceedingTestLoadQuote(private val text: String = "", private val author: String = "") :
        LoadQuoteUseCase {
        override suspend fun invoke() = Quote(text = text, author = author)
    }

    private class FailingTestLoadQuote : LoadQuoteUseCase {
        override suspend fun invoke() = throw Exception("Some exception")
    }

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun viewModelHasNotLoadedQuoteInitially() {
        val viewModel = QuotePageViewModel(loadQuoteUseCase = SucceedingTestLoadQuote())
        assertEquals(QuoteLoadingState.NotLoaded, viewModel.loadingState.value)
    }

    @Test
    fun viewModelIsLoadingWhenUseCaseIsInvoked() = runTest {
        val viewModel = QuotePageViewModel(loadQuoteUseCase = SucceedingTestLoadQuote())

        viewModel.reload()

        assertEquals(QuoteLoadingState.Loading, viewModel.loadingState.value)
    }

    @Test
    fun viewModelHasLoadedQuoteAfterReload() = runTest {
        val viewModel = QuotePageViewModel(
            loadQuoteUseCase = SucceedingTestLoadQuote(
                text = "A quote text", author = "A quote author"
            )
        )

        viewModel.reload()
        advanceUntilIdle()

        assertEquals(
            QuoteLoadingState.Loaded(text = "A quote text", author = "A quote author"),
            viewModel.loadingState.value
        )
    }

    @Test
    fun viewModelIsInErrorStateWhenUseCaseFails() = runTest {
        val viewModel = QuotePageViewModel(loadQuoteUseCase = FailingTestLoadQuote())

        viewModel.reload()
        advanceUntilIdle()

        assertEquals(QuoteLoadingState.Failed, viewModel.loadingState.value)
    }

}