package org.bandev.buddhaquotes.screens

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.FormatListBulleted
import androidx.compose.material.icons.automirrored.outlined.FormatListBulleted
import androidx.compose.material.icons.filled.FormatQuote
import androidx.compose.material.icons.filled.SelfImprovement
import androidx.compose.material.icons.outlined.FormatQuote
import androidx.compose.material.icons.outlined.SelfImprovement
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import org.bandev.buddhaquotes.R
import org.bandev.buddhaquotes.architecture.lists.ListViewModel
import org.bandev.buddhaquotes.architecture.quotes.QuoteViewModel
import org.bandev.buddhaquotes.datastore.imagePref.ImagePrefViewModel
import org.bandev.buddhaquotes.items.QuoteItem

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    pagerState: PagerState,
    quoteViewModel: QuoteViewModel = hiltViewModel(),
    listViewModel: ListViewModel = hiltViewModel(),
    imagePrefViewModel: ImagePrefViewModel = hiltViewModel()
) {
    val pagerScope = rememberCoroutineScope()

    Scaffold(
        bottomBar = {
            NavigationBar {
                listOf(
                    Triple("Quotes", Icons.Filled.FormatQuote, Icons.Outlined.FormatQuote),
                    Triple(
                        "Lists", Icons.AutoMirrored.Filled.FormatListBulleted,
                        Icons.AutoMirrored.Outlined.FormatListBulleted
                    ),
                    Triple(
                        "Meditation",
                        Icons.Filled.SelfImprovement,
                        Icons.Outlined.SelfImprovement
                    )
                ).forEachIndexed { index, (label, iconFilled, iconOutlined) ->
                    NavigationBarItem(
                        icon = {
                            Icon(
                                if (pagerState.currentPage == index) iconFilled else iconOutlined,
                                contentDescription = label
                            )
                        },
                        label = { Text(label) },
                        selected = pagerState.currentPage == index,
                        onClick = {
                            pagerScope.launch {
                                pagerState.animateScrollToPage(index)
                            }
                        }
                    )
                }
            }
        }
    ) { paddingValues ->
        HorizontalPager(
            beyondViewportPageCount = 2,
            modifier = Modifier.padding(bottom = paddingValues.calculateBottomPadding()),
            state = pagerState
        ) { page ->
            when (page) {
                0 -> QuoteScreen(quoteViewModel, listViewModel, imagePrefViewModel)
                1 -> ListsScreen(listViewModel, navController)
                else -> MeditateScreen()
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
