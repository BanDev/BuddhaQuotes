package org.bandev.buddhaquotes.screens.about

import android.content.Intent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.maxkeppeker.sheets.core.models.base.SelectionButton
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.info.InfoDialog
import com.maxkeppeler.sheets.info.models.InfoBody
import com.maxkeppeler.sheets.info.models.InfoSelection
import com.mikepenz.aboutlibraries.entity.Library
import com.mikepenz.aboutlibraries.ui.compose.LibraryDefaults
import com.mikepenz.aboutlibraries.ui.compose.m3.LibrariesContainer
import com.mikepenz.aboutlibraries.ui.compose.m3.libraryColors
import com.mikepenz.aboutlibraries.ui.compose.util.author
import kotlinx.coroutines.launch
import org.bandev.buddhaquotes.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutScene() {
    val pages = remember { listOf(R.string.about, R.string.libraries) }
    val pagerState = rememberPagerState(pageCount = pages::size)
    val animationScope = rememberCoroutineScope()
    val infoDialogState = rememberUseCaseState(embedded = false)
    var libraryState by remember { mutableStateOf<Library?>(null) }
    val context = LocalContext.current

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
                        animationScope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    }
                )
            }
        }

        HorizontalPager(state = pagerState) { page ->
            if (page == 0) {
                AboutPage()
            } else if (page == 1) {
                LibrariesContainer(
                    modifier = Modifier.fillMaxSize(),
                    colors = LibraryDefaults.libraryColors(
                        backgroundColor = MaterialTheme.colorScheme.background,
                        contentColor = MaterialTheme.colorScheme.onBackground,
                        badgeBackgroundColor = MaterialTheme.colorScheme.primary,
                        badgeContentColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    onLibraryClick = { library ->
                        libraryState = library
                        infoDialogState.show()
                    },
                    footer = {
                        item {
                            Spacer(Modifier.navigationBarsPadding())
                        }
                    }
                )
            }
        }
    }
    libraryState?.let { library ->
        InfoDialog(
            state = infoDialogState,
            selection = InfoSelection(
                extraButton = library.website?.let { SelectionButton(text = "Open website") },
                onExtraButtonClick = library.website?.let { website ->
                    {
                        context.startActivity(
                            Intent(Intent.ACTION_VIEW).apply {
                                data = website.toUri()
                            }
                        )
                    }
                },
                negativeButton = null,
            ),
            body = InfoBody.Custom {
                Row {
                    Text(
                        text = library.name,
                        modifier = Modifier.weight(1f),
                        style = MaterialTheme.typography.titleMedium
                    )
                    library.artifactVersion?.let { version ->
                        Text(text = version, style = MaterialTheme.typography.labelMedium)
                    }
                }
                Text(text = library.author, style = MaterialTheme.typography.labelMedium)
                library.description?.let { description ->
                    Spacer(modifier = Modifier.size(10.dp))
                    Text(text = description, style = MaterialTheme.typography.bodyMedium)
                }
            }
        )
    }
}
