package org.bandev.buddhaquotes.ui.theme

import android.os.Build
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.Typography
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontVariation
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.bandev.buddhaquotes.R

val robotoFlex = FontFamily(
    Font(R.font.robotoflex_variable),
)

@OptIn(ExperimentalTextApi::class)
val montserrat = FontFamily(
    Font(
        resId = R.font.montserrat_variable,
        weight = FontWeight.Thin,
        variationSettings = FontVariation.Settings(FontVariation.weight(VariableFontWeight.THIN))
    ),
    Font(
        resId = R.font.montserrat_variable,
        weight = FontWeight.ExtraLight,
        variationSettings = FontVariation.Settings(FontVariation.weight(VariableFontWeight.EXTRA_LIGHT))
    ),
    Font(
        resId = R.font.montserrat_variable,
        weight = FontWeight.Light,
        variationSettings = FontVariation.Settings(FontVariation.weight(VariableFontWeight.LIGHT))
    ),
    Font(
        resId = R.font.montserrat_variable,
        weight = FontWeight.Normal,
        variationSettings = FontVariation.Settings(FontVariation.weight(VariableFontWeight.REGULAR))
    ),
    Font(
        resId = R.font.montserrat_variable,
        weight = FontWeight.Medium,
        variationSettings = FontVariation.Settings(FontVariation.weight(VariableFontWeight.MEDIUM))
    ),
    Font(
        resId = R.font.montserrat_variable,
        weight = FontWeight.SemiBold,
        variationSettings = FontVariation.Settings(FontVariation.weight(VariableFontWeight.SEMI_BOLD))
    ),
    Font(
        resId = R.font.montserrat_variable,
        weight = FontWeight.Bold,
        variationSettings = FontVariation.Settings(FontVariation.weight(VariableFontWeight.BOLD))
    ),
    Font(
        resId = R.font.montserrat_variable,
        weight = FontWeight.ExtraBold,
        variationSettings = FontVariation.Settings(FontVariation.weight(VariableFontWeight.EXTRA_BOLD))
    ),
    Font(
        resId = R.font.montserrat_variable,
        weight = FontWeight.Black,
        variationSettings = FontVariation.Settings(FontVariation.weight(VariableFontWeight.BLACK))
    ),
)

/** XOPQ */
fun thickStroke(thickStroke: Int = 96): FontVariation.Setting {
    require(thickStroke in 27..175) { "'Thick stroke must be in 27..175'" }
    return FontVariation.Setting("XOPQ", thickStroke.toFloat())
}

/** YOPQ */
fun thinStroke(thinStroke: Int = 79): FontVariation.Setting {
    require(thinStroke in 25..135) { "'Thin stroke must bne in 25..135'" }
    return FontVariation.Setting("YOPQ", thinStroke.toFloat())
}

/** YTAS */
fun ascenderHeight(ascenderHeight: Float = 750f): FontVariation.Setting {
    require(ascenderHeight in 649f..854f) { "'Ascender Height' must be in 649f..854f" }
    return FontVariation.Setting("YTAS", ascenderHeight)
}

/**
 * Counter width - XTRA
 * */
fun counterWidth(counterWidth: Int = 468): FontVariation.Setting {
    require(counterWidth in 323..603) { "'Counter width' must be in 323..603" }
    return FontVariation.Setting("XTRA", counterWidth.toFloat())
}

/** YTLC */
fun lowercaseHeight(lowercaseHeight: Int = 514): FontVariation.Setting {
    require(lowercaseHeight in 416..570) { "'Lowercase height' must be in 416..570" }
    return FontVariation.Setting("YTLC", lowercaseHeight.toFloat())
}

/** YTUC */
fun uppercaseHeight(uppercaseHeight: Int): FontVariation.Setting {
    require(uppercaseHeight in 528..760) { "'Uppercase height' must be in 528..760" }
    return FontVariation.Setting("YTUC", uppercaseHeight.toFloat())
}

/** YTDE */
fun descenderDepth(descenderDepth: Int = -203): FontVariation.Setting {
    require(descenderDepth in -305..-98) { "'Uppercase height' must be in 528..760" }
    return FontVariation.Setting("YTDE", descenderDepth.toFloat())
}

val variableFontsSupported = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O

object VariableFontWeight {
    const val THIN = 100
    const val EXTRA_LIGHT = 200
    const val LIGHT = 300
    const val REGULAR = 400
    const val MEDIUM = 500
    const val SEMI_BOLD = 600
    const val BOLD = 700
    const val EXTRA_BOLD = 800
    const val BLACK = 900
}

object VariableFontWidth {
    const val SUPER_CONDENSED = 25f
    const val ULTRA_CONDENSED = 50f
    const val EXTRA_CONDENSED = 62.5f
    const val CONDENSED = 75f
    const val SEMI_CONDENSED = 87.5f
    const val NORMAL = 100f
    const val SEMI_EXPANDED = 112.5f
    const val EXPANDED = 125f
    const val EXTRA_EXPANDED = 150f
}

