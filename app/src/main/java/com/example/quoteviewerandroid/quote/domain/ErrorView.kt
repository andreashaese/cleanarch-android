package com.example.quoteviewerandroid

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.WifiOff
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ErrorView() {
    Surface(color = MaterialTheme.colors.background) {
        Column(
            verticalArrangement = Arrangement.spacedBy(16.0.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Icon(
                Icons.Default.WifiOff,
                contentDescription = "Warning",
                modifier = Modifier.size(48.0.dp),
                tint = Color.Gray
            )
            Text("Something went wrong, please try again")
        }
    }
}

@Composable
@Preview
private fun ErrorViewPreview() {
    ErrorView()
}