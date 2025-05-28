package org.bandev.buddhaquotes.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ProvidableCompositionLocal

@Composable
fun <T> ProvidableCompositionLocal<T>.ProvidesValue(
    value: T,
    content: @Composable () -> Unit
) = CompositionLocalProvider(value = this provides value, content)