@OptIn(ExperimentalTextApi::class)
val titleLargeEmphasizedFontFamily = if (variableFontsSupported) {
    FontFamily(
        Font(
            R.font.robotoflex_variable,
            variationSettings = FontVariation.Settings(
                FontVariation.opticalSizing(22.sp),
                FontVariation.weight(VariableFontWeight.SEMI_BOLD),
                FontVariation.grade(-10),
                thickStroke(86),
                thinStroke(88),
                counterWidth(560),
                lowercaseHeight(500),
                ascenderHeight(770f),
                descenderDepth(-230)
            )
        )
    )
} else {
    null
}

@OptIn(ExperimentalTextApi::class)
val titleMediumFontFamily = if (variableFontsSupported) {
    FontFamily(
        Font(
            R.font.robotoflex_variable,
            variationSettings = FontVariation.Settings(
                FontVariation.opticalSizing(16.sp),
                FontVariation.weight(VariableFontWeight.MEDIUM),
                FontVariation.width(VariableFontWidth.SEMI_EXPANDED),
                counterWidth(500),
                ascenderHeight(750f),
            )
        )
    )
} else {
    null
}

@OptIn(ExperimentalTextApi::class)
val titleMediumEmphasizedFontFamily = if (variableFontsSupported) {
    FontFamily(
        Font(
            R.font.robotoflex_variable,
            variationSettings = FontVariation.Settings(
                FontVariation.opticalSizing(16.sp),
                FontVariation.weight(VariableFontWeight.EXTRA_BOLD),
                FontVariation.width(VariableFontWidth.EXPANDED),
                counterWidth(500),
                ascenderHeight(750f),
            )
        )
    )
} else {
    null
}

@OptIn(ExperimentalTextApi::class)
val labelMediumFontFamily = if (variableFontsSupported) {
    FontFamily(
        Font(
            R.font.robotoflex_variable,
            variationSettings = FontVariation.Settings(
                FontVariation.opticalSizing(12.sp),
                FontVariation.weight(425),
                FontVariation.width(VariableFontWidth.SEMI_CONDENSED),
                FontVariation.grade(-15),
                ascenderHeight(765f),
                counterWidth(575),
                lowercaseHeight(520)
            )
        )
    )
} else {
    robotoFlex
}

@OptIn(ExperimentalTextApi::class)
val labelLargeFontFamily = if (variableFontsSupported) {
    FontFamily(
        Font(
            R.font.robotoflex_variable,
            variationSettings = FontVariation.Settings(
                FontVariation.opticalSizing(14.sp),
                FontVariation.weight(VariableFontWeight.MEDIUM),
                FontVariation.grade(-25),
                counterWidth(500),
            )
        )
    )
} else {
    robotoFlex
}

@OptIn(ExperimentalTextApi::class)
val labelLargeEmphasizedFontFamily = if (variableFontsSupported) {
    FontFamily(
        Font(
            R.font.robotoflex_variable,
            variationSettings = FontVariation.Settings(
                FontVariation.opticalSizing(14.sp),
                FontVariation.weight(VariableFontWeight.BOLD),
                FontVariation.grade(-25),
                counterWidth(500),
            )
        )
    )
} else {
    robotoFlex
}

val baseline = Typography()

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
val BuddhaQuotesTypography = Typography(
    displayLarge = baseline.displayLarge.copy(fontFamily = montserrat),
    displayMedium = baseline.displayMedium.copy(fontFamily = montserrat),
    displaySmall = baseline.displaySmall.copy(fontFamily = montserrat),
    headlineLarge = baseline.headlineLarge.copy(fontFamily = montserrat),
    headlineMedium = baseline.headlineMedium.copy(fontFamily = montserrat),
    headlineSmall = baseline.headlineSmall.copy(fontFamily = montserrat),
    titleLarge = baseline.titleLarge.copy(fontFamily = montserrat),
    titleMedium = baseline.titleMedium.copy(fontFamily = titleMediumFontFamily),
    titleSmall = baseline.titleSmall.copy(fontFamily = montserrat),
    bodyLarge = baseline.bodyLarge.copy(fontFamily = montserrat),
    bodyMedium = baseline.bodyMedium.copy(fontFamily = montserrat),
    bodySmall = baseline.bodySmall.copy(fontFamily = montserrat),
    labelLarge = baseline.labelLarge.copy(fontFamily = labelLargeFontFamily),
    labelMedium = baseline.labelMedium.copy(fontFamily = labelMediumFontFamily),
    labelSmall = baseline.labelSmall.copy(fontFamily = montserrat),
    displayLargeEmphasized = baseline.displayLargeEmphasized.copy(fontFamily = montserrat),
    displayMediumEmphasized = baseline.displayMediumEmphasized.copy(fontFamily = montserrat),
    displaySmallEmphasized = baseline.displaySmallEmphasized.copy(fontFamily = montserrat),
    headlineLargeEmphasized = baseline.headlineLargeEmphasized.copy(fontFamily = montserrat),
    headlineMediumEmphasized = baseline.headlineMediumEmphasized.copy(fontFamily = montserrat),
    headlineSmallEmphasized = baseline.headlineSmallEmphasized.copy(fontFamily = montserrat),
    titleLargeEmphasized = baseline.titleLargeEmphasized.copy(fontFamily = titleLargeEmphasizedFontFamily),
    titleMediumEmphasized = baseline.titleMediumEmphasized.copy(fontFamily = titleMediumEmphasizedFontFamily),
    titleSmallEmphasized = baseline.titleSmallEmphasized.copy(fontFamily = montserrat),
    bodyLargeEmphasized = baseline.bodyLargeEmphasized.copy(fontFamily = montserrat),
    bodyMediumEmphasized = baseline.bodyMediumEmphasized.copy(fontFamily = montserrat),
    bodySmallEmphasized = baseline.bodySmallEmphasized.copy(fontFamily = montserrat),
    labelLargeEmphasized = baseline.labelLargeEmphasized.copy(fontFamily = labelLargeEmphasizedFontFamily),
    labelMediumEmphasized = baseline.labelMediumEmphasized.copy(fontFamily = labelMediumFontFamily),
    labelSmallEmphasized = baseline.labelSmallEmphasized.copy(fontFamily = montserrat),
)

