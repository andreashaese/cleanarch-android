package com.example.quoteviewerandroid.quote.drivers

import com.example.quoteviewerandroid.quote.domain.HttpFetcher
import com.example.quoteviewerandroid.quote.domain.HttpResponse
import java.net.HttpURLConnection
import java.net.URL

class URLConnectionHttpFetcher() : HttpFetcher {
    override fun get(url: URL): HttpResponse {
        (url.openConnection() as? HttpURLConnection)?.run {
            requestMethod = "GET"

            return HttpResponse(statusCode = responseCode, data = inputStream)
        }

        throw Exception("Cannot open HttpURLConnection")
    }
}