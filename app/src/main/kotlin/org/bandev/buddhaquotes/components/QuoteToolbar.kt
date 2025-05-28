package org.bandev.buddhaquotes.components

import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.HorizontalFloatingToolbar
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.PreviewDynamicColors
import androidx.compose.ui.tooling.preview.PreviewLightDark
import kotlin.random.Random
import org.bandev.buddhaquotes.FavoriteButton
import org.bandev.buddhaquotes.model.Quote
import org.bandev.buddhaquotes.screens.shareQuote
import org.bandev.buddhaquotes.ui.symbols.Symbols
import org.bandev.buddhaquotes.ui.symbols.filled.ChevronLeft
import org.bandev.buddhaquotes.ui.symbols.filled.ChevronRight
import org.bandev.buddhaquotes.ui.symbols.outlined.Attribution
import org.bandev.buddhaquotes.ui.symbols.outlined.Share
import org.bandev.buddhaquotes.ui.theme.BuddhaQuotesTheme

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun QuoteToolbar(
    quote: Quote?,
    modifier: Modifier = Modifier,
    onPrevQuote: (() -> Unit)? = null,
    onToggleFavourite: (Quote) -> Unit,
    onSourceClick: () -> Unit,
    onNextQuote: (() -> Unit)? = null
) {
    val context = LocalContext.current
    val isLiked = quote?.isLiked == true
    HorizontalFloatingToolbar(
        expanded = true,
        modifier = modifier,
    ) {
        onPrevQuote?.let { onPrevQuote ->
            IconButton(
                onClick = onPrevQuote,
                shapes = IconButtonDefaults.shapes()
            ) {
                Icon(
                    imageVector = Symbols.Filled.ChevronLeft,
                    contentDescription = null
                )
            }
        }
        IconButton(
            onClick = { quote?.let(context::shareQuote) },
            shapes = IconButtonDefaults.shapes()
        ) {
            Icon(
                imageVector = Symbols.Outlined.Share,
                contentDescription = null
            )
        }
        FavoriteButton(
            checked = isLiked,
            onCheckedChange = { quote?.let(onToggleFavourite) },
        )
        IconButton(
            onClick = onSourceClick,
            shapes = IconButtonDefaults.shapes()
        ) {
            Icon(
                imageVector = Symbols.Outlined.Attribution,
                contentDescription = null
            )
        }
        onNextQuote?.let { onNextQuote ->
            IconButton(
                onClick = onNextQuote,
                shapes = IconButtonDefaults.shapes()
            ) {
                Icon(
                    imageVector = Symbols.Filled.ChevronRight,
                    contentDescription = null
                )
            }
        }
    }
}

@PreviewLightDark
@PreviewDynamicColors
@Composable
fun QuoteToolbarPreview() {
    BuddhaQuotesTheme {
        var isLiked by remember { mutableStateOf(false) }
        QuoteToolbar(
            quote = Quote(isLiked = isLiked),
            onPrevQuote = { isLiked = Random.nextBoolean() },
            onToggleFavourite = { isLiked = !isLiked },
            onSourceClick = {},
            onNextQuote = { isLiked = Random.nextBoolean() },
        )
    }
}