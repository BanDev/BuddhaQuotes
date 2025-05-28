package org.bandev.buddhaquotes.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import org.bandev.buddhaquotes.R
import org.bandev.buddhaquotes.components.QuoteCard
import org.bandev.buddhaquotes.components.QuoteToolbar
import org.bandev.buddhaquotes.db.QuoteViewModel
import java.util.Calendar

@Composable
fun DailyQuoteScreen(
    viewModel: QuoteViewModel = hiltViewModel(),
) {
    val quote by viewModel.dailyQuote.collectAsState()
    val context = LocalContext.current

    Column(Modifier.padding(start = 15.dp, top = 1.dp, end = 15.dp)) {
        QuoteCard(
            quote = quote,
            date = Calendar.getInstance().time,
            onSourceClick = {})
        Column(
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {
            Image(
                painter = painterResource(R.drawable.image_daily_specials),
                contentDescription = null,
                modifier = Modifier.size(250.dp)
            )
            QuoteToolbar(
                quote = quote,
                modifier = Modifier.padding(20.dp),
                onToggleFavourite = { viewModel.toggleFavourite(it) },
                onSourceClick = {},
            )
        }
    }
}