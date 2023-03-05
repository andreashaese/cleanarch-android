package com.example.quoteviewerandroid.quote.domain

import java.io.InputStream

data class HttpResponse(val statusCode: Int, val data: InputStream)