package org.bandev.buddhaquotes.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.LoadingIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontVariation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewDynamicColors
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.bandev.buddhaquotes.R
import org.bandev.buddhaquotes.model.Quote
import org.bandev.buddhaquotes.ui.symbols.Symbols
import org.bandev.buddhaquotes.ui.symbols.filled.FormatQuote
import org.bandev.buddhaquotes.ui.theme.BuddhaQuotesTheme
import org.bandev.buddhaquotes.ui.theme.VariableFontWeight
import org.bandev.buddhaquotes.ui.theme.VariableFontWidth
import org.bandev.buddhaquotes.ui.theme.ascenderHeight
import org.bandev.buddhaquotes.ui.theme.counterWidth
import org.bandev.buddhaquotes.ui.theme.lowercaseHeight
import org.bandev.buddhaquotes.ui.theme.variableFontsSupported
import java.text.DateFormat
import java.util.Calendar
import java.util.Date

@OptIn(ExperimentalMaterial3ExpressiveApi::class, ExperimentalTextApi::class)
@Composable
fun QuoteCard(
    quote: Quote?,
    onSourceClick: () -> Unit,
    modifier: Modifier = Modifier,
    date: Date? = null,
    attribution: Boolean = true
) {
    val quoteTextStyle = if (variableFontsSupported) Typography().bodyLarge.copy(
        fontFamily = FontFamily(
            Font(
                R.font.robotoflex_variable,
                variationSettings = FontVariation.Settings(
                    FontVariation.opticalSizing(16.sp),
                    FontVariation.weight(VariableFontWeight.REGULAR),
                    FontVariation.width(VariableFontWidth.SEMI_CONDENSED),
                    FontVariation.slant(-5f),
                    ascenderHeight(750f),
                    counterWidth(575)
                )
            )
        )
    ) else {
        MaterialTheme.typography.bodyLarge
    }
    
    val attributionTextStyle = if (variableFontsSupported) Typography().labelLarge.copy(
        fontFamily = FontFamily(
            Font(
                R.font.robotoflex_variable,
                variationSettings = FontVariation.Settings(
                    FontVariation.opticalSizing(14.sp),
                    FontVariation.weight(450),
                    FontVariation.width(95f),
                    FontVariation.grade(-25),
                    FontVariation.slant(-8f),
                    ascenderHeight(770f),
                    counterWidth(550),
                    lowercaseHeight(525)
                )
            )
        )
    ) else {
        MaterialTheme.typography.labelLarge
    }

    ElevatedCard(modifier, shape = RoundedCornerShape(24.dp)) {
        Column(
            Modifier
                .padding(20.dp)
                .fillMaxWidth()
        ) {
            Box(Modifier.fillMaxWidth()) {
                Icon(
                    imageVector = Symbols.Filled.FormatQuote,
                    contentDescription = null,
                    modifier = Modifier
                        .rotate(180f)
                        .align(Alignment.CenterStart),
                    tint = MaterialTheme.colorScheme.primary
                )
                date?.let {
                    Text(
                        text = DateFormat.getDateInstance(DateFormat.FULL).format(it),
                        modifier = Modifier.align(Alignment.Center),
                        style = MaterialTheme.typography.labelMedium,
                    )
                }
                TextButton(
                    onClick = onSourceClick,
                    shapes = ButtonDefaults.shapes(),
                    modifier = Modifier.align(Alignment.CenterEnd)
                ) {
                    Text(text = "Source")
                }
            }
            if (quote == null) {
                LoadingIndicator(Modifier.align(Alignment.CenterHorizontally))
            } else {
                AnimatedContent(
                    targetState = quote.text,
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    label = "Animated quote transition"
                ) { text ->
                    Text(
                        text = text,
                        style = quoteTextStyle,
                        textAlign = TextAlign.Center
                    )
                }
            }
            Box(Modifier
                .fillMaxWidth()
                .padding(top = 20.dp)) {
                if (quote != null && attribution) {
                    Text(
                        text = stringResource(R.string.attribution_buddha),
                        modifier = Modifier.align(Alignment.Center),
                        style = attributionTextStyle,
                    )
                }
                Icon(
                    imageVector = Symbols.Filled.FormatQuote,
                    contentDescription = null,
                    modifier = Modifier.align(Alignment.CenterEnd),
                    tint = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@PreviewLightDark
@PreviewDynamicColors
@Composable
fun QuoteCardPreview() {
    BuddhaQuotesTheme {
        QuoteCard(
            quote = Quote(
                text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
            ),
            onSourceClick = {},
        )
    }
}

@Preview
@Composable
fun QuoteCardWithDatePreview() {
    BuddhaQuotesTheme {
        QuoteCard(
            quote = Quote(
                text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
            ),
            onSourceClick = {},
            date = Calendar.getInstance().time
        )
    }
}

@Preview
@Composable
fun LoadingQuoteCardPreview() {
    BuddhaQuotesTheme {
        QuoteCard(
            quote = null,
            onSourceClick = {},
        )
    }
}