@Composable
fun TypographyPreview(
    baselineTexts: @Composable (ColumnScope) -> Unit,
    emphasizedTexts: @Composable (ColumnScope) -> Unit,
) {
    Card {
        Row(
            Modifier
                .height(IntrinsicSize.Min)
                .width(IntrinsicSize.Max)
        ) {
            Column(
                modifier = Modifier
                    .weight(weight = 0.5f, fill = false)
                    .padding(10.dp)
            ) {
                baselineTexts(this)
            }
            VerticalDivider()
            Column(
                modifier = Modifier
                    .weight(weight = 0.5f, fill = false)
                    .padding(10.dp)
            ) {
                emphasizedTexts(this)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Preview(name = "Headline")
@Composable
fun HeadlineTypography() {
    BuddhaQuotesTheme {
        TypographyPreview(
            baselineTexts = {
                Text(text = "Headline large", style = MaterialTheme.typography.headlineLarge)
                Text(text = "Headline medium", style = MaterialTheme.typography.headlineMedium)
                Text(text = "Headline small", style = MaterialTheme.typography.headlineSmall)
            },
            emphasizedTexts = {
                Text(
                    text = "Headline large",
                    style = MaterialTheme.typography.headlineLargeEmphasized
                )
                Text(
                    text = "Headline medium",
                    style = MaterialTheme.typography.headlineMediumEmphasized
                )
                Text(
                    text = "Headline small",
                    style = MaterialTheme.typography.headlineSmallEmphasized
                )
            }
        )
    }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Preview(name = "Title")
@Composable
fun TitleTypography() {
    BuddhaQuotesTheme {
        TypographyPreview(
            baselineTexts = {
                Text(text = "Title large", style = MaterialTheme.typography.titleLarge)
                Text(text = "Title medium", style = MaterialTheme.typography.titleMedium)
                Text(text = "Title small", style = MaterialTheme.typography.titleSmall)
            },
            emphasizedTexts = {
                Text(
                    text = "Title large",
                    style = MaterialTheme.typography.titleLargeEmphasized
                )
                Text(
                    text = "Title medium",
                    style = MaterialTheme.typography.titleMediumEmphasized
                )
                Text(
                    text = "Title small",
                    style = MaterialTheme.typography.titleSmallEmphasized
                )
            }
        )
    }
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Preview(name = "Body")
@Composable
fun BodyTypography() {
    TypographyPreview(
        baselineTexts = {
            Text(text = "Body large", style = MaterialTheme.typography.bodyLarge)
            Text(text = "Body medium", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Body small", style = MaterialTheme.typography.bodySmall)
        },
        emphasizedTexts = {
            Text(
                text = "Body large",
                style = MaterialTheme.typography.bodyLargeEmphasized
            )
            Text(
                text = "Body medium",
                style = MaterialTheme.typography.bodyMediumEmphasized
            )
            Text(
                text = "Body small",
                style = MaterialTheme.typography.bodySmallEmphasized
            )
        }
    )
}

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Preview(name = "Label")
@Composable
fun LabelTypography() {
    TypographyPreview(
        baselineTexts = {
            Text(text = "Label large", style = MaterialTheme.typography.labelLarge)
            Text(text = "Label medium", style = MaterialTheme.typography.labelMedium)
            Text(text = "Label small", style = MaterialTheme.typography.labelSmall)
        },
        emphasizedTexts = {
            Text(
                text = "Label large",
                style = MaterialTheme.typography.labelLargeEmphasized
            )
            Text(
                text = "Label medium",
                style = MaterialTheme.typography.labelMediumEmphasized
            )
            Text(
                text = "Label small",
                style = MaterialTheme.typography.labelSmallEmphasized
            )
        }
    )
}
