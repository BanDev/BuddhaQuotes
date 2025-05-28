package org.bandev.buddhaquotes.screens

import android.content.Context
import android.content.Intent
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import com.maxkeppeker.sheets.core.models.base.Header
import com.maxkeppeker.sheets.core.models.base.SelectionButton
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.info.InfoView
import com.maxkeppeler.sheets.info.models.InfoBody
import com.maxkeppeler.sheets.info.models.InfoSelection
import kotlin.random.Random
import kotlinx.coroutines.launch
import org.bandev.buddhaquotes.R
import org.bandev.buddhaquotes.components.AnimatedHeart
import org.bandev.buddhaquotes.components.Heart
import org.bandev.buddhaquotes.components.QuoteCard
import org.bandev.buddhaquotes.components.QuoteToolbar
import org.bandev.buddhaquotes.datastore.settings.SettingsViewModel
import org.bandev.buddhaquotes.datastore.settings.toImage
import org.bandev.buddhaquotes.db.QuoteViewModel
import org.bandev.buddhaquotes.model.Quote

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun QuoteScreen(
    viewModel: QuoteViewModel = hiltViewModel(),
    settingsViewModel: SettingsViewModel = hiltViewModel(),
) {
    val quote by viewModel.currentQuote.collectAsState()
    val settings by settingsViewModel.settings.observeAsState()
    val doubleTapHearts = remember { mutableStateListOf<Heart>() }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    var quoteSourceSheetVisible by rememberSaveable { mutableStateOf(false) }
    val quoteSourceSheetState = rememberModalBottomSheetState()

    Box {
        settings?.let { settings ->
            Crossfade(settings.image.toImage()) {
                if (it != null) {
                    Image(
                        imageVector = it,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .fillMaxSize()
                            .align(Alignment.Center),
                        alpha = 0.1f
                    )
                }
            }
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .pointerInput(Unit) {
                    detectTapGestures(
                        onDoubleTap = { offset ->
                            quote?.let { viewModel.setFavourite(it) }
                            doubleTapHearts += Heart(
                                position = offset,
                                rotation = Random.nextFloat() * 40 - 20,
                                size = Animatable(0f),
                                alpha = Animatable(1f)
                            )
                        }
                    )
                }
        ) {
            QuoteCard(
                quote = quote,
                onSourceClick = { quoteSourceSheetVisible = true },
                modifier = Modifier
                    .padding(15.dp)
                    .align(Alignment.Center)
            )
            QuoteToolbar(
                modifier = Modifier
                    .padding(20.dp)
                    .align(Alignment.BottomCenter),
                quote = quote,
                onPrevQuote = viewModel::previousQuote,
                onToggleFavourite = viewModel::toggleFavourite,
                onSourceClick = { quoteSourceSheetVisible = true },
                onNextQuote = viewModel::nextQuote
            )
            doubleTapHearts.forEachIndexed { index, heart ->
                AnimatedHeart(heart) {
                    doubleTapHearts.removeAt(index)
                }
            }
        }
    }

    val quoteSource = quote?.source
    if (quoteSourceSheetVisible && quoteSource != null) {
        ModalBottomSheet(
            onDismissRequest = { quoteSourceSheetVisible = false },
            sheetState = quoteSourceSheetState
        ) {
            InfoView(
                useCaseState = rememberUseCaseState(),
                selection = InfoSelection(
                    extraButton = SelectionButton(text = "Open source URL"),
                    onExtraButtonClick = {
                        context.startActivity(
                            Intent(Intent.ACTION_VIEW).apply {
                                data = quoteSource.url.toUri()
                            }
                        )
                    },
                    negativeButton = null,
                    onPositiveClick = {
                        scope.launch { quoteSourceSheetState.hide() }.invokeOnCompletion {
                            if (!quoteSourceSheetState.isVisible) {
                                quoteSourceSheetVisible = false
                            }
                        }
                    }
                ),
                header = Header.Custom {
                    Text(
                        text = "Quote Source",
                        modifier = Modifier.padding(16.dp),
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.SemiBold,
                        style = MaterialTheme.typography.titleLarge,
                    )
                    HorizontalDivider()
                },
                body = InfoBody.Default(
                    bodyText = quoteSource.body,
                    postBody = {
                        quoteSource.fullQuote?.let { fullQuote ->
                            Text(
                                text = fullQuote,
                                modifier = Modifier
                                    .padding(10.dp)
                                    .layoutId("text"),
                                color = Color.Gray,
                                style = MaterialTheme.typography.bodyMedium
                            )
                        }
                    }
                )
            )
        }
    }
}

fun Context.shareQuote(quote: Quote) = Intent().apply {
    action = Intent.ACTION_SEND
    putExtra(
        Intent.EXTRA_TEXT,
        """
            ${quote.text}
            
            ${getString(R.string.attribution_buddha)}
        """.trimIndent()
    )
    type = "text/plain"
}.let { startActivity(Intent.createChooser(it, null)) }
