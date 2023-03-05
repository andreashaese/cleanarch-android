package com.example.quoteviewerandroid.quote.domain

import java.net.URL

interface HttpFetcher {
    fun get(url: URL): HttpResponse
}