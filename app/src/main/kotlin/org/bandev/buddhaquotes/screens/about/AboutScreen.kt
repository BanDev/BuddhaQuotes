package org.bandev.buddhaquotes.screens.about

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import com.maxkeppeker.sheets.core.models.base.SelectionButton
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.info.InfoDialog
import com.maxkeppeler.sheets.info.models.InfoBody
import com.maxkeppeler.sheets.info.models.InfoSelection
import com.mikepenz.aboutlibraries.entity.Library
import com.mikepenz.aboutlibraries.ui.compose.LibrariesContainer
import com.mikepenz.aboutlibraries.ui.compose.LibraryDefaults
import com.mikepenz.aboutlibraries.ui.compose.util.author
import kotlinx.coroutines.launch
import org.bandev.buddhaquotes.R

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun AboutScene() {
    val pages = remember { listOf(R.string.about, R.string.libraries) }
    val pagerState = rememberPagerState()
    val scope = rememberCoroutineScope()
    val useCaseState = rememberUseCaseState(embedded = false)
    var library by remember { mutableStateOf<Library?>(null) }
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
                        scope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    }
                )
            }
        }

        HorizontalPager(pageCount = pages.size, state = pagerState) { page ->
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
                    onLibraryClick = {
                        library = it
                        useCaseState.show()
                    }
                )
            }
        }
    }
    InfoDialog(
        state = useCaseState,
        selection = InfoSelection(
            extraButton = if (library?.website != null) {
                SelectionButton(text = "Open website")
            } else {
                null
            },
            onExtraButtonClick = if (library?.website != null) {
                {
                    context.startActivity(
                        Intent(Intent.ACTION_VIEW).apply {
                            data = Uri.parse(library?.website)
                        }
                    )
                }
            } else {
                null
            },
            negativeButton = null,
            onPositiveClick = useCaseState::finish
        ),
        body = InfoBody.Custom {
            library?.name?.let { name ->
                Row {
                    Text(
                        text = name,
                        modifier = Modifier.weight(1f),
                        style = MaterialTheme.typography.titleMedium
                    )
                    library?.artifactVersion?.let {
                        Text(text = it, style = MaterialTheme.typography.labelMedium)
                    }
                }
                library?.author?.let { author ->
                    Text(text = author, style = MaterialTheme.typography.labelMedium)
                }
            }
            library?.description?.let {
                Spacer(modifier = Modifier.size(10.dp))
                Text(text = it, style = MaterialTheme.typography.bodyMedium)
            }
        }
    )
}
