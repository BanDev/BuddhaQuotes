package org.bandev.buddhaquotes.ui.images

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

val Images.AnahataOutline: ImageVector
    @Composable
    get() {
        if (_AnahataOutline != null) {
            return _AnahataOutline!!
        }
        _AnahataOutline = ImageVector.Builder(
            name = "AnahataOutline",
            defaultWidth = 512.dp,
            defaultHeight = 512.dp,
            viewportWidth = 512f,
            viewportHeight = 512f
        ).apply {
            val primary = MaterialTheme.colorScheme.primary
            val secondary = MaterialTheme.colorScheme.onPrimary
            path(fill = SolidColor(Color(0xFFD4E1F4))) {
                moveTo(272f, 107.08f)
                quadToRelative(-7.4f, -0.74f, -15f, -0.75f)
                arcToRelative(
                    149.68f,
                    149.68f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    0f,
                    299.36f
                )
                quadToRelative(7.59f, 0f, 15f, -0.75f)
                arcToRelative(
                    149.69f,
                    149.69f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    0f,
                    -297.87f
                )
                close()
            }
            path(fill = SolidColor(secondary)) {
                moveTo(503.06f, 250.11f)
                curveToRelative(-4.33f, -1.58f, -6.42f, -4.28f, -10.05f, -9.3f)
                curveToRelative(-9.38f, -12.98f, -19.42f, -21.11f, -29.94f, -24.32f)
                curveToRelative(8.6f, -6.85f, 14.94f, -18.1f, 18.88f, -33.63f)
                curveToRelative(1.12f, -4.42f, 5.31f, -11.69f, 6.82f, -14.12f)
                arcToRelative(
                    6f,
                    6f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    -5.24f,
                    -9.18f
                )
                curveToRelative(-4.6f, 0.12f, -7.55f, -1.61f, -12.77f, -4.94f)
                curveToRelative(-17.04f, -10.88f, -32.22f, -14.22f, -45.12f, -9.94f)
                curveToRelative(8.97f, -10.26f, 11.9f, -25.57f, 8.68f, -45.53f)
                curveToRelative(-0.73f, -4.5f, 0.23f, -12.84f, 0.66f, -15.67f)
                arcToRelative(
                    6f,
                    6f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    -8.46f,
                    -6.34f
                )
                curveToRelative(-4.18f, 1.94f, -7.57f, 1.52f, -13.68f, 0.53f)
                curveToRelative(-16.74f, -2.7f, -30.17f, -1.04f, -40.08f, 4.89f)
                curveToRelative(1.81f, -11.39f, -1.69f, -24.45f, -10.48f, -38.95f)
                curveToRelative(-2.37f, -3.9f, -4.61f, -11.98f, -5.28f, -14.76f)
                arcToRelative(
                    6f,
                    6f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    -10.22f,
                    -2.7f
                )
                curveToRelative(-3.14f, 3.37f, -6.44f, 4.25f, -12.48f, 5.64f)
                curveToRelative(-18.51f, 4.24f, -31.11f, 12f, -37.54f, 23.05f)
                curveToRelative(-1.6f, -12.69f, -10.19f, -24.73f, -25.58f, -35.85f)
                curveToRelative(-3.7f, -2.67f, -8.92f, -9.24f, -10.61f, -11.54f)
                arcToRelative(
                    6f,
                    6f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    -10.47f,
                    1.5f
                )
                curveToRelative(-1.58f, 4.33f, -4.28f, 6.42f, -9.3f, 10.05f)
                curveToRelative(-14.74f, 10.64f, -23.23f, 22.14f, -25.35f, 34.24f)
                curveToRelative(-6.59f, -10.35f, -18.85f, -17.7f, -36.56f, -21.87f)
                curveToRelative(-4.44f, -1.04f, -11.78f, -5.1f, -14.24f, -6.57f)
                arcToRelative(
                    6f,
                    6f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    -9.09f,
                    5.4f
                )
                curveToRelative(0.2f, 4.61f, -1.48f, 7.57f, -4.72f, 12.85f)
                curveToRelative(-9.21f, 15f, -12.72f, 28.5f, -10.49f, 40.22f)
                curveToRelative(-10.01f, -6.51f, -23.84f, -8.4f, -41.22f, -5.6f)
                curveToRelative(-4.5f, 0.73f, -12.84f, -0.23f, -15.67f, -0.66f)
                arcToRelative(
                    6f,
                    6f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    -6.34f,
                    8.46f
                )
                curveToRelative(1.94f, 4.18f, 1.52f, 7.57f, 0.53f, 13.68f)
                curveToRelative(-2.54f, 15.76f, -1.22f, 28.58f, 3.89f, 38.3f)
                curveToRelative(-10.91f, -1.17f, -23.33f, 2.33f, -37.05f, 10.5f)
                curveToRelative(-3.92f, 2.33f, -12.02f, 4.51f, -14.81f, 5.16f)
                arcToRelative(
                    6f,
                    6f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    -2.79f,
                    10.2f
                )
                curveToRelative(3.35f, 3.17f, 4.2f, 6.48f, 5.53f, 12.52f)
                curveToRelative(4.4f, 19.93f, 12.89f, 33.07f, 25.25f, 39.06f)
                curveToRelative(0.09f, 0.05f, 0.19f, 0.08f, 0.28f, 0.13f)
                curveToRelative(-0.11f, 0f, -0.22f, 0f, -0.33f, 0.01f)
                curveToRelative(-13.72f, 0.58f, -26.73f, 9.26f, -38.67f, 25.8f)
                curveToRelative(-2.67f, 3.7f, -9.24f, 8.92f, -11.54f, 10.62f)
                arcToRelative(
                    6f,
                    6f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    1.5f,
                    10.47f
                )
                curveToRelative(4.33f, 1.58f, 6.42f, 4.28f, 10.05f, 9.3f)
                curveToRelative(9.41f, 13.03f, 19.49f, 21.17f, 30.04f, 24.32f)
                curveToRelative(-8.66f, 6.8f, -15.03f, 18.05f, -18.99f, 33.63f)
                curveToRelative(-1.12f, 4.42f, -5.31f, 11.69f, -6.82f, 14.12f)
                arcToRelative(
                    6f,
                    6f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    5.24f,
                    9.18f
                )
                curveToRelative(4.61f, -0.11f, 7.55f, 1.61f, 12.77f, 4.94f)
                curveToRelative(12.23f, 7.81f, 23.51f, 11.73f, 33.67f, 11.73f)
                arcToRelative(
                    35.96f,
                    35.96f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    11.84f,
                    -1.95f
                )
                curveToRelative(0.03f, -0.01f, 0.06f, -0.02f, 0.09f, -0.03f)
                curveToRelative(-0.02f, 0.03f, -0.05f, 0.05f, -0.07f, 0.07f)
                curveToRelative(-9.29f, 10.13f, -12.35f, 25.48f, -9.09f, 45.64f)
                curveToRelative(0.73f, 4.5f, -0.23f, 12.84f, -0.66f, 15.67f)
                arcToRelative(
                    6f,
                    6f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    8.46f,
                    6.34f
                )
                curveToRelative(4.18f, -1.94f, 7.57f, -1.52f, 13.68f, -0.53f)
                arcToRelative(
                    92.02f,
                    92.02f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    14.61f,
                    1.25f
                )
                curveToRelative(10.13f, 0f, 18.63f, -2.09f, 25.44f, -6.22f)
                curveToRelative(-1.81f, 11.41f, 1.7f, 24.5f, 10.51f, 39.02f)
                curveToRelative(2.37f, 3.9f, 4.61f, 11.98f, 5.28f, 14.76f)
                arcToRelative(
                    6f,
                    6f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    10.22f,
                    2.7f
                )
                curveToRelative(3.14f, -3.37f, 6.44f, -4.25f, 12.48f, -5.64f)
                curveToRelative(18.59f, -4.26f, 31.2f, -12.06f, 37.55f, -23.18f)
                curveToRelative(1.53f, 12.73f, 10.11f, 24.82f, 25.57f, 35.98f)
                curveToRelative(3.7f, 2.67f, 8.92f, 9.24f, 10.61f, 11.54f)
                arcToRelative(
                    6f,
                    6f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    4.84f,
                    2.44f
                )
                arcToRelative(
                    6.08f,
                    6.08f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    0.85f,
                    -0.06f
                )
                arcToRelative(
                    6f,
                    6f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    4.79f,
                    -3.88f
                )
                curveToRelative(1.58f, -4.33f, 4.28f, -6.42f, 9.3f, -10.05f)
                curveToRelative(14.75f, -10.65f, 23.25f, -22.16f, 25.37f, -34.26f)
                curveToRelative(6.57f, 10.36f, 18.82f, 17.72f, 36.54f, 21.88f)
                curveToRelative(4.44f, 1.04f, 11.78f, 5.1f, 14.24f, 6.57f)
                arcToRelative(
                    6f,
                    6f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    9.09f,
                    -5.4f
                )
                curveToRelative(-0.2f, -4.61f, 1.48f, -7.57f, 4.72f, -12.85f)
                curveToRelative(9.24f, -15.04f, 12.75f, -28.57f, 10.5f, -40.31f)
                curveToRelative(6.99f, 4.62f, 15.89f, 6.94f, 26.6f, 6.94f)
                arcToRelative(
                    92.26f,
                    92.26f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    14.61f,
                    -1.26f
                )
                curveToRelative(4.5f, -0.73f, 12.84f, 0.23f, 15.67f, 0.65f)
                arcToRelative(
                    6f,
                    6f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    6.34f,
                    -8.46f
                )
                curveToRelative(-1.94f, -4.18f, -1.52f, -7.57f, -0.53f, -13.68f)
                curveToRelative(2.55f, -15.8f, 1.21f, -28.64f, -3.93f, -38.36f)
                arcToRelative(
                    38.65f,
                    38.65f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    4.36f,
                    0.25f
                )
                curveToRelative(9.83f, 0f, 20.78f, -3.57f, 32.73f, -10.68f)
                curveToRelative(3.92f, -2.33f, 12.02f, -4.51f, 14.81f, -5.16f)
                arcToRelative(
                    6f,
                    6f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    2.79f,
                    -10.2f
                )
                curveToRelative(-3.35f, -3.17f, -4.2f, -6.48f, -5.53f, -12.52f)
                curveToRelative(-4.4f, -19.94f, -12.9f, -33.09f, -25.26f, -39.09f)
                curveToRelative(-0.05f, -0.03f, -0.1f, -0.05f, -0.16f, -0.07f)
                horizontalLineToRelative(0.18f)
                curveToRelative(13.72f, -0.59f, 26.74f, -9.28f, 38.69f, -25.83f)
                curveToRelative(2.67f, -3.7f, 9.24f, -8.92f, 11.54f, -10.62f)
                arcToRelative(
                    6f,
                    6f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    -1.5f,
                    -10.47f
                )
                close()
                moveTo(483.29f, 264.16f)
                curveToRelative(-9.65f, 13.37f, -19.53f, 20.39f, -29.37f, 20.86f)
                curveToRelative(-11.91f, 0.57f, -20.38f, -8.9f, -20.46f, -9f)
                lineToRelative(-9.07f, 7.86f)
                arcToRelative(
                    42.7f,
                    42.7f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    13.41f,
                    9.75f
                )
                arcToRelative(
                    42.67f,
                    42.67f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    -16.15f,
                    3.61f
                )
                lineToRelative(5.23f, 10.8f)
                curveToRelative(0.11f, -0.05f, 11.56f, -5.31f, 22.19f, -0.16f)
                curveToRelative(8.89f, 4.31f, 15.21f, 14.7f, 18.78f, 30.88f)
                arcToRelative(
                    49.23f,
                    49.23f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    3.3f,
                    10.82f
                )
                arcToRelative(
                    51.55f,
                    51.55f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    -9.78f,
                    4.16f
                )
                curveToRelative(-14.17f, 8.44f, -26.02f, 10.95f, -35.24f, 7.48f)
                curveToRelative(-11.15f, -4.2f, -15.17f, -16.26f, -15.21f, -16.39f)
                lineToRelative(-11.44f, 3.61f)
                arcToRelative(
                    38.45f,
                    38.45f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    3.28f,
                    7.13f
                )
                arcToRelative(
                    38.2f,
                    38.2f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    -7.79f,
                    -0.47f
                )
                lineToRelative(0.82f, 11.97f)
                curveToRelative(0.12f, -0.01f, 12.65f, -0.61f, 20.61f, 8.12f)
                curveToRelative(6.69f, 7.33f, 8.74f, 19.36f, 6.1f, 35.74f)
                arcToRelative(
                    49.08f,
                    49.08f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    -0.92f,
                    11.27f
                )
                arcToRelative(
                    51.55f,
                    51.55f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    -10.62f,
                    0.27f
                )
                curveToRelative(-16.26f, 2.62f, -28.2f, 0.6f, -35.48f, -6.01f)
                curveToRelative(-8.82f, -8.02f, -8.1f, -20.75f, -8.1f, -20.88f)
                lineToRelative(-11.97f, -0.87f)
                arcToRelative(
                    41.07f,
                    41.07f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    1f,
                    11.02f
                )
                arcToRelative(
                    40.82f,
                    40.82f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    -9.7f,
                    -5.13f
                )
                lineToRelative(-3.82f, 11.38f)
                curveToRelative(0.12f, 0.04f, 12.02f, 4.25f, 16.04f, 15.45f)
                curveToRelative(3.34f, 9.3f, 0.64f, 21.16f, -8.01f, 35.24f)
                arcToRelative(
                    49.11f,
                    49.11f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    -5.15f,
                    10.07f
                )
                arcToRelative(
                    51.52f,
                    51.52f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    -9.93f,
                    -3.8f
                )
                curveToRelative(-16.05f, -3.77f, -26.32f, -10.19f, -30.52f, -19.08f)
                curveToRelative(-5.09f, -10.76f, 0.41f, -22.23f, 0.47f, -22.34f)
                lineToRelative(-10.73f, -5.37f)
                arcToRelative(
                    41.58f,
                    41.58f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    -3.44f,
                    11.6f
                )
                arcToRelative(
                    41.46f,
                    41.46f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    -7.58f,
                    -9.4f
                )
                lineToRelative(-7.86f, 9.07f)
                curveToRelative(0.09f, 0.08f, 9.42f, 8.51f, 8.9f, 20.32f)
                curveToRelative(-0.44f, 9.89f, -7.48f, 19.83f, -20.92f, 29.54f)
                arcToRelative(
                    49.19f,
                    49.19f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    -8.62f,
                    7.32f
                )
                arcToRelative(
                    51.55f,
                    51.55f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    -7.7f,
                    -7.32f
                )
                curveToRelative(-13.35f, -9.64f, -20.36f, -19.51f, -20.82f, -29.33f)
                curveToRelative(-0.56f, -11.91f, 8.96f, -20.4f, 9.06f, -20.48f)
                lineToRelative(-3.93f, -4.53f)
                lineToRelative(-3.91f, -4.55f)
                arcToRelative(
                    42.41f,
                    42.41f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    -8.64f,
                    11.13f
                )
                arcToRelative(
                    42.4f,
                    42.4f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    -3.64f,
                    -13.59f
                )
                lineToRelative(-10.74f, 5.36f)
                curveToRelative(0.06f, 0.11f, 5.47f, 11.56f, 0.43f, 22.24f)
                curveToRelative(-4.21f, 8.91f, -14.51f, 15.3f, -30.63f, 18.99f)
                arcToRelative(
                    49.17f,
                    49.17f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    -10.79f,
                    3.38f
                )
                arcToRelative(
                    51.52f,
                    51.52f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    -4.24f,
                    -9.74f
                )
                curveToRelative(-8.57f, -14.13f, -11.2f, -26f, -7.81f, -35.27f)
                curveToRelative(4.08f, -11.17f, 16.02f, -15.31f, 16.14f, -15.35f)
                lineToRelative(-1.89f, -5.7f)
                lineToRelative(-1.86f, -5.7f)
                arcToRelative(
                    40f,
                    40f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    -8.7f,
                    4.38f
                )
                arcToRelative(
                    40.39f,
                    40.39f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    0.75f,
                    -9.8f
                )
                lineToRelative(-11.97f, 0.87f)
                curveToRelative(0.01f, 0.13f, 0.73f, 12.86f, -8.09f, 20.88f)
                curveToRelative(-7.28f, 6.62f, -19.21f, 8.64f, -35.48f, 6.01f)
                arcToRelative(
                    49.09f,
                    49.09f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    -11.27f,
                    -0.92f
                )
                arcToRelative(
                    51.48f,
                    51.48f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    -0.27f,
                    -10.62f
                )
                curveToRelative(-2.63f, -16.29f, -0.6f, -28.25f, 6.02f, -35.54f)
                curveToRelative(8f, -8.82f, 20.67f, -8.12f, 20.8f, -8.12f)
                lineToRelative(0.41f, -5.99f)
                lineToRelative(0.44f, -5.99f)
                arcToRelative(
                    42.7f,
                    42.7f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    -16.22f,
                    2.55f
                )
                arcToRelative(
                    42.69f,
                    42.69f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    8.8f,
                    -13.87f
                )
                lineToRelative(-11.33f, -3.96f)
                curveToRelative(-0.04f, 0.12f, -4.44f, 11.93f, -15.61f, 15.79f)
                curveToRelative(-9.35f, 3.24f, -21.17f, 0.36f, -35.13f, -8.56f)
                arcToRelative(
                    49.09f,
                    49.09f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    -9.98f,
                    -5.32f
                )
                arcToRelative(
                    51.47f,
                    51.47f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    3.97f,
                    -9.86f
                )
                curveToRelative(4.04f, -15.94f, 10.63f, -26.06f, 19.57f, -30.08f)
                curveToRelative(10.88f, -4.9f, 22.32f, 0.9f, 22.43f, 0.96f)
                lineToRelative(2.78f, -5.32f)
                lineToRelative(2.81f, -5.3f)
                arcToRelative(
                    39.52f,
                    39.52f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    -7.99f,
                    -2.93f
                )
                arcToRelative(
                    39.03f,
                    39.03f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    6.31f,
                    -5.6f
                )
                lineToRelative(-9.1f, -7.82f)
                curveToRelative(-0.08f, 0.1f, -8.5f, 9.53f, -20.32f, 9.01f)
                curveToRelative(-9.86f, -0.42f, -19.77f, -7.43f, -29.45f, -20.84f)
                arcToRelative(
                    49.19f,
                    49.19f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    -7.32f,
                    -8.62f
                )
                arcToRelative(
                    51.54f,
                    51.54f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    7.32f,
                    -7.7f
                )
                curveToRelative(9.65f, -13.36f, 19.52f, -20.36f, 29.34f, -20.83f)
                curveToRelative(11.91f, -0.57f, 20.4f, 8.95f, 20.49f, 9.05f)
                lineToRelative(4.53f, -3.93f)
                lineToRelative(4.55f, -3.91f)
                arcToRelative(
                    42.85f,
                    42.85f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    -13.6f,
                    -9.88f
                )
                arcToRelative(
                    42.84f,
                    42.84f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    16.39f,
                    -3.63f
                )
                lineTo(85.2f, 203.9f)
                curveToRelative(-0.12f, 0.05f, -11.67f, 5.39f, -22.37f, 0.14f)
                curveToRelative(-8.84f, -4.34f, -15.11f, -14.7f, -18.67f, -30.8f)
                arcToRelative(
                    49.12f,
                    49.12f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    -3.3f,
                    -10.82f
                )
                arcToRelative(
                    51.55f,
                    51.55f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    9.78f,
                    -4.16f
                )
                curveToRelative(14.23f, -8.47f, 26.14f, -11.01f, 35.41f, -7.55f)
                curveToRelative(11.13f, 4.15f, 15.17f, 16.04f, 15.21f, 16.16f)
                lineToRelative(11.42f, -3.68f)
                arcToRelative(
                    37.72f,
                    37.72f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    -3.07f,
                    -6.65f
                )
                arcToRelative(
                    42.05f,
                    42.05f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    6f,
                    0.46f
                )
                curveToRelative(0.8f, 0f, 1.29f, -0.03f, 1.4f, -0.04f)
                lineToRelative(-0.8f, -11.97f)
                curveToRelative(-0.12f, 0.01f, -12.64f, 0.59f, -20.6f, -8.15f)
                curveToRelative(-6.69f, -7.35f, -8.75f, -19.38f, -6.1f, -35.77f)
                arcToRelative(
                    49.08f,
                    49.08f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    0.92f,
                    -11.27f
                )
                arcToRelative(
                    51.84f,
                    51.84f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    10.62f,
                    -0.27f
                )
                curveToRelative(16.32f, -2.63f, 28.33f, -0.6f, 35.68f, 6.03f)
                curveToRelative(8.87f, 7.99f, 8.27f, 20.62f, 8.26f, 20.75f)
                lineToRelative(5.89f, 0.36f)
                lineToRelative(6.08f, 0.41f)
                arcToRelative(
                    40.76f,
                    40.76f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    -1.01f,
                    -10.59f
                )
                arcToRelative(
                    40.87f,
                    40.87f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    9.44f,
                    4.94f
                )
                lineToRelative(3.81f, -11.38f)
                curveToRelative(-0.12f, -0.04f, -12.08f, -4.27f, -16.12f, -15.5f)
                curveToRelative(-3.34f, -9.3f, -0.65f, -21.14f, 7.99f, -35.21f)
                arcToRelative(
                    49.03f,
                    49.03f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    5.14f,
                    -10.07f
                )
                arcToRelative(
                    51.55f,
                    51.55f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    9.93f,
                    3.8f
                )
                curveToRelative(16.06f, 3.78f, 26.35f, 10.2f, 30.58f, 19.1f)
                curveToRelative(5.11f, 10.76f, -0.35f, 22.22f, -0.41f, 22.33f)
                lineToRelative(5.38f, 2.66f)
                lineToRelative(5.37f, 2.68f)
                arcToRelative(
                    41.51f,
                    41.51f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    3.39f,
                    -11.49f
                )
                arcToRelative(
                    41.62f,
                    41.62f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    7.56f,
                    9.38f
                )
                lineToRelative(7.86f, -9.06f)
                curveToRelative(-0.09f, -0.08f, -9.47f, -8.56f, -8.94f, -20.41f)
                curveToRelative(0.44f, -9.9f, 7.47f, -19.83f, 20.89f, -29.52f)
                arcToRelative(
                    49.19f,
                    49.19f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    8.62f,
                    -7.32f
                )
                arcToRelative(
                    51.55f,
                    51.55f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    7.7f,
                    7.32f
                )
                curveToRelative(13.36f, 9.65f, 20.38f, 19.54f, 20.86f, 29.38f)
                curveToRelative(0.58f, 11.93f, -8.9f, 20.45f, -9f, 20.53f)
                lineToRelative(3.95f, 4.52f)
                lineToRelative(3.92f, 4.54f)
                arcToRelative(
                    42.26f,
                    42.26f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    8.37f,
                    -10.73f
                )
                arcToRelative(
                    42.08f,
                    42.08f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    3.51f,
                    13.1f
                )
                lineToRelative(10.77f, -5.29f)
                curveToRelative(-0.05f, -0.11f, -5.39f, -11.55f, -0.3f, -22.24f)
                curveToRelative(4.26f, -8.94f, 14.6f, -15.35f, 30.74f, -19.05f)
                arcToRelative(
                    49.17f,
                    49.17f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    10.79f,
                    -3.38f
                )
                arcToRelative(
                    51.52f,
                    51.52f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    4.24f,
                    9.74f
                )
                curveToRelative(8.55f, 14.1f, 11.17f, 25.94f, 7.78f, 35.2f)
                curveToRelative(-4.1f, 11.2f, -16.13f, 15.34f, -16.25f, 15.38f)
                lineToRelative(3.72f, 11.41f)
                arcToRelative(
                    40.13f,
                    40.13f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    8.53f,
                    -4.24f
                )
                arcToRelative(
                    39.91f,
                    39.91f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    -0.78f,
                    9.48f
                )
                lineToRelative(11.98f, -0.77f)
                curveToRelative(-0.01f, -0.13f, -0.62f, -12.75f, 8.25f, -20.74f)
                curveToRelative(7.36f, -6.63f, 19.36f, -8.66f, 35.68f, -6.03f)
                arcToRelative(
                    49.19f,
                    49.19f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    11.27f,
                    0.92f
                )
                arcToRelative(
                    51.62f,
                    51.62f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    0.27f,
                    10.62f
                )
                curveToRelative(2.64f, 16.4f, 0.62f, 28.49f, -6.03f, 35.92f)
                curveToRelative(-7.92f, 8.86f, -20.36f, 8.37f, -20.48f, 8.37f)
                lineToRelative(-0.67f, 11.98f)
                curveToRelative(0.1f, 0.01f, 0.49f, 0.03f, 1.12f, 0.03f)
                arcToRelative(
                    43.29f,
                    43.29f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    13.8f,
                    -2.4f
                )
                arcToRelative(
                    42.33f,
                    42.33f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    -7.97f,
                    12.93f
                )
                lineToRelative(11.32f, 3.97f)
                curveToRelative(0.04f, -0.12f, 4.43f, -11.86f, 15.57f, -15.71f)
                curveToRelative(9.36f, -3.24f, 21.19f, -0.35f, 35.18f, 8.58f)
                arcToRelative(
                    49.12f,
                    49.12f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    9.98f,
                    5.32f
                )
                arcToRelative(
                    51.54f,
                    51.54f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    -3.97f,
                    9.86f
                )
                curveToRelative(-4.06f, 16f, -10.67f, 26.18f, -19.64f, 30.25f)
                curveToRelative(-10.84f, 4.92f, -22.19f, -0.73f, -22.31f, -0.79f)
                lineToRelative(-5.52f, 10.65f)
                arcToRelative(
                    38.86f,
                    38.86f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    7.43f,
                    2.72f
                )
                arcToRelative(
                    38.58f,
                    38.58f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    -5.94f,
                    5.28f
                )
                lineToRelative(9.07f, 7.86f)
                curveToRelative(0.08f, -0.09f, 8.52f, -9.49f, 20.35f, -8.93f)
                curveToRelative(9.89f, 0.44f, 19.82f, 7.47f, 29.52f, 20.9f)
                arcToRelative(
                    49.23f,
                    49.23f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    7.32f,
                    8.62f
                )
                arcToRelative(
                    51.49f,
                    51.49f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    -7.31f,
                    7.69f
                )
                close()
            }
            path(fill = SolidColor(primary)) {
                moveTo(256f, 75.5f)
                curveToRelative(-99.55f, 0f, -180.54f, 80.99f, -180.54f, 180.54f)
                reflectiveCurveTo(156.45f, 436.57f, 256f, 436.57f)
                reflectiveCurveToRelative(180.54f, -80.99f, 180.54f, -180.54f)
                reflectiveCurveTo(355.55f, 75.5f, 256f, 75.5f)
                close()
                moveTo(256f, 424.57f)
                curveToRelative(-92.93f, 0f, -168.54f, -75.61f, -168.54f, -168.54f)
                reflectiveCurveTo(163.07f, 87.5f, 256f, 87.5f)
                reflectiveCurveToRelative(168.54f, 75.6f, 168.54f, 168.53f)
                reflectiveCurveTo(348.93f, 424.57f, 256f, 424.57f)
                close()
            }
            path(fill = SolidColor(primary)) {
                moveTo(255.55f, 211.71f)
                arcToRelative(
                    45.14f,
                    45.14f,
                    0f,
                    isMoreThanHalf = true,
                    isPositiveArc = false,
                    45.14f,
                    45.14f
                )
                arcToRelative(
                    45.19f,
                    45.19f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    -45.14f,
                    -45.14f
                )
                close()
                moveTo(255.55f, 289.98f)
                arcToRelative(
                    33.14f,
                    33.14f,
                    0f,
                    isMoreThanHalf = true,
                    isPositiveArc = true,
                    33.14f,
                    -33.14f
                )
                arcToRelative(
                    33.17f,
                    33.17f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    -33.14f,
                    33.14f
                )
                close()
            }
            path(fill = SolidColor(primary)) {
                moveTo(260.12f, 100.37f)
                curveToRelative(-79.14f, -1.56f, -145.34f, 56.28f, -156.97f, 131.88f)
                arcToRelative(
                    6.01f,
                    6.01f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    5.93f,
                    6.94f
                )
                horizontalLineToRelative(0.01f)
                arcToRelative(
                    6f,
                    6f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    5.92f,
                    -5.1f
                )
                arcToRelative(
                    142.48f,
                    142.48f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    10.7f,
                    -36.36f
                )
                lineToRelative(35.25f, 59.1f)
                lineToRelative(-35.23f, 57.57f)
                arcToRelative(
                    142.52f,
                    142.52f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    -11.46f,
                    -41.86f
                )
                arcToRelative(
                    6f,
                    6f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = false,
                    -11.93f,
                    1.27f
                )
                curveToRelative(8.85f, 77.5f, 74.83f, 137.89f, 154.66f, 137.89f)
                curveToRelative(87.35f, 0f, 158.13f, -72.32f, 155.61f, -160.23f)
                curveToRelative(-2.37f, -82.66f, -69.83f, -149.48f, -152.5f, -151.1f)
                close()
                moveTo(382.09f, 185.52f)
                lineTo(309.46f, 185.52f)
                lineToRelative(-43.48f, -72.9f)
                arcToRelative(
                    144f,
                    144f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    116.11f,
                    72.9f
                )
                close()
                moveTo(300.7f, 314.54f)
                horizontalLineToRelative(-91.35f)
                lineTo(174.97f, 256.9f)
                lineToRelative(36.33f, -59.37f)
                horizontalLineToRelative(91.34f)
                lineToRelative(34.38f, 57.64f)
                close()
                moveTo(343.98f, 266.81f)
                lineTo(372.45f, 314.54f)
                horizontalLineToRelative(-57.68f)
                close()
                moveTo(254.44f, 390.13f)
                lineTo(216.51f, 326.54f)
                horizontalLineToRelative(76.84f)
                close()
                moveTo(257.56f, 121.93f)
                lineTo(295.49f, 185.52f)
                horizontalLineToRelative(-76.84f)
                close()
                moveTo(316.62f, 197.52f)
                lineTo(372.3f, 197.52f)
                lineToRelative(-28.2f, 46.08f)
                close()
                moveTo(249.23f, 112.55f)
                lineTo(204.58f, 185.52f)
                horizontalLineToRelative(-72.74f)
                arcToRelative(
                    143.82f,
                    143.82f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    117.39f,
                    -72.97f
                )
                close()
                moveTo(139.56f, 197.52f)
                horizontalLineToRelative(57.68f)
                lineToRelative(-29.21f, 47.73f)
                close()
                moveTo(167.9f, 268.46f)
                lineToRelative(27.49f, 46.08f)
                lineTo(139.7f, 314.54f)
                close()
                moveTo(131.87f, 326.54f)
                horizontalLineToRelative(70.67f)
                lineToRelative(43.37f, 72.72f)
                arcToRelative(
                    143.88f,
                    143.88f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    -114.05f,
                    -72.72f
                )
                close()
                moveTo(262.74f, 399.57f)
                lineTo(307.42f, 326.54f)
                horizontalLineToRelative(74.72f)
                arcToRelative(
                    143.82f,
                    143.82f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    -119.41f,
                    73.03f
                )
                close()
                moveTo(387.43f, 316.24f)
                lineTo(351.05f, 255.25f)
                lineTo(387.4f, 195.85f)
                arcToRelative(
                    143.22f,
                    143.22f,
                    0f,
                    isMoreThanHalf = false,
                    isPositiveArc = true,
                    0.03f,
                    120.4f
                )
                close()
            }
        }.build()

        return _AnahataOutline!!
    }

@Suppress("ObjectPropertyName")
private var _AnahataOutline: ImageVector? = null

@Preview
@Composable
private fun AnahataOutlinePreview() {
    Box(modifier = Modifier.padding(12.dp)) {
        Image(imageVector = Images.AnahataOutline, contentDescription = null)
    }
}
