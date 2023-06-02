package org.bandev.buddhaquotes.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AddCircleOutline
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.launch
import org.bandev.buddhaquotes.FavoriteButton
import org.bandev.buddhaquotes.R
import org.bandev.buddhaquotes.architecture.lists.ListViewModel
import org.bandev.buddhaquotes.architecture.quotes.QuoteViewModel
import org.bandev.buddhaquotes.items.QuoteItem
import java.text.DateFormat
import java.util.Calendar

@Composable
fun DailyQuoteScreen(
    quoteViewModel: QuoteViewModel = hiltViewModel(),
    listViewModel: ListViewModel = hiltViewModel()
) {
    val quote by quoteViewModel.dailyQuote.observeAsState(QuoteItem())
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    Column(Modifier.padding(start = 15.dp, top = 1.dp, end = 15.dp)) {
        ElevatedCard(Modifier.fillMaxWidth()) {
            Column(Modifier.padding(20.dp)) {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(bottom = 10.dp)) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_left_quote),
                        contentDescription = null,
                    )
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.End
                    ) {
                        Text(text = DateFormat.getDateInstance(DateFormat.FULL).format(Calendar.getInstance().time))
                    }
                }
                Text(text = stringResource(quote.resource))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 10.dp),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_right_quote),
                        contentDescription = null,
                    )
                }
                Text(text = stringResource(R.string.attribution_buddha))
            }
        }
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
                modifier = Modifier.padding(20.dp),
                shape = RoundedCornerShape(20.dp)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    IconButton(
                        onClick = {
                            context.shareQuote(quote = quote)
                        },
                        modifier = Modifier.padding(5.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.Share,
                            contentDescription = null
                        )
                    }
                    FavoriteButton(
                        checked = quote.isLiked,
                        onClick = {
                            scope.launch {
                                listViewModel.setLiked(quote.id, quote.isLiked)
                            }
                        },
                        modifier = Modifier.padding(5.dp)
                    )
                    IconButton(
                        onClick = {},
                        modifier = Modifier.padding(5.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.AddCircleOutline,
                            contentDescription = null
                        )
                    }
                }
            }
        }
    }
}