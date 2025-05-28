package org.bandev.buddhaquotes.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun ElevatedCardIcon(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    shape: Shape,
    cardColors: CardColors = CardDefaults.elevatedCardColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
    iconTint: Color = MaterialTheme.colorScheme.onPrimaryContainer
) {
    ElevatedCard(
        modifier = modifier,
        shape = shape,
        colors = cardColors
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier
                .padding(10.dp)
                .align(Alignment.CenterHorizontally),
            tint = iconTint
        )
    }
}