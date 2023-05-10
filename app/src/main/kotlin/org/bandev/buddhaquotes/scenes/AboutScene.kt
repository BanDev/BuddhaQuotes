package org.bandev.buddhaquotes.scenes

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.mikepenz.aboutlibraries.ui.compose.LibrariesContainer
import com.mikepenz.aboutlibraries.ui.compose.LibraryDefaults
import kotlinx.coroutines.launch
import org.bandev.buddhaquotes.BuildConfig
import org.bandev.buddhaquotes.R

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AboutScene() {
    val pages = remember { listOf(R.string.about, R.string.libraries) }
    val pagerState = rememberPagerState()
    val scope = rememberCoroutineScope()

    Column(Modifier.fillMaxSize()) {

        TabRow(selectedTabIndex = pagerState.currentPage) {
            pages.forEachIndexed { index, titleRes ->
                Tab(
                    text = {
                        Text(
                            stringResource(id = titleRes),
                            style = MaterialTheme.typography.labelLarge,
                        )
                    },
                    selected = pagerState.currentPage == index,
                    onClick = {
                        scope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    }
                )
            }
        }

        HorizontalPager(pageCount = pages.size, state = pagerState) { page ->
            if (page == 0) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_buddha),
                        contentDescription = null,
                        modifier = Modifier.size(100.dp)
                    )
                    Spacer(modifier = Modifier.size(10.dp))
                    Text(text = stringResource(id = R.string.app_name))
                    Text(text = BuildConfig.VERSION_NAME)
                }
            } else if (page == 1) {
                LibrariesContainer(
                    modifier = Modifier.fillMaxSize(),
                    colors = LibraryDefaults.libraryColors(
                        backgroundColor = MaterialTheme.colorScheme.background,
                        contentColor = MaterialTheme.colorScheme.onBackground,
                        badgeBackgroundColor = MaterialTheme.colorScheme.primary,
                        badgeContentColor = MaterialTheme.colorScheme.onPrimary
                    )
                )
            }
        }
    }
}
