package com.example.quoteviewerandroid.quote.domain

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.quoteviewerandroid.quote.usecases.LoadQuote
import com.example.quoteviewerandroid.quote.usecases.LoadQuoteUseCase
import kotlinx.coroutines.launch

class QuotePageViewModel(private val loadQuoteUseCase: LoadQuoteUseCase = LoadQuote()) :
    ViewModel() {
    private val _loadingState: MutableState<QuoteLoadingState> =
        mutableStateOf(QuoteLoadingState.NotLoaded)
    val loadingState: State<QuoteLoadingState> = _loadingState

    suspend fun reload() {
        _loadingState.value = QuoteLoadingState.Loading

        viewModelScope.launch {
            try {
                val quote = loadQuoteUseCase()
                _loadingState.value =
                    QuoteLoadingState.Loaded(text = quote.text, author = quote.author)
            } catch (_: Exception) {
                _loadingState.value = QuoteLoadingState.Failed
            }
        }
    }
}