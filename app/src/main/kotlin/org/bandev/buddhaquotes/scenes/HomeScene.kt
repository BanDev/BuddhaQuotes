package org.bandev.buddhaquotes.scenes

import android.content.Context
import android.content.Intent
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FormatListBulleted
import androidx.compose.material.icons.filled.FormatQuote
import androidx.compose.material.icons.filled.SelfImprovement
import androidx.compose.material.icons.outlined.FormatListBulleted
import androidx.compose.material.icons.outlined.FormatQuote
import androidx.compose.material.icons.outlined.SelfImprovement
import androidx.compose.material.icons.rounded.AddCircleOutline
import androidx.compose.material.icons.rounded.ChevronLeft
import androidx.compose.material.icons.rounded.ChevronRight
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import kotlin.random.Random
import kotlinx.coroutines.launch
import org.bandev.buddhaquotes.FavoriteButton
import org.bandev.buddhaquotes.R
import org.bandev.buddhaquotes.architecture.BuddhaQuotesViewModel
import org.bandev.buddhaquotes.architecture.quotes.QuoteStore
import org.bandev.buddhaquotes.items.AnimatedHeart
import org.bandev.buddhaquotes.items.Heart
import org.bandev.buddhaquotes.items.QuoteItem

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScene(navController: NavController, pagerState: PagerState, viewModel: BuddhaQuotesViewModel = viewModel()) {
    val quote by viewModel.selectedQuote.collectAsStateWithLifecycle(QuoteItem())
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val hearts = remember { mutableStateListOf<Heart>() }

    Scaffold(
        bottomBar = {
            NavigationBar {
                listOf(
                    Triple("Quotes", Icons.Filled.FormatQuote, Icons.Outlined.FormatQuote),
                    Triple("Lists", Icons.Filled.FormatListBulleted, Icons.Outlined.FormatListBulleted),
                    Triple("Meditation", Icons.Filled.SelfImprovement, Icons.Outlined.SelfImprovement)
                ).forEachIndexed { index, (label, iconFilled, iconOutlined) ->
                    NavigationBarItem(
                        icon = { Icon(if (pagerState.currentPage == index) iconFilled else iconOutlined, contentDescription = label) },
                        label = { Text(label) },
                        selected = pagerState.currentPage == index,
                        onClick = {
                            scope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        }
                    )
                }
            }
        }
    ) { paddingValues ->
        HorizontalPager(
            pageCount = 3,
            beyondBoundsPageCount = 2,
            modifier = Modifier.padding(bottom = paddingValues.calculateBottomPadding()),
            state = pagerState
        ) { page ->
            when (page) {
                0 -> {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .pointerInput(Unit) {
                                detectTapGestures(
                                    onDoubleTap = { offset ->
                                        if (!quote.isLiked) {
                                            scope.launch {
                                                viewModel.toggleLikedOnSelectedQuote()
                                                viewModel
                                                    .Quotes()
                                                    .setLike(quote.id, !quote.isLiked)
                                            }
                                        }
                                        hearts += Heart(
                                            position = offset,
                                            rotation = Random.nextFloat() * 40 - 20,
                                            size = Animatable(0f),
                                            alpha = Animatable(1f)
                                        )
                                    }
                                )
                            }
                    ) {
                        hearts.forEachIndexed { index, heart ->
                            AnimatedHeart(heart) {
                                hearts.removeAt(index)
                            }
                        }
                    }
                    Column(Modifier.padding(start = 15.dp, top = 1.dp, end = 15.dp)) {
                        ElevatedCard(Modifier.fillMaxWidth()) {
                            Column(Modifier.padding(20.dp)) {
                                Image(
                                    painter = painterResource(id = R.drawable.ic_left_quote),
                                    contentDescription = null
                                )
                                AnimatedContent(targetState = quote.resource) {
                                    Text(text = stringResource(it))
                                }
                                Box(
                                    modifier = Modifier.fillMaxWidth(),
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
                                painter = painterResource(id = R.drawable.image_anahata),
                                contentDescription = null,
                                modifier = Modifier.size(250.dp)
                            )
                            ElevatedCard(Modifier.padding(20.dp)) {
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.spacedBy(5.dp, Alignment.CenterHorizontally)
                                ) {
                                    IconButton(
                                        onClick = {
                                            val id = if (quote.id - 1 > QuoteStore.quotes.size) {
                                                1
                                            } else if (quote.id - 1 < 1) {
                                                QuoteStore.quotes.size
                                            } else {
                                                quote.id - 1
                                            }
                                            scope.launch {
                                                viewModel.setNewQuote(viewModel.Quotes().get(id))
                                            }
                                        }
                                    ) {
                                        Icon(
                                            imageVector = Icons.Rounded.ChevronLeft,
                                            contentDescription = null
                                        )
                                    }
                                    IconButton(
                                        onClick = { context.shareQuote(quote = quote) }
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
                                                viewModel.toggleLikedOnSelectedQuote()
                                                viewModel.Quotes().setLike(quote.id, !quote.isLiked)
                                            }
                                        }
                                    )
                                    IconButton(onClick = {}) {
                                        Icon(
                                            imageVector = Icons.Rounded.AddCircleOutline,
                                            contentDescription = null
                                        )
                                    }
                                    IconButton(
                                        onClick = {
                                            val id = if (quote.id + 1 > QuoteStore.quotes.size) {
                                                1
                                            } else if (quote.id + 1 < 1) {
                                                QuoteStore.quotes.size
                                            } else {
                                                quote.id + 1
                                            }
                                            scope.launch {
                                                viewModel.setNewQuote(viewModel.Quotes().get(id))
                                            }
                                        }
                                    ) {
                                        Icon(
                                            imageVector = Icons.Rounded.ChevronRight,
                                            contentDescription = null
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
                1 -> ListsScene(navController = navController)
                else -> MeditateScene()
            }
        }
    }
}

fun Context.shareQuote(quote: QuoteItem) = Intent().apply {
    action = Intent.ACTION_SEND
    putExtra(
        Intent.EXTRA_TEXT,
        """
            ${getString(quote.resource)}
            
            ${getString(R.string.attribution_buddha)}
        """.trimIndent()
    )
    type = "text/plain"
}.let { startActivity(Intent.createChooser(it, null)) }
