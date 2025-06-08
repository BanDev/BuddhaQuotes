package org.bandev.buddhaquotes.ui.symbols.filled

import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.unit.dp
import org.bandev.buddhaquotes.ui.symbols.Symbols
import org.bandev.buddhaquotes.ui.theme.BuddhaQuotesTheme

val Symbols.Filled.Spa: ImageVector by lazy(LazyThreadSafetyMode.NONE) {
    ImageVector.Builder(
        name = "Filled.Spa",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 960f,
        viewportHeight = 960f
    ).apply {
        path(fill = SolidColor(Color.Black)) {
            moveTo(474f, 877f)
            quadToRelative(-174f, -26f, -276f, -134.5f)
            reflectiveQuadTo(82f, 447f)
            quadToRelative(-1f, -11f, 2.5f, -19.5f)
            reflectiveQuadTo(94f, 413f)
            quadToRelative(6f, -6f, 14.5f, -9f)
            reflectiveQuadToRelative(18.5f, -1f)
            quadToRelative(183f, 25f, 282f, 141f)
            reflectiveQuadToRelative(110f, 290f)
            quadToRelative(1f, 9f, -2.5f, 17.5f)
            reflectiveQuadTo(506f, 866f)
            quadToRelative(-6f, 6f, -14.5f, 9.5f)
            reflectiveQuadTo(474f, 877f)
            close()
            moveTo(480f, 505f)
            quadToRelative(-14f, -22f, -64.5f, -64.5f)
            reflectiveQuadTo(330f, 384f)
            quadToRelative(8f, -50f, 40.5f, -126.5f)
            reflectiveQuadTo(448f, 119f)
            quadToRelative(6f, -8f, 14.5f, -12f)
            reflectiveQuadToRelative(17.5f, -4f)
            quadToRelative(9f, 0f, 17f, 4f)
            reflectiveQuadToRelative(14f, 13f)
            quadToRelative(45f, 63f, 78.5f, 138f)
            reflectiveQuadTo(630f, 384f)
            quadToRelative(-39f, 18f, -87f, 58.5f)
            reflectiveQuadTo(480f, 505f)
            close()
            moveTo(598f, 848f)
            quadToRelative(-2f, -61f, -18.5f, -138.5f)
            reflectiveQuadTo(528f, 576f)
            quadToRelative(43f, -66f, 127.5f, -114f)
            reflectiveQuadTo(834f, 403f)
            quadToRelative(10f, -2f, 18f, 1.5f)
            reflectiveQuadToRelative(14f, 9.5f)
            quadToRelative(6f, 6f, 9.5f, 14f)
            reflectiveQuadToRelative(2.5f, 18f)
            quadToRelative(-8f, 161f, -87.5f, 261.5f)
            reflectiveQuadTo(598f, 848f)
            close()
        }
    }.build()
}

@PreviewLightDark
@Composable
private fun FilledSpaPreview() {
    BuddhaQuotesTheme {
        Surface {
            Icon(imageVector = Symbols.Filled.Spa, contentDescription = null)
        }
    }
}
