package org.bandev.buddhaquotes.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Attribution
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import org.bandev.buddhaquotes.FavoriteButton
import org.bandev.buddhaquotes.R
import org.bandev.buddhaquotes.db.QuoteViewModel
import org.bandev.buddhaquotes.items.QuoteCard
import org.bandev.buddhaquotes.model.Quote
import org.bandev.buddhaquotes.model.Source
import java.util.Calendar

@Composable
fun DailyQuoteScreen(
    viewModel: QuoteViewModel = hiltViewModel(),
) {
    val quote by viewModel.dailyQuote.collectAsState()
    val context = LocalContext.current

    Column(Modifier.padding(start = 15.dp, top = 1.dp, end = 15.dp)) {
        QuoteCard(
            quote = quote ?: Quote(0, "", Source("", url = "")),
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
            ElevatedCard(
                modifier = Modifier
                    .padding(20.dp)
                    .navigationBarsPadding(),
                shape = RoundedCornerShape(20.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(
                        5.dp,
                        Alignment.CenterHorizontally
                    )
                ) {
                    IconButton(
                        onClick = {
                            quote?.let { context.shareQuote(it) }
                        },
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Share,
                            contentDescription = null
                        )
                    }
                    FavoriteButton(
                        checked = quote?.isLiked == true,
                        onClick = {
                            quote?.let { viewModel.toggleFavourite(it) }
                        }
                    )
                    IconButton(onClick = {}) {
                        Icon(
                            imageVector = Icons.Rounded.Attribution,
                            contentDescription = null
                        )
                    }
                }
            }
        }
    }
}