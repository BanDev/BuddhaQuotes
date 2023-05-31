package org.bandev.buddhaquotes.architecture.quotes

data class QuoteSource(
    val bodyRes: Int,
    val verse: Number? = null,
    val fullQuoteRes: Int? = null,
    val url: String? = null
)
