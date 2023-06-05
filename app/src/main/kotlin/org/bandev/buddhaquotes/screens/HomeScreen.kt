package org.bandev.buddhaquotes.screens

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.Crossfade
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
import androidx.compose.foundation.layout.width
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
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
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
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.layoutId
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.maxkeppeker.sheets.core.models.base.Header
import com.maxkeppeker.sheets.core.models.base.SelectionButton
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.info.InfoView
import com.maxkeppeler.sheets.info.models.InfoBody
import com.maxkeppeler.sheets.info.models.InfoSelection
import kotlin.math.max
import kotlin.random.Random
import kotlinx.coroutines.launch
import org.bandev.buddhaquotes.FavoriteButton
import org.bandev.buddhaquotes.R
import org.bandev.buddhaquotes.architecture.lists.ListViewModel
import org.bandev.buddhaquotes.architecture.quotes.QuoteStore
import org.bandev.buddhaquotes.architecture.quotes.QuoteViewModel
import org.bandev.buddhaquotes.datastore.imagePref.ImagePrefViewModel
import org.bandev.buddhaquotes.imagepref.ImagePref
import org.bandev.buddhaquotes.items.AnimatedHeart
import org.bandev.buddhaquotes.items.Heart
import org.bandev.buddhaquotes.items.QuoteItem
import org.bandev.buddhaquotes.sheets.ImageSelectionSheet

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController,
    pagerState: PagerState,
    quoteViewModel: QuoteViewModel = hiltViewModel(),
    listViewModel: ListViewModel = hiltViewModel(),
    imagePrefViewModel: ImagePrefViewModel = ImagePrefViewModel(LocalContext.current)
) {
    val quote by quoteViewModel.selectedQuote.observeAsState(QuoteItem())
    val context = LocalContext.current
    val scope = rememberCoroutineScope()

    val hearts = remember { mutableStateListOf<Heart>() }

    var imageSheetVisible by rememberSaveable { mutableStateOf(false) }
    val imageSheetState = rememberModalBottomSheetState()

    var quoteSourceSheetVisible by rememberSaveable { mutableStateOf(false) }
    val quoteSourceSheetState = rememberModalBottomSheetState()

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
        val centerImage by imagePrefViewModel.imagePref.observeAsState(ImagePref.getDefaultInstance())
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
                                                listViewModel.setLiked(quote.id, !quote.isLiked)
                                                quoteViewModel.setLiked(!quote.isLiked)
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
                        ElevatedCard(
                            modifier = Modifier
                                .fillMaxWidth()
                                .pointerInput(Unit) {
                                    detectTapGestures(
                                        onLongPress = {
                                            quoteSourceSheetVisible = true
                                        }
                                    )
                                }
                        ) {
                            Box {
                                Column(
                                    Modifier
                                        .padding(20.dp)
                                        .fillMaxWidth()) {
                                    Image(
                                        painter = painterResource(id = R.drawable.ic_left_quote),
                                        contentDescription = null
                                    )
                                    AnimatedContent(
                                        targetState = quote.resource,
                                        label = "Animated quote transition"
                                    ) {
                                        Text(
                                            text = stringResource(it),
                                            modifier = Modifier.padding(vertical = 10.dp)
                                        )
                                    }
                                    Image(
                                        painter = painterResource(id = R.drawable.ic_right_quote),
                                        contentDescription = null,
                                        modifier = Modifier.align(Alignment.End)
                                    )
                                    Text(text = stringResource(R.string.attribution_buddha))
                                }
                                TextButton(
                                    onClick = { quoteSourceSheetVisible = true },
                                    modifier = Modifier
                                        .align(Alignment.TopEnd)
                                        .padding(10.dp)
                                ) {
                                    Text(text = "Source")
                                }
                            }
                        }
                        Column(
                            Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Bottom
                        ) {
                            Crossfade(targetState = centerImage, label = "Fade between images") {
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
                                    horizontalArrangement = Arrangement.spacedBy(5.dp, Alignment.CenterHorizontally)
                                ) {
                                    IconButton(
                                        onClick = {
                                            val id = if (quote.id - 1 > QuoteStore.quotesWithSources.size) {
                                                1
                                            } else if (quote.id - 1 < 1) {
                                                QuoteStore.quotesWithSources.size
                                            } else {
                                                quote.id - 1
                                            }
                                            scope.launch {
                                                quoteViewModel.setNewQuote(quoteViewModel.get(id))
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
                                                listViewModel.setLiked(quote.id, !quote.isLiked)
                                                quoteViewModel.setLiked(!quote.isLiked)
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
                                            val id = if (quote.id + 1 > QuoteStore.quotesWithSources.size) {
                                                1
                                            } else if (quote.id + 1 < 1) {
                                                QuoteStore.quotesWithSources.size
                                            } else {
                                                quote.id + 1
                                            }
                                            scope.launch {
                                                quoteViewModel.setNewQuote(quoteViewModel.get(id))
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
                1 -> ListsScreen(navController = navController)
                else -> MeditateScreen()
            }
        }
        if (imageSheetVisible) {
            ImageSelectionSheet(
                sheetState = imageSheetState,
                onClose = { imageSheetVisible = false },
                onImageSelection = imagePrefViewModel::setImage
            )
        }
        val quoteSource = QuoteStore.quotesWithSources[quote.resource]
        if (quoteSourceSheetVisible && quoteSource != null) {
            ModalBottomSheet(
                onDismissRequest = { quoteSourceSheetVisible = false },
                sheetState = imageSheetState
            ) {
                InfoView(
                    useCaseState = rememberUseCaseState(),
                    selection = InfoSelection(
                        extraButton = quoteSource.url?.let {
                            SelectionButton(text = "Open source URL")
                        },
                        onExtraButtonClick = {
                            context.startActivity(
                                Intent(Intent.ACTION_VIEW).apply {
                                    data = Uri.parse(quoteSource.url as String)
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
                        Divider()
                    },
                    body = InfoBody.Default(
                        bodyText = if (quoteSource.verse != null) {
                            stringResource(id = quoteSource.bodyRes, quoteSource.verse)
                        } else {
                            stringResource(id = quoteSource.bodyRes)
                        },
                        postBody = {
                            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                                quoteSource.fullQuoteRes?.let {
                                    Layout(
                                        content = {
                                            Divider(modifier = Modifier
                                                .width(1.dp)
                                                .layoutId("line"))
                                            Text(
                                                text = stringResource(id = it),
                                                modifier = Modifier
                                                    .padding(10.dp)
                                                    .layoutId("text"),
                                                color = Color.Gray,
                                                style = MaterialTheme.typography.bodyMedium
                                            )
                                        }
                                    ) { measurables, constraints ->
                                        val textPlaceable = measurables.first { it.layoutId == "text" }.measure(constraints)
                                        val linePlaceable = measurables.first { it.layoutId == "line" }
                                            .measure(constraints.copy(minHeight = textPlaceable.height, maxHeight = textPlaceable.height))
                                        layout(max(textPlaceable.width, linePlaceable.width), max(textPlaceable.height, linePlaceable.height)) {
                                            linePlaceable.placeRelative(0, 0)
                                            textPlaceable.placeRelative(linePlaceable.width, 0)
                                        }
                                    }
                                }
                            }
                        }
                    )
                )
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
