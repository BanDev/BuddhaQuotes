package org.bandev.buddhaquotes.screens

import android.content.Intent
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.content.res.Configuration.UI_MODE_TYPE_NORMAL
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import com.maxkeppeker.sheets.core.models.base.SelectionButton
import com.maxkeppeker.sheets.core.models.base.UseCaseState
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.info.InfoDialog
import com.maxkeppeler.sheets.info.models.InfoBody
import com.maxkeppeler.sheets.info.models.InfoSelection
import com.mikepenz.aboutlibraries.entity.Library
import com.mikepenz.aboutlibraries.entity.Organization
import com.mikepenz.aboutlibraries.ui.compose.m3.LibrariesContainer
import com.mikepenz.aboutlibraries.ui.compose.util.author
import kotlinx.collections.immutable.persistentListOf
import org.bandev.buddhaquotes.R
import org.bandev.buddhaquotes.ui.symbols.Symbols
import org.bandev.buddhaquotes.ui.symbols.filled.ArrowBack
import org.bandev.buddhaquotes.ui.theme.BuddhaQuotesTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LibrariesScreen(onBack: () -> Unit) {
    val infoDialogState = rememberUseCaseState(embedded = false)
    var libraryState by remember { mutableStateOf<Library?>(null) }

    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior()

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        "Libraries",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontWeight = FontWeight.SemiBold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Symbols.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back)
                        )
                    }
                },
                scrollBehavior = scrollBehavior
            )
        },
    ) { innerPadding ->
        LibrariesContainer(
            onLibraryClick = { library ->
                libraryState = library
                infoDialogState.show()
            },
            footer = {
                item {
                    Spacer(Modifier.height(innerPadding.calculateBottomPadding()))
                }
            }
        )
    }
    libraryState?.let { library ->
        LibraryDialog(library = library, state = infoDialogState)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LibraryDialog(library: Library, state: UseCaseState = rememberUseCaseState(embedded = false)) {
    val context = LocalContext.current

    InfoDialog(
        state = state,
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
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
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

@Composable
@Preview(name = "Light", heightDp = 250, showBackground = true)
@Preview(
    name = "Dark",
    heightDp = 250,
    showBackground = true,
    uiMode = UI_MODE_NIGHT_YES or UI_MODE_TYPE_NORMAL
)
fun LibraryDialogPreview() {
    BuddhaQuotesTheme {
        LibraryDialog(
            library = Library(
                uniqueId = "",
                artifactVersion = "1.0.0",
                name = "Library name",
                description = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
                website = "",
                developers = persistentListOf(),
                organization = Organization(name = "Author", url = ""),
                scm = null
            ),
            state = rememberUseCaseState(visible = true, embedded = false)
        )
    }
}
