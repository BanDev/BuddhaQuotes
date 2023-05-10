package org.bandev.buddhaquotes.scenes

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.indication
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Cancel
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material.icons.rounded.Spa
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.CompositingStrategy
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.launch
import org.bandev.buddhaquotes.R
import org.bandev.buddhaquotes.architecture.BuddhaQuotesViewModel
import org.bandev.buddhaquotes.items.QuoteItem

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InsideListScene(listId: Int, viewModel: BuddhaQuotesViewModel = viewModel()) {
    val quotes = remember { mutableStateListOf<QuoteItem>() }
    val allQuotes = remember { mutableStateListOf<QuoteItem>() }
    val selectedItems = remember { mutableStateListOf<QuoteItem>() }
    var openBottomSheet by rememberSaveable { mutableStateOf(false) }
    val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    val scope = rememberCoroutineScope()
    LaunchedEffect(Unit) {
        quotes += viewModel.ListQuotes().getFrom(listId)
        allQuotes += viewModel.Quotes().getAll()
    }
    BackHandler(enabled = selectedItems.isNotEmpty()) {
        selectedItems.clear()
    }
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { openBottomSheet = true }) {
                Icon(imageVector = Icons.Rounded.Add, contentDescription = null)
            }
        },
        floatingActionButtonPosition = FabPosition.Center
    ) { paddingValues ->
        Crossfade(targetState = quotes.isEmpty(), label = "") {
            if (it) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Spa,
                        contentDescription = Icons.Rounded.Spa.name,
                        modifier = Modifier
                            .size(48.dp)
                            .align(Alignment.CenterHorizontally)
                    )
                    Text(
                        text = "No items yet",
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(
                        start = paddingValues.calculateLeftPadding(LayoutDirection.Ltr),
                        end = paddingValues.calculateRightPadding(LayoutDirection.Ltr),
                        bottom = paddingValues.calculateBottomPadding()
                    ),
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    items(items = quotes, key = QuoteItem::id) { quoteItem ->
                        val interactionSource = remember(::MutableInteractionSource)
                        val isSelected = quoteItem in selectedItems
                        ListItem(
                            headlineContent = {
                                Text(
                                    text = stringResource(id = quoteItem.resource),
                                    modifier = Modifier.padding(10.dp),
                                    style = MaterialTheme.typography.bodyLarge
                                )
                            },
                            modifier = Modifier
                                .padding(horizontal = 10.dp)
                                .clip(MaterialTheme.shapes.medium)
                                .indication(
                                    interactionSource = interactionSource,
                                    indication = rememberRipple()
                                )
                                .pointerInput(Unit) {
                                    detectTapGestures(
                                        onLongPress = { offset ->
                                            val press = PressInteraction.Press(offset)
                                            scope.launch {
                                                interactionSource.emit(press)
                                                interactionSource.emit(
                                                    PressInteraction.Release(
                                                        press
                                                    )
                                                )
                                            }
                                            if (quoteItem in selectedItems) {
                                                selectedItems -= quoteItem
                                            } else {
                                                selectedItems += quoteItem
                                            }
                                        }
                                    )
                                },
                            leadingContent = {
                                val rotationY by animateFloatAsState(
                                    targetValue = if (isSelected) 180f else 0f,
                                    label = "Icon rotation"
                                )
                                IconButton(
                                    onClick = { selectedItems -= quoteItem },
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
                                                .drawWithCache {
                                                    onDrawWithContent {
                                                        drawContent()
                                                        drawRect(
                                                            brush = Brush.verticalGradient(
                                                                listOf(Color.Cyan, Color.Blue)
                                                            ),
                                                            blendMode = BlendMode.SrcAtop
                                                        )
                                                    }
                                                }
                                        )
                                    } else {
                                        Icon(
                                            imageVector = Icons.Rounded.CheckCircle,
                                            contentDescription = null,
                                            modifier = Modifier.graphicsLayer(rotationY = 180f - rotationY),
                                            tint = MaterialTheme.colorScheme.primary
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
                        )
                    }
                }
            }
        }
        if (openBottomSheet) {
            ModalBottomSheet(
                onDismissRequest = { openBottomSheet = false },
                modifier = Modifier.fillMaxHeight(),
                sheetState = bottomSheetState
            ) {
                var text by rememberSaveable { mutableStateOf("") }
                OutlinedTextField(
                    value = text,
                    onValueChange = { text = it },
                    modifier = Modifier.fillMaxWidth(),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Rounded.Search,
                            contentDescription = Icons.Rounded.Search.name
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
                                    imageVector = Icons.Outlined.Cancel,
                                    contentDescription = Icons.Outlined.Cancel.name
                                )
                            }
                        }
                    }
                )
                val filteredQuotes = allQuotes.filter {
                    it !in quotes && stringResource(id = it.resource).contains(text, ignoreCase = true)
                }
                LazyColumn {
                    items(items = filteredQuotes, key = QuoteItem::id) {
                        Divider()
                        Text(
                            text = stringResource(id = it.resource),
                            modifier = Modifier
                                .fillMaxWidth()
                                .clickable {
                                    scope
                                        .launch {
                                            bottomSheetState.hide()
                                            quotes += it
                                            viewModel
                                                .ListQuotes()
                                                .addTo(listId, it)
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
