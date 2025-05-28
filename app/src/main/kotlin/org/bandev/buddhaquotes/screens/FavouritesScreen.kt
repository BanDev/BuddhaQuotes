package org.bandev.buddhaquotes.screens

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.material3.ExpandedFullScreenSearchBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LoadingIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.SearchBarValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.rememberSearchBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.launch
import org.bandev.buddhaquotes.R
import org.bandev.buddhaquotes.components.QuoteCard
import org.bandev.buddhaquotes.db.QuoteViewModel
import org.bandev.buddhaquotes.model.Quote
import org.bandev.buddhaquotes.ui.symbols.Symbols
import org.bandev.buddhaquotes.ui.symbols.filled.Add
import org.bandev.buddhaquotes.ui.symbols.filled.ArrowBack
import org.bandev.buddhaquotes.ui.symbols.filled.Search
import org.bandev.buddhaquotes.ui.symbols.filled.Spa
import org.bandev.buddhaquotes.ui.symbols.outlined.Cancel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun FavouritesScreen(
    viewModel: QuoteViewModel = hiltViewModel(),
) {
    val quotes by viewModel.favourites.collectAsState(null)
    val selectedItems = remember { mutableStateListOf<Quote>() }
    var openBottomSheet by rememberSaveable { mutableStateOf(false) }
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val allQuotes by viewModel.quotes.collectAsState(emptyList())
    BackHandler(enabled = selectedItems.isNotEmpty()) {
        selectedItems.clear()
    }
    val textFieldState = rememberTextFieldState()
    val searchBarState = rememberSearchBarState()
    val scope = rememberCoroutineScope()

    val inputField =
        @Composable {
            SearchBarDefaults.InputField(
                modifier = Modifier,
                searchBarState = searchBarState,
                textFieldState = textFieldState,
                onSearch = { scope.launch { searchBarState.animateToCollapsed() } },
                placeholder = { Text("Search...") },
                leadingIcon = {
                    if (searchBarState.currentValue == SearchBarValue.Expanded) {
                        IconButton(
                            onClick = { scope.launch { searchBarState.animateToCollapsed() } }
                        ) {
                            Icon(Symbols.Filled.ArrowBack, contentDescription = "Back")
                        }
                    } else {
                        Icon(Symbols.Filled.Search, contentDescription = null)
                    }
                },
            )
        }

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { scope.launch { searchBarState.animateToExpanded() } }) {
                Icon(imageVector = Symbols.Filled.Add, contentDescription = null)
            }
        },
        floatingActionButtonPosition = FabPosition.Center
    ) { paddingValues ->
        ExpandedFullScreenSearchBar(
            state = searchBarState,
            inputField = inputField
        ) {
            var text by rememberSaveable { mutableStateOf("") }
            val filteredQuotes = allQuotes.filter {
                it !in quotes!! && it.text.contains(
                    text,
                    ignoreCase = true
                )
            }
            val firstQuote = filteredQuotes.firstOrNull()
            LazyColumn(
                contentPadding = PaddingValues(bottom = paddingValues.calculateBottomPadding()),
            ) {
                items(items = filteredQuotes, key = Quote::id) { quote ->
                    if (quote != firstQuote) {
                        HorizontalDivider()
                    }
                    Text(
                        text = quote.text,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                scope
                                    .launch {
                                        bottomSheetState.hide()
                                        viewModel.setFavourite(quote)
                                    }
                                    .invokeOnCompletion {
                                        if (!bottomSheetState.isVisible) {
                                            openBottomSheet = false
                                        }
                                    }
                            }
                            .padding(10.dp)
                    )
                }
            }
        }

        quotes?.let { quotes ->
            Crossfade(targetState = quotes.isEmpty()) { noQuotes ->
                if (noQuotes) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Symbols.Filled.Spa,
                            contentDescription = null,
                            modifier = Modifier
                                .size(48.dp)
                                .align(Alignment.CenterHorizontally)
                        )
                        Text(
                            text = stringResource(R.string.no_favourites),
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(
                            start = paddingValues.calculateStartPadding(LayoutDirection.Ltr) + 20.dp,
                            top = 20.dp,
                            end = paddingValues.calculateEndPadding(LayoutDirection.Ltr) + 20.dp,
                            bottom = paddingValues.calculateBottomPadding() + 20.dp
                        ),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        itemsIndexed(items = quotes) { index, quote ->
                            QuoteCard(
                                quote = quote,
                                onSourceClick = {},
                                attribution = false
                            )
                            if (index != quotes.lastIndex) {
                                HorizontalDivider(
                                    Modifier
                                        .padding(vertical = 20.dp)
                                        .clip(MaterialTheme.shapes.extraSmall)
                                )
                            }
                            /*val interactionSource = remember(::MutableInteractionSource)
                                    val isSelected = quote in selectedItems
                                    ListItem(
                                        headlineContent = {
                                            Text(
                                                text = quote.text,
                                                modifier = Modifier.padding(10.dp),
                                                style = MaterialTheme.typography.bodyLarge
                                            )
                                        },
                                        modifier = Modifier
                                            .padding(horizontal = 10.dp)
                                            .clip(MaterialTheme.shapes.medium)
                                            .indication(
                                                interactionSource = interactionSource,
                                                indication = ripple()
                                            )
                                            .pointerInput(Unit) {
                                                detectTapGestures(
                                                    onLongPress = { offset ->
                                                        val press = PressInteraction.Press(offset)
                                                        scope.launch {
                                                            interactionSource.emit(press)
                                                            interactionSource.emit(
                                                                PressInteraction.Release(press)
                                                            )
                                                        }
                                                        if (quote in selectedItems) {
                                                            selectedItems -= quote
                                                        } else {
                                                            selectedItems += quote
                                                        }
                                                    }
                                                )
                                            }
                                            .animateItem(),
                                        leadingContent = {
                                            val rotationY by animateFloatAsState(
                                                targetValue = if (isSelected) 180f else 0f,
                                                label = "Icon rotation"
                                            )
                                            IconButton(
                                                onClick = { selectedItems -= quote },
                                                enabled = isSelected
                                            ) {
                                                if (rotationY < 90f) {
                                                    Image(
                                                        painter = painterResource(id = R.drawable.ic_left_quote),
                                                        contentDescription = null,
                                                        modifier = Modifier
                                                            .graphicsLayer(
                                                                rotationY = rotationY,
                                                                compositingStrategy = CompositingStrategy.Offscreen
                                                            )
                                                    )
                                                } else {
                                                    Icon(
                                                        imageVector = Icons.Rounded.CheckCircle,
                                                        contentDescription = null,
                                                        modifier = Modifier.graphicsLayer(rotationY = 180f - rotationY),
                                                    )
                                                }
                                            }
                                        },
                                        colors = ListItemDefaults.colors(
                                            containerColor = if (isSelected) {
                                                MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)
                                            } else {
                                                MaterialTheme.colorScheme.surface
                                            }
                                        )
                                    )*/
                        }
                    }
                }
            }
        } ?: Box(Modifier.fillMaxSize()) {
            LoadingIndicator(Modifier.align(Alignment.Center))
        }

        if (openBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = { openBottomSheet = false },
                modifier = Modifier
                    .fillMaxHeight()
                    .statusBarsPadding(),
                sheetState = bottomSheetState,
                contentWindowInsets = { WindowInsets.safeDrawing.only(WindowInsetsSides.Horizontal) }
            ) {
                var text by rememberSaveable { mutableStateOf("") }
                OutlinedTextField(
                    value = text,
                    onValueChange = { text = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp),
                    leadingIcon = {
                        Icon(
                            imageVector = Symbols.Filled.Search,
                            contentDescription = "Search"
                        )
                    },
                    trailingIcon = {
                        AnimatedVisibility(
                            visible = text.isNotBlank(),
                            enter = fadeIn(),
                            exit = fadeOut()
                        ) {
                            IconButton(onClick = { text = "" }) {
                                Icon(
                                    imageVector = Symbols.Outlined.Cancel,
                                    contentDescription = "Cancel"
                                )
                            }
                        }
                    }
                )

                val filteredQuotes = allQuotes.filter {
                    it !in quotes!! && it.text.contains(
                        text,
                        ignoreCase = true
                    )
                }
                val firstQuote = filteredQuotes.firstOrNull()
                LazyColumn(
                    contentPadding = PaddingValues(bottom = paddingValues.calculateBottomPadding()),
                ) {
                    items(items = filteredQuotes, key = Quote::id) { quote ->
                        if (quote != firstQuote) {
                            HorizontalDivider()
                        }
                        Text(
                            text = quote.text,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    scope
                                        .launch {
                                            bottomSheetState.hide()
                                            viewModel.setFavourite(quote)
                                        }
                                        .invokeOnCompletion {
                                            if (!bottomSheetState.isVisible) {
                                                openBottomSheet = false
                                            }
                                        }
                                }
                                .padding(10.dp)
                        )
                    }
                }
            }
        }
    }
}
