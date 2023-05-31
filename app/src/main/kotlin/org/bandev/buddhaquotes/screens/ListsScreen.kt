package org.bandev.buddhaquotes.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.KeyboardArrowDown
import androidx.compose.material.icons.rounded.MoreVert
import androidx.compose.material3.Divider
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.maxkeppeker.sheets.core.models.base.IconSource
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.input.InputView
import com.maxkeppeler.sheets.input.models.InputSelection
import com.maxkeppeler.sheets.input.models.InputText
import com.maxkeppeler.sheets.option.OptionView
import com.maxkeppeler.sheets.option.models.DisplayMode
import com.maxkeppeler.sheets.option.models.Option
import com.maxkeppeler.sheets.option.models.OptionConfig
import com.maxkeppeler.sheets.option.models.OptionSelection
import java.util.UUID
import kotlinx.coroutines.launch
import org.bandev.buddhaquotes.R
import org.bandev.buddhaquotes.Scene
import org.bandev.buddhaquotes.architecture.BuddhaQuotesViewModel
import org.bandev.buddhaquotes.architecture.ListMapper
import org.bandev.buddhaquotes.items.ListData

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun ListsScreen(
    viewModel: BuddhaQuotesViewModel = viewModel(),
    navController: NavController
) {
    val scope = rememberCoroutineScope()
    var listId = remember { 0 }
    var newListBottomSheetVisible by rememberSaveable { mutableStateOf(false) }
    var listOptionsBottomSheetVisible by rememberSaveable { mutableStateOf(false) }
    val bottomSheetState = rememberModalBottomSheetState()
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { newListBottomSheetVisible = true }) {
                Icon(imageVector = Icons.Rounded.Add, contentDescription = null)
            }
        },
        floatingActionButtonPosition = FabPosition.Center
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            contentPadding = PaddingValues(
                start = paddingValues.calculateLeftPadding(LayoutDirection.Ltr),
                end = paddingValues.calculateRightPadding(LayoutDirection.Ltr),
                bottom = paddingValues.calculateBottomPadding()
            ),
            verticalArrangement = Arrangement.spacedBy(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            items(items = viewModel.lists, key = ListData::id) { list ->
                ElevatedCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { navController.navigate("${Scene.InsideList.route}/${list.id}") }
                        .animateItemPlacement()
                ) {
                    Row(Modifier.height(IntrinsicSize.Max)) {
                        Box(
                            modifier = Modifier
                                .fillMaxHeight()
                                .width(60.dp)
                                .background(list.icon.colour),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = list.icon.imageVector,
                                contentDescription = null,
                                tint = Color.White
                            )
                        }
                        Column {
                            Text(
                                text = if (list.id != 1) list.title else stringResource(id = R.string.favourites),
                                modifier = Modifier.padding(16.dp),
                                style = MaterialTheme.typography.titleMedium
                            )
                            Divider(Modifier.fillMaxWidth())
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = stringResource(id = if (list.count == 1) R.string.quote_count else R.string.quotes_count, list.count),
                                    modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp),
                                    color = Color.Gray,
                                    style = MaterialTheme.typography.labelLarge
                                )
                                if (list.id != 1) {
                                    Spacer(Modifier.weight(1f))
                                    IconButton(
                                        onClick = {
                                            listId = list.id
                                            listOptionsBottomSheetVisible = true
                                        }
                                    ) {
                                        Icon(
                                            imageVector = Icons.Rounded.MoreVert,
                                            contentDescription = null
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        if (newListBottomSheetVisible) {
            ModalBottomSheet(
                onDismissRequest = { newListBottomSheetVisible = false },
                sheetState = bottomSheetState
            ) {
                InputView(
                    useCaseState = rememberUseCaseState(),
                    selection = InputSelection(
                        input = listOf(InputText(text = "Insert name")),
                        onNegativeClick = {
                            scope.launch { bottomSheetState.hide() }.invokeOnCompletion {
                                if (!bottomSheetState.isVisible) {
                                    newListBottomSheetVisible = false
                                }
                            }
                        },
                        onPositiveClick = {
                            scope.launch {
                                bottomSheetState.hide()
                                viewModel.Lists().new(UUID.randomUUID().toString())
                            }.invokeOnCompletion {
                                if (!bottomSheetState.isVisible) {
                                    newListBottomSheetVisible = false
                                }
                            }
                        }
                    )
                )
            }
        } else if (listOptionsBottomSheetVisible) {
            ModalBottomSheet(
                onDismissRequest = { listOptionsBottomSheetVisible = false },
                sheetState = bottomSheetState
            ) {
                var isRename by remember { mutableStateOf(false) }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    IconButton(
                        onClick = {
                            scope.launch { bottomSheetState.hide() }.invokeOnCompletion {
                                if (!bottomSheetState.isVisible) {
                                    listOptionsBottomSheetVisible = false
                                }
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Rounded.KeyboardArrowDown,
                            contentDescription = null
                        )
                    }
                    Text(
                        text = if (isRename) "Rename list" else "List options",
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.SemiBold,
                        style = MaterialTheme.typography.titleLarge
                    )
                    Spacer(Modifier.weight(1f))
                    Row {
                        IconButton(onClick = { isRename = true }) {
                            Icon(
                                imageVector = Icons.Rounded.Edit,
                                contentDescription = null
                            )
                        }
                        IconButton(
                            onClick = {
                                scope.launch {
                                    viewModel.Lists().delete(listId)
                                    bottomSheetState.hide()
                                }.invokeOnCompletion {
                                    if (!bottomSheetState.isVisible) {
                                        listOptionsBottomSheetVisible = false
                                    }
                                }
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Delete,
                                contentDescription = null
                            )
                        }
                    }
                }
                Divider()
                if (isRename) {
                    InputView(
                        useCaseState = rememberUseCaseState(),
                        selection = InputSelection(input = listOf())
                    )
                } else {
                    OptionView(
                        useCaseState = rememberUseCaseState(),
                        selection = OptionSelection.Single(
                            options = ListMapper.listIcons.map {
                                Option(
                                    icon = IconSource(it.imageVector),
                                    titleText = it.imageVector.name.substringAfterLast('.')
                                )
                            },
                            onNegativeClick = {
                                scope.launch { bottomSheetState.hide() }.invokeOnCompletion {
                                    if (!bottomSheetState.isVisible) {
                                        listOptionsBottomSheetVisible = false
                                    }
                                }
                            }
                        ) { index, option ->
                            scope.launch {
                                bottomSheetState.hide()
                                viewModel.lists.find { it.id == listId }
                                viewModel.Lists().updateIcon(listId, ListMapper.listIcons[index])
                            }.invokeOnCompletion {
                                if (!bottomSheetState.isVisible) {
                                    listOptionsBottomSheetVisible = false
                                }
                            }
                        },
                        config = OptionConfig(mode = DisplayMode.GRID_HORIZONTAL)
                    )
                }
            }
        }
    }
}
