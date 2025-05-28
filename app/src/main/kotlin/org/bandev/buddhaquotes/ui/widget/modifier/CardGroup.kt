package org.bandev.buddhaquotes.ui.widget.modifier

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

@Composable
fun CardGroup(content: @Composable () -> Unit = {}) {
    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
        content()
    }
}
