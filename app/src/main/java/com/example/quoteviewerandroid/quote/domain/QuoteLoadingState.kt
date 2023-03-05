package com.example.quoteviewerandroid.quote.domain

sealed class QuoteLoadingState {
    object NotLoaded : QuoteLoadingState()
    object Loading: QuoteLoadingState()
    data class Loaded(val text: String, val author: String): QuoteLoadingState()
    object Failed: QuoteLoadingState()
}
