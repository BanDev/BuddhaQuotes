package org.bandev.buddhaquotes.ui.images

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

val Images.DharmaWheel: ImageVector by lazy(LazyThreadSafetyMode.NONE) {
    ImageVector.Builder(
        name = "DharmaWheel",
        defaultWidth = 512.dp,
        defaultHeight = 512.dp,
        viewportWidth = 512f,
        viewportHeight = 512f
    ).apply {
        path(
            fill = Brush.linearGradient(
                colorStops = arrayOf(
                    0.32f to Color(0xFFA163F5),
                    0.47f to Color(0xFFB074EE),
                    0.75f to Color(0xFFD8A1DD),
                    0.9f to Color(0xFFEFBAD3)
                ),
                start = Offset(256f, 455f),
                end = Offset(256f, 68.01f)
            )
        ) {
            moveTo(491.55f, 239.97f)
            arcToRelative(16.42f, 16.42f, 0f, isMoreThanHalf = false, isPositiveArc = false, -4.03f, 0.29f)
            arcToRelative(16.34f, 16.34f, 0f, isMoreThanHalf = false, isPositiveArc = false, -22.12f, -9.68f)
            arcToRelative(210.86f, 210.86f, 0f, isMoreThanHalf = false, isPositiveArc = false, -47.58f, -110.09f)
            lineToRelative(11.42f, -11.44f)
            arcToRelative(20.69f, 20.69f, 0f, isMoreThanHalf = false, isPositiveArc = false, -14.62f, -35.33f)
            arcTo(20.54f, 20.54f, 0f, isMoreThanHalf = false, isPositiveArc = false, 400f, 79.79f)
            lineToRelative(-11.64f, 11.65f)
            arcToRelative(210.3f, 210.3f, 0f, isMoreThanHalf = false, isPositiveArc = false, -107.2f, -45.57f)
            arcToRelative(16.37f, 16.37f, 0f, isMoreThanHalf = false, isPositiveArc = false, -9.2f, -20.88f)
            arcToRelative(16.55f, 16.55f, 0f, isMoreThanHalf = false, isPositiveArc = false, 0.39f, -2.71f)
            arcToRelative(16.36f, 16.36f, 0f, isMoreThanHalf = true, isPositiveArc = false, -32.38f, 2.2f)
            arcToRelative(16.34f, 16.34f, 0f, isMoreThanHalf = false, isPositiveArc = false, -9.97f, 21.38f)
            arcToRelative(210.33f, 210.33f, 0f, isMoreThanHalf = false, isPositiveArc = false, -109.33f, 47.28f)
            lineToRelative(-13.3f, -13.35f)
            arcToRelative(20.69f, 20.69f, 0f, isMoreThanHalf = true, isPositiveArc = false, -29.24f, 29.27f)
            lineToRelative(13.49f, 13.46f)
            arcToRelative(213.26f, 213.26f, 0f, isMoreThanHalf = false, isPositiveArc = false, -9.97f, 13.24f)
            arcToRelative(6f, 6f, 0f, isMoreThanHalf = true, isPositiveArc = false, 9.89f, 6.79f)
            arcToRelative(199.23f, 199.23f, 0f, isMoreThanHalf = false, isPositiveArc = true, 164.01f, -86.25f)
            curveToRelative(109.94f, 0f, 199.38f, 89.55f, 199.38f, 199.62f)
            reflectiveCurveTo(365.48f, 455.56f, 255.55f, 455.56f)
            reflectiveCurveTo(56.17f, 366.01f, 56.17f, 255.94f)
            arcTo(198.65f, 198.65f, 0f, isMoreThanHalf = false, isPositiveArc = true, 72.6f, 176.62f)
            arcToRelative(5.99f, 5.99f, 0f, isMoreThanHalf = false, isPositiveArc = false, -2.16f, -7.36f)
            lineToRelative(-0.01f, -0.01f)
            arcToRelative(5.99f, 5.99f, 0f, isMoreThanHalf = false, isPositiveArc = false, -8.82f, 2.56f)
            arcToRelative(210.15f, 210.15f, 0f, isMoreThanHalf = false, isPositiveArc = false, -15.97f, 59.28f)
            arcToRelative(16.33f, 16.33f, 0f, isMoreThanHalf = false, isPositiveArc = false, -20.87f, 9.42f)
            arcToRelative(16.36f, 16.36f, 0f, isMoreThanHalf = true, isPositiveArc = false, -4.12f, 32.35f)
            curveToRelative(0.25f, 0.01f, 0.49f, 0.02f, 0.73f, 0.02f)
            arcToRelative(16.42f, 16.42f, 0f, isMoreThanHalf = false, isPositiveArc = false, 3.3f, -0.34f)
            arcTo(16.38f, 16.38f, 0f, isMoreThanHalf = false, isPositiveArc = false, 39.36f, 283.4f)
            quadToRelative(0.37f, 0.02f, 0.73f, 0.02f)
            arcToRelative(16.28f, 16.28f, 0f, isMoreThanHalf = false, isPositiveArc = false, 5.72f, -1.04f)
            arcToRelative(210.81f, 210.81f, 0f, isMoreThanHalf = false, isPositiveArc = false, 45.42f, 106.53f)
            lineTo(78.13f, 402.05f)
            arcToRelative(20.69f, 20.69f, 0f, isMoreThanHalf = false, isPositiveArc = false, 14.62f, 35.33f)
            arcToRelative(20.52f, 20.52f, 0f, isMoreThanHalf = false, isPositiveArc = false, 14.61f, -6.05f)
            lineToRelative(12.95f, -12.88f)
            arcToRelative(210.35f, 210.35f, 0f, isMoreThanHalf = false, isPositiveArc = false, 110.56f, 47.66f)
            arcToRelative(16.39f, 16.39f, 0f, isMoreThanHalf = false, isPositiveArc = false, 9.53f, 22.1f)
            arcToRelative(16.85f, 16.85f, 0f, isMoreThanHalf = false, isPositiveArc = false, -0.19f, 2.06f)
            arcToRelative(16.39f, 16.39f, 0f, isMoreThanHalf = false, isPositiveArc = false, 16f, 16.73f)
            horizontalLineToRelative(0.37f)
            arcToRelative(16.34f, 16.34f, 0f, isMoreThanHalf = false, isPositiveArc = false, 16.03f, -19.55f)
            arcToRelative(16.35f, 16.35f, 0f, isMoreThanHalf = false, isPositiveArc = false, 9.01f, -21.5f)
            arcToRelative(210.33f, 210.33f, 0f, isMoreThanHalf = false, isPositiveArc = false, 107.16f, -45.86f)
            lineTo(400f, 431.31f)
            arcToRelative(20.53f, 20.53f, 0f, isMoreThanHalf = false, isPositiveArc = false, 14.62f, 6.06f)
            arcToRelative(20.69f, 20.69f, 0f, isMoreThanHalf = false, isPositiveArc = false, 14.62f, -35.33f)
            lineToRelative(-11.07f, -11.08f)
            arcToRelative(210.86f, 210.86f, 0f, isMoreThanHalf = false, isPositiveArc = false, 47.24f, -109.81f)
            arcToRelative(16.23f, 16.23f, 0f, isMoreThanHalf = false, isPositiveArc = false, 5.55f, 1.33f)
            quadToRelative(0.47f, 0.03f, 0.93f, 0.03f)
            arcToRelative(16.33f, 16.33f, 0f, isMoreThanHalf = false, isPositiveArc = false, 15.14f, -10.24f)
            arcToRelative(16.34f, 16.34f, 0f, isMoreThanHalf = false, isPositiveArc = false, 2.69f, 0.39f)
            curveToRelative(0.31f, 0.02f, 0.62f, 0.03f, 0.93f, 0.03f)
            arcToRelative(16.37f, 16.37f, 0f, isMoreThanHalf = false, isPositiveArc = false, 0.89f, -32.72f)
            close()
            moveTo(99.48f, 113.41f)
            lineToRelative(-12.86f, -12.83f)
            arcToRelative(8.72f, 8.72f, 0f, isMoreThanHalf = false, isPositiveArc = true, 0f, -12.3f)
            arcToRelative(8.66f, 8.66f, 0f, isMoreThanHalf = false, isPositiveArc = true, 12.26f, -0.01f)
            lineToRelative(12.77f, 12.82f)
            quadTo(105.32f, 107f, 99.48f, 113.41f)
            close()
            moveTo(249.5f, 39.24f)
            arcToRelative(16.38f, 16.38f, 0f, isMoreThanHalf = false, isPositiveArc = false, 0f, 1.76f)
            arcToRelative(4.36f, 4.36f, 0f, isMoreThanHalf = true, isPositiveArc = true, -4.24f, -5.39f)
            curveToRelative(0.08f, 0f, 0.17f, 0f, 0.25f, 0.01f)
            arcToRelative(4.36f, 4.36f, 0f, isMoreThanHalf = false, isPositiveArc = true, 4.01f, 3.47f)
            curveToRelative(-0.01f, 0.05f, -0.02f, 0.1f, -0.02f, 0.15f)
            close()
            moveTo(258.93f, 24.64f)
            arcToRelative(4.32f, 4.32f, 0f, isMoreThanHalf = false, isPositiveArc = true, -3.15f, 1.1f)
            arcToRelative(4.38f, 4.38f, 0f, isMoreThanHalf = false, isPositiveArc = true, -2.66f, -7.63f)
            arcToRelative(4.32f, 4.32f, 0f, isMoreThanHalf = false, isPositiveArc = true, 2.9f, -1.11f)
            curveToRelative(0.08f, 0f, 0.17f, 0f, 0.25f, 0.01f)
            arcToRelative(4.38f, 4.38f, 0f, isMoreThanHalf = false, isPositiveArc = true, 2.66f, 7.63f)
            close()
            moveTo(268.74f, 43.42f)
            arcToRelative(4.3f, 4.3f, 0f, isMoreThanHalf = false, isPositiveArc = true, -3.15f, 1.1f)
            arcToRelative(4.36f, 4.36f, 0f, isMoreThanHalf = false, isPositiveArc = true, -4.01f, -3.47f)
            curveToRelative(0f, -0.05f, 0.01f, -0.1f, 0.02f, -0.15f)
            arcToRelative(16.38f, 16.38f, 0f, isMoreThanHalf = false, isPositiveArc = false, 0f, -1.76f)
            arcToRelative(4.35f, 4.35f, 0f, isMoreThanHalf = false, isPositiveArc = true, 4.23f, -3.36f)
            curveToRelative(0.08f, 0f, 0.17f, 0f, 0.25f, 0.01f)
            arcToRelative(4.38f, 4.38f, 0f, isMoreThanHalf = false, isPositiveArc = true, 2.66f, 7.63f)
            close()
            moveTo(397.51f, 99.28f)
            lineTo(408.49f, 88.28f)
            arcToRelative(8.66f, 8.66f, 0f, isMoreThanHalf = false, isPositiveArc = true, 12.27f, 0f)
            arcToRelative(8.72f, 8.72f, 0f, isMoreThanHalf = false, isPositiveArc = true, 0f, 12.3f)
            lineToRelative(-10.89f, 10.91f)
            quadToRelative(-5.93f, -6.35f, -12.36f, -12.21f)
            close()
            moveTo(24.3f, 259.72f)
            arcToRelative(4.29f, 4.29f, 0f, isMoreThanHalf = false, isPositiveArc = true, -3.13f, 1.14f)
            arcToRelative(4.38f, 4.38f, 0f, isMoreThanHalf = false, isPositiveArc = true, -2.75f, -7.59f)
            arcToRelative(4.32f, 4.32f, 0f, isMoreThanHalf = false, isPositiveArc = true, 2.94f, -1.15f)
            horizontalLineToRelative(0.19f)
            arcToRelative(4.38f, 4.38f, 0f, isMoreThanHalf = false, isPositiveArc = true, 2.75f, 7.59f)
            close()
            moveTo(37.05f, 243.22f)
            arcToRelative(4.32f, 4.32f, 0f, isMoreThanHalf = false, isPositiveArc = true, 2.94f, -1.15f)
            horizontalLineToRelative(0.2f)
            arcToRelative(4.38f, 4.38f, 0f, isMoreThanHalf = false, isPositiveArc = true, 2.75f, 7.59f)
            arcToRelative(4.31f, 4.31f, 0f, isMoreThanHalf = false, isPositiveArc = true, -1.99f, 1.03f)
            curveToRelative(-0.05f, 0f, -0.1f, -0.01f, -0.15f, -0.01f)
            arcToRelative(16.59f, 16.59f, 0f, isMoreThanHalf = false, isPositiveArc = false, -1.74f, 0.02f)
            arcToRelative(4.37f, 4.37f, 0f, isMoreThanHalf = false, isPositiveArc = true, -1.99f, -7.49f)
            close()
            moveTo(43.02f, 270.27f)
            arcToRelative(4.31f, 4.31f, 0f, isMoreThanHalf = false, isPositiveArc = true, -3.13f, 1.14f)
            arcToRelative(4.38f, 4.38f, 0f, isMoreThanHalf = false, isPositiveArc = true, -2.75f, -7.6f)
            arcToRelative(4.32f, 4.32f, 0f, isMoreThanHalf = false, isPositiveArc = true, 1.98f, -1.03f)
            curveToRelative(0.06f, 0f, 0.11f, 0.01f, 0.17f, 0.02f)
            curveToRelative(0.25f, 0.01f, 0.49f, 0.02f, 0.73f, 0.02f)
            curveToRelative(0.32f, 0f, 0.64f, -0.03f, 0.95f, -0.04f)
            arcToRelative(4.37f, 4.37f, 0f, isMoreThanHalf = false, isPositiveArc = true, 2.05f, 7.49f)
            close()
            moveTo(98.89f, 422.83f)
            arcToRelative(8.66f, 8.66f, 0f, isMoreThanHalf = false, isPositiveArc = true, -12.27f, 0f)
            arcToRelative(8.72f, 8.72f, 0f, isMoreThanHalf = false, isPositiveArc = true, 0f, -12.3f)
            lineToRelative(12.44f, -12.47f)
            quadToRelative(5.86f, 6.47f, 12.25f, 12.43f)
            close()
            moveTo(248.84f, 475.93f)
            arcToRelative(4.37f, 4.37f, 0f, isMoreThanHalf = true, isPositiveArc = true, -3.02f, -7.53f)
            horizontalLineToRelative(0.1f)
            arcToRelative(4.38f, 4.38f, 0f, isMoreThanHalf = false, isPositiveArc = true, 2.92f, 7.53f)
            close()
            moveTo(259.59f, 493.78f)
            arcToRelative(4.37f, 4.37f, 0f, isMoreThanHalf = true, isPositiveArc = true, -3.02f, -7.53f)
            horizontalLineToRelative(0.1f)
            arcToRelative(4.38f, 4.38f, 0f, isMoreThanHalf = false, isPositiveArc = true, 2.92f, 7.53f)
            close()
            moveTo(269.55f, 475.39f)
            arcToRelative(4.36f, 4.36f, 0f, isMoreThanHalf = false, isPositiveArc = true, -7.37f, -3.26f)
            arcToRelative(4.36f, 4.36f, 0f, isMoreThanHalf = true, isPositiveArc = true, 7.37f, 3.26f)
            close()
            moveTo(420.75f, 410.52f)
            arcToRelative(8.72f, 8.72f, 0f, isMoreThanHalf = false, isPositiveArc = true, 0f, 12.3f)
            arcToRelative(8.66f, 8.66f, 0f, isMoreThanHalf = false, isPositiveArc = true, -12.27f, 0f)
            lineToRelative(-10.59f, -10.6f)
            quadToRelative(6.42f, -5.86f, 12.33f, -12.24f)
            close()
            moveTo(474.79f, 269.4f)
            arcToRelative(4.32f, 4.32f, 0f, isMoreThanHalf = false, isPositiveArc = true, -3.15f, 1.1f)
            arcToRelative(4.37f, 4.37f, 0f, isMoreThanHalf = false, isPositiveArc = true, -0.68f, -8.63f)
            curveToRelative(0.06f, 0f, 0.12f, 0.02f, 0.18f, 0.02f)
            curveToRelative(0.31f, 0.02f, 0.62f, 0.03f, 0.93f, 0.03f)
            curveToRelative(0.25f, 0f, 0.5f, -0.02f, 0.75f, -0.03f)
            arcToRelative(4.37f, 4.37f, 0f, isMoreThanHalf = false, isPositiveArc = true, 1.96f, 7.52f)
            close()
            moveTo(474.95f, 248.81f)
            arcToRelative(4.32f, 4.32f, 0f, isMoreThanHalf = false, isPositiveArc = true, -2.01f, 1f)
            curveToRelative(-0.05f, 0f, -0.1f, -0.01f, -0.15f, -0.02f)
            arcToRelative(16.61f, 16.61f, 0f, isMoreThanHalf = false, isPositiveArc = false, -1.74f, 0f)
            arcToRelative(4.37f, 4.37f, 0f, isMoreThanHalf = false, isPositiveArc = true, -1.9f, -7.51f)
            arcToRelative(4.32f, 4.32f, 0f, isMoreThanHalf = false, isPositiveArc = true, 2.9f, -1.11f)
            curveToRelative(0.08f, 0f, 0.17f, 0f, 0.25f, 0.01f)
            arcToRelative(4.38f, 4.38f, 0f, isMoreThanHalf = false, isPositiveArc = true, 2.66f, 7.63f)
            close()
            moveTo(493.54f, 259.58f)
            arcToRelative(4.32f, 4.32f, 0f, isMoreThanHalf = false, isPositiveArc = true, -3.14f, 1.1f)
            arcToRelative(4.38f, 4.38f, 0f, isMoreThanHalf = false, isPositiveArc = true, -2.66f, -7.63f)
            arcToRelative(4.32f, 4.32f, 0f, isMoreThanHalf = false, isPositiveArc = true, 2.9f, -1.11f)
            curveToRelative(0.08f, 0f, 0.17f, 0f, 0.25f, 0.01f)
            arcToRelative(4.38f, 4.38f, 0f, isMoreThanHalf = false, isPositiveArc = true, 2.66f, 7.63f)
            close()
        }
        path(
            fill = Brush.linearGradient(
                colorStops = arrayOf(
                    0.32f to Color(0xFFA163F5),
                    0.47f to Color(0xFFB074EE),
                    0.75f to Color(0xFFD8A1DD),
                    0.9f to Color(0xFFEFBAD3)
                ),
                start = Offset(244.59f, 455f),
                end = Offset(244.59f, 68.01f)
            )
        ) {
            moveTo(248.84f, 74.81f)
            arcToRelative(6f, 6f, 0f, isMoreThanHalf = true, isPositiveArc = false, -4.25f, 1.76f)
            arcToRelative(6.06f, 6.06f, 0f, isMoreThanHalf = false, isPositiveArc = false, 4.25f, -1.76f)
            close()
        }
        path(
            fill = Brush.linearGradient(
                colorStops = arrayOf(
                    0.32f to Color(0xFFA163F5),
                    0.47f to Color(0xFFB074EE),
                    0.75f to Color(0xFFD8A1DD),
                    0.9f to Color(0xFFEFBAD3)
                ),
                start = Offset(267.1f, 455f),
                end = Offset(267.1f, 68.01f)
            )
        ) {
            moveTo(271.34f, 74.81f)
            arcToRelative(5.99f, 5.99f, 0f, isMoreThanHalf = true, isPositiveArc = false, -4.24f, 1.76f)
            arcToRelative(6.03f, 6.03f, 0f, isMoreThanHalf = false, isPositiveArc = false, 4.24f, -1.76f)
            close()
        }
        path(
            fill = Brush.linearGradient(
                colorStops = arrayOf(
                    0.32f to Color(0xFFA163F5),
                    0.47f to Color(0xFFB074EE),
                    0.75f to Color(0xFFD8A1DD),
                    0.9f to Color(0xFFEFBAD3)
                ),
                start = Offset(244.59f, 455f),
                end = Offset(244.59f, 68.01f)
            )
        ) {
            moveTo(240.35f, 437.6f)
            arcToRelative(6f, 6f, 0f, isMoreThanHalf = true, isPositiveArc = false, 4.24f, -1.76f)
            arcToRelative(6.03f, 6.03f, 0f, isMoreThanHalf = false, isPositiveArc = false, -4.24f, 1.76f)
            close()
        }
        path(
            fill = Brush.linearGradient(
                colorStops = arrayOf(
                    0.32f to Color(0xFFA163F5),
                    0.47f to Color(0xFFB074EE),
                    0.75f to Color(0xFFD8A1DD),
                    0.9f to Color(0xFFEFBAD3)
                ),
                start = Offset(267.1f, 455f),
                end = Offset(267.1f, 68.01f)
            )
        ) {
            moveTo(262.85f, 437.6f)
            arcToRelative(6f, 6f, 0f, isMoreThanHalf = true, isPositiveArc = false, 4.25f, -1.76f)
            arcToRelative(6.06f, 6.06f, 0f, isMoreThanHalf = false, isPositiveArc = false, -4.25f, 1.76f)
            close()
        }
        path(
            fill = Brush.linearGradient(
                colorStops = arrayOf(
                    0.32f to Color(0xFFA163F5),
                    0.47f to Color(0xFFB074EE),
                    0.75f to Color(0xFFD8A1DD),
                    0.9f to Color(0xFFEFBAD3)
                ),
                start = Offset(441.26f, 455f),
                end = Offset(441.26f, 68.01f)
            )
        ) {
            moveTo(441.26f, 250.94f)
            arcToRelative(6.01f, 6.01f, 0f, isMoreThanHalf = true, isPositiveArc = false, -4.24f, -1.76f)
            arcToRelative(6.01f, 6.01f, 0f, isMoreThanHalf = false, isPositiveArc = false, 4.24f, 1.76f)
            close()
            moveTo(437.02f, 271.71f)
            arcToRelative(5.99f, 5.99f, 0f, isMoreThanHalf = true, isPositiveArc = false, -1.76f, -4.24f)
            arcToRelative(6.03f, 6.03f, 0f, isMoreThanHalf = false, isPositiveArc = false, 1.76f, 4.24f)
            close()
        }
        path(
            fill = Brush.linearGradient(
                colorStops = arrayOf(
                    0.32f to Color(0xFFA163F5),
                    0.47f to Color(0xFFB074EE),
                    0.75f to Color(0xFFD8A1DD),
                    0.9f to Color(0xFFEFBAD3)
                ),
                start = Offset(70.43f, 455f),
                end = Offset(70.43f, 68.01f)
            )
        ) {
            moveTo(74.67f, 240.7f)
            arcToRelative(5.99f, 5.99f, 0f, isMoreThanHalf = true, isPositiveArc = false, 1.76f, 4.24f)
            arcToRelative(6.01f, 6.01f, 0f, isMoreThanHalf = false, isPositiveArc = false, -1.76f, -4.24f)
            close()
            moveTo(70.43f, 261.47f)
            arcToRelative(6.01f, 6.01f, 0f, isMoreThanHalf = true, isPositiveArc = false, 4.24f, 1.76f)
            arcToRelative(6.01f, 6.01f, 0f, isMoreThanHalf = false, isPositiveArc = false, -4.24f, -1.76f)
            close()
        }
        path(
            fill = Brush.linearGradient(
                colorStops = arrayOf(
                    0.32f to Color(0xFFA163F5),
                    0.47f to Color(0xFFB074EE),
                    0.75f to Color(0xFFD8A1DD),
                    0.9f to Color(0xFFEFBAD3)
                ),
                start = Offset(394.91f, 455f),
                end = Offset(394.91f, 68.01f)
            )
        ) {
            moveTo(390.67f, 375.26f)
            arcToRelative(5.99f, 5.99f, 0f, isMoreThanHalf = true, isPositiveArc = false, 4.24f, -1.76f)
            arcToRelative(6.03f, 6.03f, 0f, isMoreThanHalf = false, isPositiveArc = false, -4.24f, 1.76f)
            close()
        }
        path(
            fill = Brush.linearGradient(
                colorStops = arrayOf(
                    0.32f to Color(0xFFA163F5),
                    0.47f to Color(0xFFB074EE),
                    0.75f to Color(0xFFD8A1DD),
                    0.9f to Color(0xFFEFBAD3)
                ),
                start = Offset(379f, 455f),
                end = Offset(379f, 68.01f)
            )
        ) {
            moveTo(374.75f, 391.19f)
            arcToRelative(6.02f, 6.02f, 0f, isMoreThanHalf = true, isPositiveArc = false, 4.25f, -1.75f)
            arcToRelative(6.09f, 6.09f, 0f, isMoreThanHalf = false, isPositiveArc = false, -4.25f, 1.75f)
            close()
        }
        path(
            fill = Brush.linearGradient(
                colorStops = arrayOf(
                    0.32f to Color(0xFFA163F5),
                    0.47f to Color(0xFFB074EE),
                    0.75f to Color(0xFFD8A1DD),
                    0.9f to Color(0xFFEFBAD3)
                ),
                start = Offset(132.69f, 455f),
                end = Offset(132.69f, 68.01f)
            )
        ) {
            moveTo(136.94f, 121.21f)
            arcToRelative(6f, 6f, 0f, isMoreThanHalf = true, isPositiveArc = false, -4.25f, 1.76f)
            arcToRelative(6.04f, 6.04f, 0f, isMoreThanHalf = false, isPositiveArc = false, 4.25f, -1.76f)
            close()
        }
        path(
            fill = Brush.linearGradient(
                colorStops = arrayOf(
                    0.32f to Color(0xFFA163F5),
                    0.47f to Color(0xFFB074EE),
                    0.75f to Color(0xFFD8A1DD),
                    0.9f to Color(0xFFEFBAD3)
                ),
                start = Offset(116.78f, 455f),
                end = Offset(116.78f, 68.01f)
            )
        ) {
            moveTo(121.02f, 137.15f)
            arcToRelative(6.01f, 6.01f, 0f, isMoreThanHalf = true, isPositiveArc = false, -4.24f, 1.75f)
            arcToRelative(6.05f, 6.05f, 0f, isMoreThanHalf = false, isPositiveArc = false, 4.24f, -1.75f)
            close()
        }
        path(
            fill = Brush.linearGradient(
                colorStops = arrayOf(
                    0.32f to Color(0xFFA163F5),
                    0.47f to Color(0xFFB074EE),
                    0.75f to Color(0xFFD8A1DD),
                    0.9f to Color(0xFFEFBAD3)
                ),
                start = Offset(132.69f, 455f),
                end = Offset(132.69f, 68.01f)
            )
        ) {
            moveTo(128.45f, 391.19f)
            arcToRelative(6.02f, 6.02f, 0f, isMoreThanHalf = true, isPositiveArc = false, 4.24f, -1.75f)
            arcToRelative(6.05f, 6.05f, 0f, isMoreThanHalf = false, isPositiveArc = false, -4.24f, 1.75f)
            close()
        }
        path(
            fill = Brush.linearGradient(
                colorStops = arrayOf(
                    0.32f to Color(0xFFA163F5),
                    0.47f to Color(0xFFB074EE),
                    0.75f to Color(0xFFD8A1DD),
                    0.9f to Color(0xFFEFBAD3)
                ),
                start = Offset(116.78f, 455f),
                end = Offset(116.78f, 68.01f)
            )
        ) {
            moveTo(112.54f, 375.26f)
            arcToRelative(5.99f, 5.99f, 0f, isMoreThanHalf = true, isPositiveArc = false, 4.24f, -1.76f)
            arcToRelative(6.03f, 6.03f, 0f, isMoreThanHalf = false, isPositiveArc = false, -4.24f, 1.76f)
            close()
        }
        path(
            fill = Brush.linearGradient(
                colorStops = arrayOf(
                    0.32f to Color(0xFFA163F5),
                    0.47f to Color(0xFFB074EE),
                    0.75f to Color(0xFFD8A1DD),
                    0.9f to Color(0xFFEFBAD3)
                ),
                start = Offset(394.91f, 455f),
                end = Offset(394.91f, 68.01f)
            )
        ) {
            moveTo(399.15f, 137.15f)
            arcToRelative(6.01f, 6.01f, 0f, isMoreThanHalf = true, isPositiveArc = false, -4.24f, 1.75f)
            arcToRelative(6.05f, 6.05f, 0f, isMoreThanHalf = false, isPositiveArc = false, 4.24f, -1.75f)
            close()
        }
        path(
            fill = Brush.linearGradient(
                colorStops = arrayOf(
                    0.32f to Color(0xFFA163F5),
                    0.47f to Color(0xFFB074EE),
                    0.75f to Color(0xFFD8A1DD),
                    0.9f to Color(0xFFEFBAD3)
                ),
                start = Offset(379f, 455f),
                end = Offset(379f, 68.01f)
            )
        ) {
            moveTo(383.24f, 121.21f)
            arcToRelative(5.99f, 5.99f, 0f, isMoreThanHalf = true, isPositiveArc = false, -4.24f, 1.76f)
            arcToRelative(6.01f, 6.01f, 0f, isMoreThanHalf = false, isPositiveArc = false, 4.24f, -1.76f)
            close()
        }
        path(
            fill = Brush.linearGradient(
                colorStops = arrayOf(
                    0.32f to Color(0xFFA163F5),
                    0.47f to Color(0xFFB074EE),
                    0.75f to Color(0xFFD8A1DD),
                    0.9f to Color(0xFFEFBAD3)
                ),
                start = Offset(255.55f, 455f),
                end = Offset(255.55f, 68.01f)
            )
        ) {
            moveTo(255.55f, 428.61f)
            curveToRelative(95.1f, 0f, 172.48f, -77.46f, 172.48f, -172.68f)
            reflectiveCurveTo(350.65f, 83.26f, 255.55f, 83.26f)
            reflectiveCurveTo(83.07f, 160.72f, 83.07f, 255.94f)
            reflectiveCurveToRelative(77.37f, 172.68f, 172.48f, 172.68f)
            close()
            moveTo(126.53f, 351.37f)
            arcToRelative(159.94f, 159.94f, 0f, isMoreThanHalf = false, isPositiveArc = true, -30.2f, -75.3f)
            lineToRelative(109.12f, -1.03f)
            close()
            moveTo(134.05f, 360.79f)
            lineTo(218.88f, 278.75f)
            arcToRelative(41.6f, 41.6f, 0f, isMoreThanHalf = false, isPositiveArc = false, 10.63f, 10.89f)
            lineToRelative(-83.5f, 83.6f)
            arcToRelative(161.66f, 161.66f, 0f, isMoreThanHalf = false, isPositiveArc = true, -11.95f, -12.44f)
            close()
            moveTo(232.57f, 414.96f)
            arcToRelative(159.67f, 159.67f, 0f, isMoreThanHalf = false, isPositiveArc = true, -77.5f, -33.84f)
            lineToRelative(78.15f, -78.25f)
            close()
            moveTo(255.55f, 416.61f)
            curveToRelative(-3.69f, 0f, -7.36f, -0.14f, -10.99f, -0.38f)
            lineToRelative(0.71f, -119.85f)
            arcToRelative(40.87f, 40.87f, 0f, isMoreThanHalf = false, isPositiveArc = false, 15.06f, 0.15f)
            lineToRelative(1.49f, 119.95f)
            curveToRelative(-2.08f, 0.08f, -4.17f, 0.13f, -6.26f, 0.13f)
            close()
            moveTo(273.8f, 415.57f)
            lineTo(272.41f, 303.64f)
            lineTo(351.35f, 384.76f)
            arcToRelative(159.53f, 159.53f, 0f, isMoreThanHalf = false, isPositiveArc = true, -77.55f, 30.81f)
            close()
            moveTo(253.15f, 249.94f)
            curveToRelative(-4.16f, -1.27f, -6.72f, -3.26f, -7.84f, -6.11f)
            curveToRelative(-2.03f, -5.16f, 0.51f, -12.57f, 2.49f, -16.78f)
            arcToRelative(29.29f, 29.29f, 0f, isMoreThanHalf = false, isPositiveArc = true, 30.84f, 43.27f)
            curveToRelative(-3.14f, -7.74f, -10.48f, -15.81f, -25.49f, -20.38f)
            close()
            moveTo(360.73f, 377.19f)
            lineTo(276.09f, 290.21f)
            curveToRelative(0.5f, -0.33f, 0.98f, -0.68f, 1.46f, -1.03f)
            curveToRelative(0.16f, -0.1f, 0.31f, -0.21f, 0.45f, -0.33f)
            arcToRelative(41.61f, 41.61f, 0f, isMoreThanHalf = false, isPositiveArc = false, 8.97f, -9.3f)
            lineToRelative(86.2f, 85.57f)
            arcToRelative(162.1f, 162.1f, 0f, isMoreThanHalf = false, isPositiveArc = true, -12.45f, 12.08f)
            close()
            moveTo(381.02f, 356f)
            lineToRelative(-80.78f, -80.2f)
            lineToRelative(114.52f, 0.35f)
            arcTo(160.05f, 160.05f, 0f, isMoreThanHalf = false, isPositiveArc = true, 381.02f, 356f)
            close()
            moveTo(416.02f, 255.94f)
            quadToRelative(0f, 4.13f, -0.21f, 8.21f)
            lineToRelative(-122.12f, -0.37f)
            arcToRelative(41.12f, 41.12f, 0f, isMoreThanHalf = false, isPositiveArc = false, -0.25f, -17.01f)
            horizontalLineToRelative(122.3f)
            curveToRelative(0.17f, 3.04f, 0.27f, 6.09f, 0.27f, 9.17f)
            close()
            moveTo(380.56f, 155.32f)
            arcToRelative(160.06f, 160.06f, 0f, isMoreThanHalf = false, isPositiveArc = true, 34.07f, 79.45f)
            lineTo(299.76f, 234.77f)
            close()
            moveTo(372.68f, 146.24f)
            lineTo(286.29f, 231.19f)
            arcToRelative(41.63f, 41.63f, 0f, isMoreThanHalf = false, isPositiveArc = false, -10.57f, -9.93f)
            lineToRelative(84.49f, -87.02f)
            arcToRelative(161.85f, 161.85f, 0f, isMoreThanHalf = false, isPositiveArc = true, 12.47f, 12f)
            close()
            moveTo(273.8f, 96.31f)
            arcToRelative(159.51f, 159.51f, 0f, isMoreThanHalf = false, isPositiveArc = true, 77f, 30.4f)
            lineToRelative(-78.76f, 81.11f)
            close()
            moveTo(255.55f, 95.26f)
            curveToRelative(2.1f, 0f, 4.19f, 0.05f, 6.27f, 0.13f)
            lineToRelative(-1.89f, 119.7f)
            arcToRelative(41.13f, 41.13f, 0f, isMoreThanHalf = false, isPositiveArc = false, -14.91f, 0.26f)
            lineToRelative(-0.47f, -119.71f)
            curveToRelative(3.64f, -0.25f, 7.31f, -0.38f, 11.01f, -0.38f)
            close()
            moveTo(249.65f, 261.42f)
            curveToRelative(17.32f, 5.27f, 19.08f, 15.05f, 18.95f, 19.34f)
            arcToRelative(29.17f, 29.17f, 0f, isMoreThanHalf = false, isPositiveArc = true, -26.7f, 2.13f)
            curveToRelative(-0.1f, -0.05f, -0.2f, -0.1f, -0.3f, -0.14f)
            lineToRelative(-0.08f, -0.03f)
            arcToRelative(29.3f, 29.3f, 0f, isMoreThanHalf = false, isPositiveArc = true, -8.56f, -48.01f)
            arcToRelative(26f, 26f, 0f, isMoreThanHalf = false, isPositiveArc = false, 1.16f, 13.46f)
            curveToRelative(1.72f, 4.41f, 5.8f, 10.29f, 15.53f, 13.25f)
            close()
            moveTo(232.55f, 96.92f)
            lineTo(232.99f, 208.82f)
            lineTo(155.55f, 130.36f)
            arcToRelative(159.66f, 159.66f, 0f, isMoreThanHalf = false, isPositiveArc = true, 77f, -33.44f)
            close()
            moveTo(146.45f, 138.22f)
            lineTo(229.33f, 222.18f)
            arcToRelative(41.63f, 41.63f, 0f, isMoreThanHalf = false, isPositiveArc = false, -10.43f, 10.73f)
            lineToRelative(-84.42f, -82.34f)
            arcToRelative(162.02f, 162.02f, 0f, isMoreThanHalf = false, isPositiveArc = true, 11.96f, -12.36f)
            close()
            moveTo(126.92f, 159.97f)
            lineTo(205.45f, 236.57f)
            lineTo(96.45f, 234.91f)
            arcToRelative(159.96f, 159.96f, 0f, isMoreThanHalf = false, isPositiveArc = true, 30.47f, -74.94f)
            close()
            moveTo(212.57f, 248.68f)
            arcToRelative(40.76f, 40.76f, 0f, isMoreThanHalf = false, isPositiveArc = false, -0.01f, 14.3f)
            lineToRelative(-117.29f, 1.11f)
            quadToRelative(-0.2f, -4.05f, -0.2f, -8.15f)
            curveToRelative(0f, -3.04f, 0.09f, -6.05f, 0.26f, -9.05f)
            close()
        }
    }.build()
}

@Preview
@Composable
private fun DharmaWheelPreview() {
    Box(modifier = Modifier.padding(12.dp)) {
        Image(imageVector = Images.DharmaWheel, contentDescription = null)
    }
}
