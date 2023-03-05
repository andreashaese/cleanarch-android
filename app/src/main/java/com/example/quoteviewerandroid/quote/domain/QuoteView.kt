package com.example.quoteviewerandroid.quote.domain

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun QuoteView(text: String, author: String) {
    Surface(
        modifier = Modifier.background(MaterialTheme.colors.background).padding(8.0.dp),
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(8.0.dp)) {
            Text(
                style = MaterialTheme.typography.h4,
                fontFamily = FontFamily.Serif,
                color = Color.Gray,
                text = "“",
            )
            Column(
                verticalArrangement = Arrangement.spacedBy(16.0.dp),
            ) {
                Text(
                    style = MaterialTheme.typography.h4,
                    text = text,
                    fontFamily = FontFamily.Serif,
                )
                Text(
                    modifier = Modifier.align(Alignment.End),
                    color = Color.Gray,
                    style = MaterialTheme.typography.caption,
                    fontStyle = FontStyle.Italic,
                    text = "– $author",
                )
            }
        }
    }
}

@Preview
@Composable
private fun QuoteViewPreview() {
    QuoteView(
        text = "The way to get started is to quit talking and begin doing.",
        author = "Walt Disney",
    )
}