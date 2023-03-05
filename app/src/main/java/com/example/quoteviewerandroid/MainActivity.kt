package com.example.quoteviewerandroid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.example.quoteviewerandroid.quote.domain.QuotePage
import com.example.quoteviewerandroid.ui.theme.QuoteViewerAndroidTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            QuoteViewerAndroidTheme {
                QuotePage()
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    QuoteViewerAndroidTheme {
        Greeting("Android")
    }
}