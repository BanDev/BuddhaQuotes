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

val Images.MandalaVariant1: ImageVector by lazy(LazyThreadSafetyMode.NONE) {
    ImageVector.Builder(
        name = "MandalaVariant1",
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
                start = Offset(256.83f, 436f),
                end = Offset(256.83f, 92.91f)
            )
        ) {
            moveTo(256.83f, 74.39f)
            arcToRelative(
                19.82f,
                19.82f,
                0f,
                isMoreThanHalf = true,
                isPositiveArc = false,
                -20.01f,
                -19.82f
            )
            arcToRelative(
                19.93f,
                19.93f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = false,
                20.01f,
                19.82f
            )
            close()
            moveTo(256.83f, 42.76f)
            arcToRelative(
                11.82f,
                11.82f,
                0f,
                isMoreThanHalf = true,
                isPositiveArc = true,
                -12.01f,
                11.82f
            )
            arcToRelative(
                11.93f,
                11.93f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = true,
                12.01f,
                -11.82f
            )
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
                start = Offset(256f, 436f),
                end = Offset(256f, 92.91f)
            )
        ) {
            moveTo(455.04f, 211.65f)
            curveToRelative(12.27f, -12.38f, 18.98f, -28.8f, 19.44f, -47.85f)
            arcToRelative(
                89.03f,
                89.03f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = false,
                -3.71f,
                -28f
            )
            arcToRelative(4f, 4f, 0f, isMoreThanHalf = false, isPositiveArc = false, -0.96f, -1.55f)
            curveToRelative(-33.13f, -33.11f, -61.22f, -29.11f, -72.86f, -25.25f)
            curveToRelative(-1.59f, -23.7f, -8.7f, -45.33f, -12.43f, -55.27f)
            arcToRelative(
                10.88f,
                10.88f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = false,
                -12.06f,
                -6.85f
            )
            quadToRelative(-32.79f, 5.72f, -56.17f, 16.78f)
            curveToRelative(-11.5f, -41.38f, -56.22f, -51.17f, -58.19f, -51.58f)
            arcToRelative(
                4.03f,
                4.03f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = false,
                -1.82f,
                0.04f
            )
            curveToRelative(-45.65f, 11.81f, -56.17f, 37.93f, -58.59f, 49.83f)
            curveToRelative(-22.28f, -10.88f, -45.6f, -15.57f, -54.67f, -17.1f)
            arcToRelative(
                10.88f,
                10.88f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = false,
                -12.02f,
                6.93f
            )
            curveToRelative(-7.65f, 20.77f, -12.12f, 39.77f, -13.41f, 56.86f)
            curveToRelative(-41.96f, -10.87f, -72.89f, 22.1f, -74.23f, 23.56f)
            arcToRelative(4f, 4f, 0f, isMoreThanHalf = false, isPositiveArc = false, -0.91f, 1.64f)
            curveToRelative(-4.6f, 16.58f, -7.77f, 40.62f, 3.26f, 60.95f)
            arcToRelative(
                51.44f,
                51.44f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = false,
                11.02f,
                14.02f
            )
            curveToRelative(-19.96f, 13.22f, -35.35f, 30.1f, -42.19f, 38.27f)
            arcToRelative(
                10.71f,
                10.71f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = false,
                0.05f,
                13.85f
            )
            quadToRelative(21.55f, 25.36f, 43.15f, 39.89f)
            curveTo(45.44f, 313.18f, 38.6f, 329.5f, 37.9f, 348.38f)
            arcToRelative(
                87.83f,
                87.83f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = false,
                3.31f,
                27.76f
            )
            arcToRelative(
                3.98f,
                3.98f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = false,
                0.98f,
                1.6f
            )
            curveToRelative(22.33f, 22.31f, 42.38f, 27.76f, 56.67f, 27.76f)
            arcToRelative(
                51.7f,
                51.7f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = false,
                16.26f,
                -2.54f
            )
            curveToRelative(1.52f, 23.71f, 8.64f, 45.36f, 12.37f, 55.29f)
            arcToRelative(
                10.89f,
                10.89f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = false,
                12.06f,
                6.85f
            )
            quadToRelative(32.88f, -5.74f, 56.4f, -16.9f)
            curveToRelative(11.48f, 41.37f, 55.99f, 51.28f, 57.96f, 51.69f)
            arcToRelative(
                3.99f,
                3.99f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = false,
                1.83f,
                -0.04f
            )
            curveToRelative(45.66f, -11.81f, 56.18f, -37.94f, 58.59f, -49.84f)
            curveToRelative(21.54f, 10.59f, 44.09f, 15.33f, 54.66f, 17.1f)
            arcToRelative(
                11.03f,
                11.03f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = false,
                1.82f,
                0.15f
            )
            arcToRelative(
                10.86f,
                10.86f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = false,
                10.2f,
                -7.08f
            )
            curveToRelative(7.62f, -20.69f, 12.08f, -39.64f, 13.39f, -56.72f)
            arcToRelative(
                67.53f,
                67.53f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = false,
                16.99f,
                2.2f
            )
            curveToRelative(33.09f, 0f, 56.12f, -24.64f, 57.27f, -25.9f)
            arcToRelative(4f, 4f, 0f, isMoreThanHalf = false, isPositiveArc = false, 0.9f, -1.63f)
            curveToRelative(4.6f, -16.58f, 7.77f, -40.64f, -3.2f, -61.1f)
            arcToRelative(
                52.02f,
                52.02f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = false,
                -11f,
                -14.16f
            )
            curveToRelative(20.56f, -13.48f, 36.28f, -31.02f, 42.12f, -37.98f)
            arcToRelative(
                10.71f,
                10.71f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = false,
                -0.05f,
                -13.85f
            )
            quadToRelative(-21.21f, -24.98f, -42.4f, -39.42f)
            close()
            moveTo(397.26f, 117.41f)
            curveToRelative(7.36f, -3.06f, 33.52f, -10.36f, 66.19f, 21.81f)
            curveToRelative(1.76f, 6.07f, 11.01f, 42.96f, -15.31f, 68.01f)
            arcToRelative(
                104.8f,
                104.8f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = false,
                -32.01f,
                -13.09f
            )
            arcToRelative(
                68.35f,
                68.35f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = false,
                -22.34f,
                -1.25f
            )
            arcToRelative(
                314.24f,
                314.24f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = false,
                7.83f,
                -11.98f
            )
            arcToRelative(
                6.91f,
                6.91f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = false,
                -6.18f,
                -10.54f
            )
            quadToRelative(-7.38f, 0.27f, -14.27f, 0.77f)
            curveToRelative(12.3f, -15.4f, 16.23f, -34.86f, 16.1f, -53.74f)
            close()
            moveTo(273.75f, 212.44f)
            arcToRelative(
                31.92f,
                31.92f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = false,
                14.59f,
                -17.9f
            )
            curveToRelative(4.64f, -13.5f, 2.83f, -32.45f, -5.39f, -56.34f)
            quadToRelative(-1.08f, -3.13f, -2.24f, -6.19f)
            arcToRelative(
                41.4f,
                41.4f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = true,
                12.55f,
                -6.72f
            )
            curveToRelative(23.51f, -8.29f, 49.46f, 3.86f, 57.84f, 27.07f)
            arcToRelative(
                44.01f,
                44.01f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = true,
                2.06f,
                22.03f
            )
            curveToRelative(-19.58f, 3.28f, -34.97f, 8.4f, -45.97f, 15.33f)
            curveToRelative(-15.79f, 9.94f, -19.87f, 21.85f, -20.32f, 30.62f)
            arcToRelative(
                47.61f,
                47.61f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = false,
                -13.11f,
                -7.9f
            )
            close()
            moveTo(295.5f, 256f)
            curveToRelative(0f, 21.49f, -17.72f, 38.98f, -39.5f, 38.98f)
            reflectiveCurveTo(216.5f, 277.49f, 216.5f, 256f)
            reflectiveCurveToRelative(17.72f, -38.98f, 39.5f, -38.98f)
            reflectiveCurveToRelative(39.5f, 17.49f, 39.5f, 38.98f)
            close()
            moveTo(280.78f, 191.94f)
            arcToRelative(
                24.1f,
                24.1f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = true,
                -13.94f,
                15.04f
            )
            arcToRelative(
                23.67f,
                23.67f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = true,
                -21.04f,
                -0.87f
            )
            curveToRelative(-12.93f, -7.42f, -28.54f, -31.1f, 11.29f, -106.39f)
            curveToRelative(9.07f, 16.79f, 32.98f, 65.19f, 23.69f, 92.23f)
            close()
            moveTo(223.76f, 179.48f)
            curveToRelative(0.8f, 18.03f, 8.8f, 27.47f, 16.11f, 32.34f)
            arcToRelative(
                47.61f,
                47.61f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = false,
                -13.24f,
                7.31f
            )
            arcToRelative(
                31.6f,
                31.6f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = false,
                -8.13f,
                -21.26f
            )
            curveToRelative(-9.4f, -10.83f, -26.92f, -18.83f, -52.06f, -23.79f)
            arcToRelative(
                231.99f,
                231.99f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = false,
                -5.65f,
                -1.03f
            )
            curveToRelative(-1.08f, -5.14f, -1.65f, -9.47f, -0.75f, -14.31f)
            curveToRelative(4.49f, -24.24f, 28.11f, -40.34f, 52.66f, -35.9f)
            arcToRelative(
                46.69f,
                46.69f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = true,
                20.67f,
                9.45f
            )
            curveToRelative(-6.96f, 18.47f, -10.19f, 34.26f, -9.62f, 47.19f)
            close()
            moveTo(164.89f, 181.93f)
            curveToRelative(23.02f, 4.54f, 39.47f, 11.86f, 47.56f, 21.18f)
            arcToRelative(
                23.71f,
                23.71f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = true,
                5.99f,
                19.16f
            )
            arcToRelative(
                23.03f,
                23.03f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = true,
                -11.25f,
                17.49f
            )
            arcToRelative(
                26.06f,
                26.06f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = true,
                -13.03f,
                3.38f
            )
            curveToRelative(-15.7f, 0f, -40.31f, -12.77f, -74.62f, -66.15f)
            arcToRelative(
                294.42f,
                294.42f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = true,
                45.34f,
                4.94f
            )
            close()
            moveTo(172.78f, 245.42f)
            curveToRelative(8.17f, 4.2f, 15.33f, 5.74f, 21.44f, 5.74f)
            arcToRelative(
                33.97f,
                33.97f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = false,
                15.04f,
                -3.44f
            )
            arcToRelative(
                46.2f,
                46.2f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = false,
                -0.21f,
                15.4f
            )
            arcToRelative(
                32.48f,
                32.48f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = false,
                -22.77f,
                -3.48f
            )
            curveToRelative(-14.13f, 2.75f, -29.83f, 13.77f, -46.66f, 32.74f)
            arcToRelative(
                228.7f,
                228.7f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = false,
                -4.03f,
                4.68f
            )
            arcToRelative(
                44.4f,
                44.4f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = true,
                -18.02f,
                -70.53f
            )
            arcToRelative(
                46.53f,
                46.53f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = true,
                18.56f,
                -13.04f
            )
            curveToRelative(12.74f, 15.25f, 25f, 25.96f, 36.65f, 31.95f)
            close()
            moveTo(145.6f, 297.67f)
            curveToRelative(15.42f, -17.38f, 30.01f, -27.82f, 42.21f, -30.2f)
            arcToRelative(
                28.42f,
                28.42f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = true,
                5.46f,
                -0.55f
            )
            arcToRelative(
                24.3f,
                24.3f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = true,
                14.69f,
                4.97f
            )
            arcToRelative(
                22.96f,
                22.96f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = true,
                9.71f,
                18.35f
            )
            curveToRelative(-0.03f, 14.77f, -12.98f, 39.96f, -98.9f, 43.54f)
            arcTo(
                287.99f,
                287.99f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = true,
                145.6f,
                297.67f
            )
            close()
            moveTo(158.76f, 337.95f)
            curveToRelative(19.85f, -3.28f, 35.43f, -8.43f, 46.54f, -15.43f)
            curveToRelative(15.68f, -9.87f, 19.82f, -21.69f, 20.31f, -30.45f)
            arcToRelative(
                47.64f,
                47.64f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = false,
                13.15f,
                7.69f
            )
            arcToRelative(
                31.92f,
                31.92f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = false,
                -14.24f,
                17.7f
            )
            curveToRelative(-4.64f, 13.5f, -2.83f, 32.45f, 5.39f, 56.34f)
            arcToRelative(
                227.2f,
                227.2f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = false,
                2.02f,
                5.61f
            )
            arcToRelative(
                44.94f,
                44.94f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = true,
                -13.21f,
                7.3f
            )
            curveToRelative(-23.51f, 8.29f, -49.46f, -3.86f, -57.84f, -27.07f)
            arcToRelative(
                42.96f,
                42.96f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = true,
                -2.13f,
                -21.7f
            )
            close()
            moveTo(232.1f, 320.06f)
            arcToRelative(
                24.1f,
                24.1f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = true,
                13.94f,
                -15.04f
            )
            arcToRelative(
                24.87f,
                24.87f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = true,
                9.81f,
                -2.04f
            )
            arcToRelative(
                22.53f,
                22.53f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = true,
                11.22f,
                2.92f
            )
            curveTo(280f, 313.32f, 295.61f, 337f, 255.78f, 412.29f)
            curveTo(246.72f, 395.5f, 222.8f, 347.1f, 232.1f, 320.06f)
            close()
            moveTo(289.12f, 332.52f)
            curveToRelative(-0.81f, -18.3f, -9.03f, -27.74f, -16.42f, -32.55f)
            arcToRelative(
                47.63f,
                47.63f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = false,
                13.29f,
                -7.58f
            )
            arcToRelative(
                31.59f,
                31.59f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = false,
                8.13f,
                21.4f
            )
            curveToRelative(9.4f, 10.83f, 26.92f, 18.83f, 52.06f, 23.79f)
            curveToRelative(1.97f, 0.39f, 3.92f, 0.74f, 5.87f, 1.07f)
            arcToRelative(
                40.79f,
                40.79f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = true,
                -0.08f,
                14.61f
            )
            curveToRelative(-4.49f, 24.24f, -28.11f, 40.34f, -52.66f, 35.9f)
            arcToRelative(
                43.47f,
                43.47f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = true,
                -20f,
                -8.93f
            )
            curveToRelative(7.09f, -18.69f, 10.39f, -34.67f, 9.81f, -47.72f)
            close()
            moveTo(347.72f, 329.73f)
            curveToRelative(-23.02f, -4.54f, -39.47f, -11.86f, -47.56f, -21.18f)
            arcToRelative(
                23.71f,
                23.71f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = true,
                -5.99f,
                -19.16f
            )
            arcToRelative(
                23.04f,
                23.04f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = true,
                11.25f,
                -17.49f
            )
            curveToRelative(12.99f, -7.37f, 41.57f, -8.91f, 87.65f, 62.77f)
            arcToRelative(
                294.42f,
                294.42f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = true,
                -45.34f,
                -4.94f
            )
            close()
            moveTo(339.83f, 266.24f)
            curveToRelative(-16.7f, -8.59f, -29.21f, -6.03f, -37.09f, -1.99f)
            arcToRelative(
                46.23f,
                46.23f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = false,
                0.2f,
                -15.42f
            )
            arcToRelative(
                31.8f,
                31.8f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = false,
                16.3f,
                4.48f
            )
            arcToRelative(
                36.41f,
                36.41f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = false,
                6.97f,
                -0.69f
            )
            curveToRelative(14.13f, -2.75f, 29.83f, -13.77f, 46.66f, -32.74f)
            quadToRelative(2.09f, -2.36f, 4.08f, -4.74f)
            arcToRelative(
                41.35f,
                41.35f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = true,
                12.3f,
                7.43f
            )
            arcToRelative(
                44.26f,
                44.26f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = true,
                5.17f,
                62.92f
            )
            arcToRelative(
                42.64f,
                42.64f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = true,
                -18.21f,
                12.39f
            )
            curveToRelative(-12.65f, -15.09f, -24.82f, -25.69f, -36.39f, -31.64f)
            close()
            moveTo(366.89f, 214.57f)
            curveToRelative(-15.42f, 17.38f, -30.01f, 27.82f, -42.21f, 30.2f)
            arcToRelative(
                24.56f,
                24.56f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = true,
                -20.15f,
                -4.42f
            )
            arcToRelative(
                22.96f,
                22.96f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = true,
                -9.71f,
                -18.35f
            )
            curveToRelative(0.03f, -14.77f, 12.98f, -39.96f, 98.9f, -43.54f)
            arcToRelative(
                288f,
                288f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = true,
                -26.83f,
                36.12f
            )
            close()
            moveTo(373.83f, 54.77f)
            arcToRelative(
                2.87f,
                2.87f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = true,
                3.2f,
                1.78f
            )
            curveToRelative(3.88f, 10.33f, 10.59f, 31.03f, 12.01f, 53.63f)
            curveToRelative(1.71f, 27.37f, -4.95f, 48.22f, -19.75f, 62.02f)
            quadToRelative(-4.03f, 0.43f, -7.86f, 0.94f)
            arcToRelative(
                52.03f,
                52.03f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = false,
                -2.79f,
                -23.51f
            )
            curveToRelative(-9.87f, -27.33f, -40.38f, -41.64f, -68.02f, -31.9f)
            arcToRelative(
                50.37f,
                50.37f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = false,
                -12.94f,
                6.55f
            )
            arcToRelative(
                286.67f,
                286.67f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = false,
                -3.08f,
                -7.14f
            )
            curveToRelative(0.57f, -3f, 3.17f, -12.99f, 13.74f, -24.29f)
            curveToRelative(21.42f, -22.9f, 58.32f, -33.35f, 85.5f, -38.09f)
            close()
            moveTo(257.32f, 20.12f)
            curveToRelative(6.13f, 1.55f, 42.84f, 12.22f, 51.66f, 47.26f)
            arcToRelative(
                103.66f,
                103.66f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = false,
                -27.06f,
                20.63f
            )
            arcToRelative(
                66.77f,
                66.77f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = false,
                -12.3f,
                18.55f
            )
            arcToRelative(
                312.85f,
                312.85f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = false,
                -6.44f,
                -12.41f
            )
            arcToRelative(
                6.94f,
                6.94f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = false,
                -6.09f,
                -3.59f
            )
            horizontalLineToRelative(-0.04f)
            arcToRelative(
                6.95f,
                6.95f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = false,
                -6.1f,
                3.67f
            )
            quadToRelative(-3.46f, 6.47f, -6.48f, 12.63f)
            curveToRelative(-6.47f, -16.21f, -19.65f, -29.95f, -39.33f, -40.99f)
            curveToRelative(1.04f, -7.64f, 7.82f, -33.92f, 52.18f, -45.74f)
            close()
            moveTo(138.5f, 54.56f)
            arcToRelative(
                2.87f,
                2.87f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = true,
                3.18f,
                -1.81f
            )
            curveToRelative(8.81f, 1.48f, 31.62f, 6.07f, 53.15f, 16.72f)
            curveToRelative(24.92f, 12.32f, 39.92f, 28.48f, 44.64f, 48.04f)
            quadToRelative(-1.6f, 3.58f, -3.04f, 7.04f)
            arcToRelative(
                54.82f,
                54.82f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = false,
                -22.32f,
                -9.59f
            )
            curveToRelative(-28.87f, -5.21f, -56.66f, 13.77f, -61.95f, 42.32f)
            arcToRelative(
                38.97f,
                38.97f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = false,
                0.21f,
                14.46f
            )
            arcToRelative(
                292.97f,
                292.97f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = false,
                -8.74f,
                -1.07f
            )
            curveToRelative(-2.27f, -1.91f, -9.83f, -9.12f, -14.5f, -23.95f)
            curveToRelative(-9.36f, -29.76f, -0.08f, -66.55f, 9.36f, -92.17f)
            close()
            moveTo(49.87f, 136.97f)
            curveToRelative(4.51f, -4.5f, 32.19f, -30.16f, 67.33f, -20.12f)
            arcToRelative(
                101.26f,
                101.26f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = false,
                4.56f,
                33.06f
            )
            arcToRelative(
                66.63f,
                66.63f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = false,
                10.05f,
                19.72f
            )
            curveToRelative(-5.67f, -0.4f, -10.52f, -0.59f, -14.08f, -0.69f)
            arcToRelative(
                7.02f,
                7.02f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = false,
                -6.23f,
                3.52f
            )
            arcToRelative(
                6.88f,
                6.88f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = false,
                0.18f,
                7.1f
            )
            quadToRelative(3.87f, 6.07f, 7.68f, 11.64f)
            curveToRelative(-19.79f, -2.88f, -38.95f, 3.55f, -55.53f, 13.19f)
            curveToRelative(-6.34f, -4.75f, -25.8f, -23.45f, -13.97f, -67.42f)
            close()
            moveTo(20.65f, 255.76f)
            arcToRelative(
                2.73f,
                2.73f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = true,
                -0.01f,
                -3.53f
            )
            curveToRelative(5.72f, -6.82f, 21.18f, -24.09f, 41.16f, -37.16f)
            curveToRelative(23.25f, -15.2f, 44.92f, -19.97f, 64.45f, -14.21f)
            quadToRelative(2.33f, 3.15f, 4.63f, 6.1f)
            arcToRelative(
                54.79f,
                54.79f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = false,
                -19.38f,
                14.32f
            )
            arcToRelative(
                52.64f,
                52.64f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = false,
                18.81f,
                82.23f
            )
            quadToRelative(-2.44f, 3.1f, -4.66f, 6.11f)
            curveToRelative(-2.94f, 1.01f, -13.15f, 3.78f, -28.52f, 0.34f)
            curveToRelative(-31.04f, -6.95f, -58.71f, -33.31f, -76.47f, -54.21f)
            close()
            moveTo(114.83f, 394.54f)
            curveToRelative(-7.34f, 2.96f, -33.8f, 10.2f, -66.26f, -21.74f)
            curveToRelative(-1.69f, -6.12f, -10.28f, -42.59f, 16.09f, -67.59f)
            arcToRelative(
                105.38f,
                105.38f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = false,
                31.51f,
                12.73f
            )
            arcToRelative(
                74.17f,
                74.17f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = false,
                15.51f,
                1.7f
            )
            arcToRelative(
                61.12f,
                61.12f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = false,
                7.1f,
                -0.41f
            )
            curveToRelative(-3.31f, 4.82f, -5.99f, 9f, -7.9f, 12.08f)
            arcToRelative(
                6.86f,
                6.86f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = false,
                -0.1f,
                7.08f
            )
            arcToRelative(
                6.95f,
                6.95f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = false,
                6.02f,
                3.46f
            )
            horizontalLineToRelative(0.26f)
            quadToRelative(7.21f, -0.27f, 13.96f, -0.75f)
            curveToRelative(-12.28f, 15.34f, -16.26f, 34.66f, -16.19f, 53.43f)
            close()
            moveTo(138.18f, 457.23f)
            arcToRelative(
                2.87f,
                2.87f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = true,
                -3.2f,
                -1.78f
            )
            curveToRelative(-3.11f, -8.28f, -10.49f, -30.07f, -11.92f, -53.49f)
            curveToRelative(-1.66f, -27.28f, 5.03f, -48.08f, 19.84f, -61.89f)
            quadToRelative(3.9f, -0.41f, 7.62f, -0.9f)
            arcToRelative(
                51f,
                51f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = false,
                2.86f,
                23.19f
            )
            arcToRelative(
                53.6f,
                53.6f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = false,
                81.61f,
                24.8f
            )
            quadToRelative(1.55f, 3.76f, 3.12f, 7.28f)
            curveToRelative(-0.62f, 3.05f, -3.36f, 13.12f, -14.06f, 24.49f)
            curveToRelative(-21.7f, 23.06f, -58.67f, 33.56f, -85.86f, 38.3f)
            close()
            moveTo(254.7f, 491.87f)
            curveToRelative(-6.14f, -1.59f, -42.66f, -12.44f, -51.45f, -47.38f)
            arcToRelative(
                105.31f,
                105.31f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = false,
                27.18f,
                -20.69f
            )
            arcToRelative(
                68.28f,
                68.28f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = false,
                12.6f,
                -18.84f
            )
            arcToRelative(
                314.76f,
                314.76f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = false,
                6.68f,
                12.88f
            )
            arcToRelative(
                6.94f,
                6.94f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = false,
                6.09f,
                3.59f
            )
            horizontalLineToRelative(0.04f)
            arcToRelative(
                6.95f,
                6.95f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = false,
                6.1f,
                -3.67f
            )
            quadToRelative(3.36f, -6.29f, 6.31f, -12.29f)
            curveToRelative(7.26f, 18.2f, 22.18f, 31.28f, 38.63f, 40.62f)
            curveToRelative(-1.02f, 7.59f, -7.76f, 33.93f, -52.17f, 45.78f)
            close()
            moveTo(373.5f, 457.44f)
            arcToRelative(
                2.87f,
                2.87f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = true,
                -3.18f,
                1.81f
            )
            curveToRelative(-10.95f, -1.84f, -32.36f, -6.43f, -52.8f, -16.53f)
            curveToRelative(-24.69f, -12.2f, -39.58f, -28.3f, -44.28f, -47.85f)
            quadToRelative(1.58f, -3.51f, 3f, -6.91f)
            arcToRelative(
                52.32f,
                52.32f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = false,
                21.66f,
                9.08f
            )
            arcToRelative(
                53.94f,
                53.94f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = false,
                9.59f,
                0.86f
            )
            curveToRelative(25.2f, 0f, 47.66f, -17.82f, 52.35f, -43.18f)
            arcToRelative(
                48.21f,
                48.21f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = false,
                0.48f,
                -14.79f
            )
            curveToRelative(2.74f, 0.38f, 5.43f, 0.7f, 8.03f, 0.99f)
            curveToRelative(5.14f, 4.36f, 35.37f, 34.43f, 5.15f, 116.53f)
            close()
            moveTo(462.12f, 375.03f)
            curveToRelative(-4.51f, 4.5f, -32.31f, 30.26f, -67.34f, 20.27f)
            arcToRelative(
                102.26f,
                102.26f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = false,
                -4.57f,
                -33.42f
            )
            arcToRelative(
                67.49f,
                67.49f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = false,
                -10.09f,
                -19.89f
            )
            curveToRelative(5.96f, 0.43f, 11.05f, 0.64f, 14.76f, 0.74f)
            horizontalLineToRelative(0.18f)
            arcToRelative(
                6.92f,
                6.92f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = false,
                5.86f,
                -10.62f
            )
            quadToRelative(-3.97f, -6.24f, -7.89f, -11.94f)
            arcToRelative(
                67.2f,
                67.2f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = false,
                9.83f,
                0.72f
            )
            curveToRelative(14.46f, 0f, 29.64f, -4.55f, 45.36f, -13.6f)
            curveToRelative(6.33f, 4.82f, 25.75f, 23.75f, 13.91f, 67.75f)
            close()
            moveTo(491.35f, 259.77f)
            curveToRelative(-5.67f, 6.76f, -21f, 23.87f, -40.96f, 36.82f)
            curveToRelative(-23.12f, 15f, -44.74f, 19.66f, -64.28f, 13.88f)
            quadToRelative(-2.32f, -3.14f, -4.63f, -6.08f)
            arcToRelative(
                50.69f,
                50.69f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = false,
                19.01f,
                -13.68f
            )
            arcToRelative(
                52.26f,
                52.26f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = false,
                -6.09f,
                -74.26f
            )
            arcToRelative(
                50.87f,
                50.87f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = false,
                -12.19f,
                -7.78f
            )
            quadToRelative(2.44f, -3.1f, 4.66f, -6.11f)
            curveToRelative(2.79f, -0.99f, 12.92f, -3.87f, 28.3f, -0.44f)
            curveToRelative(30.79f, 6.88f, 58.41f, 33.23f, 76.16f, 54.12f)
            arcToRelative(
                2.73f,
                2.73f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = true,
                0.01f,
                3.53f
            )
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
                start = Offset(79.88f, 436f),
                end = Offset(79.88f, 92.91f)
            )
        ) {
            moveTo(69.89f, 171.74f)
            arcToRelative(
                20.14f,
                20.14f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = false,
                27.3f,
                -7.25f
            )
            arcToRelative(
                19.71f,
                19.71f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = false,
                -7.33f,
                -27.1f
            )
            arcToRelative(
                20.15f,
                20.15f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = false,
                -27.3f,
                7.24f
            )
            arcToRelative(
                19.71f,
                19.71f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = false,
                7.33f,
                27.1f
            )
            close()
            moveTo(69.47f, 148.68f)
            arcToRelative(
                12.13f,
                12.13f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = true,
                16.43f,
                -4.33f
            )
            arcToRelative(
                11.76f,
                11.76f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = true,
                5.59f,
                7.21f
            )
            arcToRelative(
                11.59f,
                11.59f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = true,
                -1.2f,
                8.9f
            )
            arcToRelative(
                12.12f,
                12.12f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = true,
                -16.43f,
                4.34f
            )
            arcToRelative(
                11.76f,
                11.76f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = true,
                -5.59f,
                -7.21f
            )
            arcToRelative(
                11.58f,
                11.58f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = true,
                1.2f,
                -8.91f
            )
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
                start = Offset(79.04f, 436f),
                end = Offset(79.04f, 92.91f)
            )
        ) {
            moveTo(69.06f, 338.83f)
            arcToRelative(
                19.72f,
                19.72f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = false,
                -7.33f,
                27.1f
            )
            arcToRelative(
                20.14f,
                20.14f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = false,
                27.3f,
                7.24f
            )
            arcToRelative(
                19.71f,
                19.71f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = false,
                7.33f,
                -27.1f
            )
            arcToRelative(
                20.14f,
                20.14f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = false,
                -27.3f,
                -7.24f
            )
            close()
            moveTo(90.66f, 359.01f)
            arcToRelative(
                11.76f,
                11.76f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = true,
                -5.59f,
                7.21f
            )
            arcToRelative(
                12.12f,
                12.12f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = true,
                -16.43f,
                -4.33f
            )
            arcToRelative(
                11.59f,
                11.59f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = true,
                -1.2f,
                -8.9f
            )
            arcToRelative(
                11.77f,
                11.77f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = true,
                5.59f,
                -7.21f
            )
            arcToRelative(
                12.12f,
                12.12f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = true,
                16.43f,
                4.33f
            )
            arcToRelative(
                11.59f,
                11.59f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = true,
                1.19f,
                8.9f
            )
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
                start = Offset(255.17f, 436f),
                end = Offset(255.17f, 92.91f)
            )
        ) {
            moveTo(255.17f, 437.61f)
            arcToRelative(
                19.82f,
                19.82f,
                0f,
                isMoreThanHalf = true,
                isPositiveArc = false,
                20.01f,
                19.82f
            )
            arcToRelative(
                19.93f,
                19.93f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = false,
                -20.01f,
                -19.82f
            )
            close()
            moveTo(255.17f, 469.24f)
            arcToRelative(
                11.82f,
                11.82f,
                0f,
                isMoreThanHalf = true,
                isPositiveArc = true,
                12.01f,
                -11.82f
            )
            arcToRelative(
                11.93f,
                11.93f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = true,
                -12.01f,
                11.82f
            )
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
                start = Offset(432.12f, 436f),
                end = Offset(432.12f, 92.91f)
            )
        ) {
            moveTo(442.11f, 340.26f)
            arcToRelative(
                20.15f,
                20.15f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = false,
                -27.3f,
                7.25f
            )
            arcToRelative(
                19.71f,
                19.71f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = false,
                7.33f,
                27.1f
            )
            arcToRelative(
                20.15f,
                20.15f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = false,
                27.3f,
                -7.25f
            )
            arcToRelative(
                19.72f,
                19.72f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = false,
                -7.33f,
                -27.1f
            )
            close()
            moveTo(442.53f, 363.32f)
            arcToRelative(
                12.12f,
                12.12f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = true,
                -16.43f,
                4.33f
            )
            arcToRelative(
                11.76f,
                11.76f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = true,
                -5.59f,
                -7.21f
            )
            arcToRelative(
                11.59f,
                11.59f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = true,
                1.2f,
                -8.9f
            )
            arcToRelative(
                12.12f,
                12.12f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = true,
                16.43f,
                -4.33f
            )
            arcToRelative(
                11.76f,
                11.76f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = true,
                5.59f,
                7.21f
            )
            arcToRelative(
                11.58f,
                11.58f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = true,
                -1.2f,
                8.9f
            )
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
                start = Offset(432.96f, 436f),
                end = Offset(432.96f, 92.91f)
            )
        ) {
            moveTo(433f, 175.81f)
            arcToRelative(
                20.02f,
                20.02f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = false,
                9.94f,
                -2.64f
            )
            arcToRelative(
                19.71f,
                19.71f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = false,
                7.33f,
                -27.1f
            )
            arcToRelative(
                20.14f,
                20.14f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = false,
                -27.3f,
                -7.25f
            )
            arcToRelative(
                19.71f,
                19.71f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = false,
                -7.33f,
                27.1f
            )
            arcTo(20.07f, 20.07f, 0f, isMoreThanHalf = false, isPositiveArc = false, 433f, 175.81f)
            close()
            moveTo(421.35f, 152.99f)
            arcToRelative(
                11.76f,
                11.76f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = true,
                5.59f,
                -7.21f
            )
            arcToRelative(
                12.13f,
                12.13f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = true,
                16.43f,
                4.33f
            )
            arcToRelative(
                11.58f,
                11.58f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = true,
                1.2f,
                8.9f
            )
            arcToRelative(
                11.76f,
                11.76f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = true,
                -5.59f,
                7.21f
            )
            arcToRelative(
                12.12f,
                12.12f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = true,
                -16.43f,
                -4.33f
            )
            arcToRelative(
                11.59f,
                11.59f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = true,
                -1.2f,
                -8.9f
            )
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
                start = Offset(257.27f, 436f),
                end = Offset(257.27f, 92.91f)
            )
        ) {
            moveTo(238.71f, 184.19f)
            arcToRelative(
                18.56f,
                18.56f,
                0f,
                isMoreThanHalf = true,
                isPositiveArc = false,
                18.56f,
                -18.38f
            )
            arcToRelative(
                18.49f,
                18.49f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = false,
                -18.56f,
                18.38f
            )
            close()
            moveTo(257.27f, 173.81f)
            arcToRelative(
                10.38f,
                10.38f,
                0f,
                isMoreThanHalf = true,
                isPositiveArc = true,
                -10.56f,
                10.38f
            )
            arcToRelative(
                10.48f,
                10.48f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = true,
                10.56f,
                -10.38f
            )
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
                start = Offset(193.69f, 436f),
                end = Offset(193.69f, 92.91f)
            )
        ) {
            moveTo(184.43f, 234.94f)
            arcToRelative(
                18.68f,
                18.68f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = false,
                25.32f,
                -6.72f
            )
            arcToRelative(
                18.29f,
                18.29f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = false,
                -6.8f,
                -25.14f
            )
            arcToRelative(
                18.68f,
                18.68f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = false,
                -25.32f,
                6.72f
            )
            arcToRelative(
                18.28f,
                18.28f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = false,
                6.8f,
                25.14f
            )
            close()
            moveTo(184.54f, 213.84f)
            arcToRelative(
                10.66f,
                10.66f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = true,
                14.45f,
                -3.81f
            )
            arcToRelative(
                10.34f,
                10.34f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = true,
                4.91f,
                6.34f
            )
            arcToRelative(
                10.18f,
                10.18f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = true,
                -1.05f,
                7.82f
            )
            arcToRelative(
                10.65f,
                10.65f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = true,
                -14.45f,
                3.81f
            )
            arcToRelative(
                10.33f,
                10.33f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = true,
                -4.91f,
                -6.33f
            )
            arcToRelative(
                10.17f,
                10.17f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = true,
                1.06f,
                -7.82f
            )
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
                start = Offset(192.43f, 436f),
                end = Offset(192.43f, 92.91f)
            )
        ) {
            moveTo(192.46f, 309.2f)
            arcToRelative(
                18.58f,
                18.58f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = false,
                9.22f,
                -2.44f
            )
            arcToRelative(
                18.29f,
                18.29f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = false,
                6.8f,
                -25.14f
            )
            arcToRelative(
                18.68f,
                18.68f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = false,
                -25.32f,
                -6.72f
            )
            arcToRelative(
                18.29f,
                18.29f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = false,
                -6.8f,
                25.14f
            )
            arcToRelative(
                18.6f,
                18.6f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = false,
                16.1f,
                9.17f
            )
            close()
            moveTo(182.22f, 288.18f)
            arcToRelative(
                10.33f,
                10.33f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = true,
                4.91f,
                -6.33f
            )
            arcToRelative(
                10.66f,
                10.66f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = true,
                14.45f,
                3.81f
            )
            arcToRelative(
                10.17f,
                10.17f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = true,
                1.05f,
                7.82f
            )
            arcToRelative(
                10.34f,
                10.34f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = true,
                -4.91f,
                6.34f
            )
            arcTo(10.66f, 10.66f, 0f, isMoreThanHalf = false, isPositiveArc = true, 183.28f, 296f)
            arcToRelative(
                10.17f,
                10.17f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = true,
                -1.05f,
                -7.82f
            )
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
                start = Offset(254.73f, 436f),
                end = Offset(254.73f, 92.91f)
            )
        ) {
            moveTo(273.29f, 327.81f)
            arcToRelative(
                18.56f,
                18.56f,
                0f,
                isMoreThanHalf = true,
                isPositiveArc = false,
                -18.56f,
                18.38f
            )
            arcToRelative(
                18.49f,
                18.49f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = false,
                18.56f,
                -18.38f
            )
            close()
            moveTo(254.73f, 338.19f)
            arcToRelative(
                10.38f,
                10.38f,
                0f,
                isMoreThanHalf = true,
                isPositiveArc = true,
                10.56f,
                -10.38f
            )
            arcToRelative(
                10.48f,
                10.48f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = true,
                -10.56f,
                10.38f
            )
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
                start = Offset(318.31f, 436f),
                end = Offset(318.31f, 92.91f)
            )
        ) {
            moveTo(327.57f, 277.06f)
            arcToRelative(
                18.68f,
                18.68f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = false,
                -25.32f,
                6.72f
            )
            arcToRelative(
                18.29f,
                18.29f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = false,
                6.8f,
                25.14f
            )
            arcToRelative(
                18.68f,
                18.68f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = false,
                25.32f,
                -6.72f
            )
            arcToRelative(
                18.28f,
                18.28f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = false,
                -6.8f,
                -25.14f
            )
            close()
            moveTo(327.46f, 298.16f)
            arcToRelative(
                10.65f,
                10.65f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = true,
                -14.45f,
                3.81f
            )
            arcToRelative(
                10.34f,
                10.34f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = true,
                -4.91f,
                -6.34f
            )
            arcToRelative(
                10.18f,
                10.18f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = true,
                1.05f,
                -7.82f
            )
            arcToRelative(
                10.66f,
                10.66f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = true,
                14.45f,
                -3.81f
            )
            arcToRelative(
                10.33f,
                10.33f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = true,
                4.91f,
                6.33f
            )
            arcToRelative(
                10.17f,
                10.17f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = true,
                -1.06f,
                7.82f
            )
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
                start = Offset(319.57f, 436f),
                end = Offset(319.57f, 92.91f)
            )
        ) {
            moveTo(310.31f, 205.25f)
            arcToRelative(
                18.29f,
                18.29f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = false,
                -6.8f,
                25.14f
            )
            arcToRelative(
                18.68f,
                18.68f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = false,
                25.32f,
                6.72f
            )
            arcToRelative(
                18.29f,
                18.29f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = false,
                6.8f,
                -25.14f
            )
            arcToRelative(
                18.68f,
                18.68f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = false,
                -25.32f,
                -6.72f
            )
            close()
            moveTo(329.78f, 223.82f)
            arcToRelative(
                10.33f,
                10.33f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = true,
                -4.91f,
                6.33f
            )
            arcToRelative(
                10.66f,
                10.66f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = true,
                -14.45f,
                -3.81f
            )
            arcToRelative(
                10.17f,
                10.17f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = true,
                -1.05f,
                -7.82f
            )
            arcToRelative(
                10.34f,
                10.34f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = true,
                4.91f,
                -6.34f
            )
            arcTo(10.66f, 10.66f, 0f, isMoreThanHalf = false, isPositiveArc = true, 328.72f, 216f)
            arcToRelative(
                10.17f,
                10.17f,
                0f,
                isMoreThanHalf = false,
                isPositiveArc = true,
                1.05f,
                7.82f
            )
            close()
        }
    }.build()
}

@Preview
@Composable
private fun MandalaVariant1Preview() {
    Box(modifier = Modifier.padding(12.dp)) {
        Image(imageVector = Images.MandalaVariant1, contentDescription = null)
    }
}
