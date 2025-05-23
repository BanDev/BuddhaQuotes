package org.bandev.buddhaquotes.screens

import android.content.Intent
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.Animatable
import androidx.compose.foundation.Image
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Attribution
import androidx.compose.material.icons.rounded.ChevronLeft
import androidx.compose.material.icons.rounded.ChevronRight
import androidx.compose.material.icons.rounded.Share
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
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
import org.bandev.buddhaquotes.FavoriteButton
import org.bandev.buddhaquotes.R
import org.bandev.buddhaquotes.datastore.imagePref.ImagePrefViewModel
import org.bandev.buddhaquotes.db.QuoteViewModel
import org.bandev.buddhaquotes.imagepref.ImagePref
import org.bandev.buddhaquotes.items.AnimatedHeart
import org.bandev.buddhaquotes.items.Heart
import org.bandev.buddhaquotes.items.QuoteCard
import org.bandev.buddhaquotes.model.Quote
import org.bandev.buddhaquotes.model.Source
import org.bandev.buddhaquotes.sheets.ImageSelectionSheet

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuoteScreen(
    viewModel: QuoteViewModel = hiltViewModel(),
    imagePrefViewModel: ImagePrefViewModel = hiltViewModel(),
) {
    val centerImage by imagePrefViewModel.imagePref.observeAsState(ImagePref.getDefaultInstance())

    val quote by viewModel.currentQuote.collectAsState()
    val doubleTapHearts = remember { mutableStateListOf<Heart>() }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    var imageSheetVisible by rememberSaveable { mutableStateOf(false) }
    val imageSheetState = rememberModalBottomSheetState()

    var quoteSourceSheetVisible by rememberSaveable { mutableStateOf(false) }
    val quoteSourceSheetState = rememberModalBottomSheetState()

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
        Column(Modifier.padding(start = 15.dp, top = 1.dp, end = 15.dp)) {
            QuoteCard(
                quote = quote ?: Quote(id = 0, text = "", source = Source("", url = "")),
                onSourceClick = { quoteSourceSheetVisible = true }
            )
            Column(
                Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Bottom
            ) {
                Crossfade(
                    targetState = centerImage,
                    label = "Fade between images"
                ) {
                    Image(
                        painter = painterResource(
                            if (it.image == 1) {
                                R.drawable.image_anahata
                            } else if (it.image != 0) {
                                it.image
                            } else {
                                R.drawable.image_cancel
                            }
                        ),
                        contentDescription = null,
                        modifier = Modifier
                            .size(250.dp)
                            .pointerInput(Unit) {
                                detectTapGestures(
                                    onLongPress = {
                                        imageSheetVisible = true
                                    }
                                )
                            },
                        alpha = if (it.image == R.drawable.image_cancel || it.image == 0) {
                            0f
                        } else {
                            1f
                        }
                    )
                }
                ElevatedCard(Modifier.padding(20.dp)) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(
                            5.dp,
                            Alignment.CenterHorizontally
                        )
                    ) {
                        IconButton(onClick = { viewModel.previousQuote() }) {
                            Icon(
                                imageVector = Icons.Rounded.ChevronLeft,
                                contentDescription = null
                            )
                        }
                        IconButton(
                            onClick = { quote?.let { context.shareQuote(quote = it) } }
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
                        IconButton(onClick = { quoteSourceSheetVisible = true }) {
                            Icon(
                                imageVector = Icons.Rounded.Attribution,
                                contentDescription = null
                            )
                        }
                        IconButton(
                            onClick = { viewModel.nextQuote() }
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
        doubleTapHearts.forEachIndexed { index, heart ->
            AnimatedHeart(heart) {
                doubleTapHearts.removeAt(index)
            }
        }
    }

    if (imageSheetVisible) {
        ImageSelectionSheet(
            sheetState = imageSheetState,
            onClose = { imageSheetVisible = false },
            onImageSelection = imagePrefViewModel::setImage
        )
    }

    val quoteSource = quote?.source
    if (quoteSourceSheetVisible && quoteSource != null) {
        ModalBottomSheet(
            onDismissRequest = { quoteSourceSheetVisible = false },
            sheetState = imageSheetState
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