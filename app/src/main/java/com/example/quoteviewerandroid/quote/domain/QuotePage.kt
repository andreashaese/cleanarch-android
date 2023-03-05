package com.example.quoteviewerandroid.quote.domain

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch

@Composable
fun QuotePage(viewModel: QuotePageViewModel = viewModel()) {
    val coroutineScope = rememberCoroutineScope()
    val loadingState by viewModel.loadingState

    LaunchedEffect(null) {
        viewModel.reload()
    }

    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colors.background)
            .padding(16.0.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(8.0.dp),
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .weight(1.0f)
                    .fillMaxSize(),
            ) {
                when(loadingState) {
                    QuoteLoadingState.NotLoaded -> Text("Not loaded")
                    QuoteLoadingState.Loading -> CircularProgressIndicator()
                    is QuoteLoadingState.Loaded -> {
                        val loadedState = loadingState as QuoteLoadingState.Loaded
                        QuoteView(text = loadedState.text, author = loadedState.author)
                    }
                    QuoteLoadingState.Failed -> Text("Error")
                }
            }
            Button(
                enabled = loadingState != QuoteLoadingState.Loading,
                onClick = {
                    coroutineScope.launch {
                        viewModel.reload()
                    }
                },
            ) {
                Row(horizontalArrangement = Arrangement.spacedBy(8.0.dp)) {
                    Icon(Icons.Default.Refresh, contentDescription = "Next quote")
                    Text("Next quote")
                }
            }
        }
    }
}

@Preview
@Composable
private fun QuotePagePreview() {
    QuotePage()
}