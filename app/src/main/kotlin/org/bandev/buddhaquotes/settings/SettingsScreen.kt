package org.bandev.buddhaquotes.settings

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.LoadingIndicator
import androidx.compose.material3.MaterialShapes
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.toShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.maxkeppeker.sheets.core.models.base.IconSource
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.option.OptionView
import com.maxkeppeler.sheets.option.models.Option
import com.maxkeppeler.sheets.option.models.OptionSelection
import org.bandev.buddhaquotes.BuildConfig
import org.bandev.buddhaquotes.R
import org.bandev.buddhaquotes.Scene
import org.bandev.buddhaquotes.components.ElevatedCardIcon
import org.bandev.buddhaquotes.datastore.settings.SettingsViewModel
import org.bandev.buddhaquotes.datastore.settings.isDark
import org.bandev.buddhaquotes.datastore.settings.isLight
import org.bandev.buddhaquotes.datastore.settings.isSystem
import org.bandev.buddhaquotes.datastore.settings.toFilledIcon
import org.bandev.buddhaquotes.datastore.settings.toFormattedString
import org.bandev.buddhaquotes.datastore.settings.toShape
import org.bandev.buddhaquotes.sheets.ImageSelection
import org.bandev.buddhaquotes.ui.symbols.Symbols
import org.bandev.buddhaquotes.ui.symbols.filled.BrightnessAuto
import org.bandev.buddhaquotes.ui.symbols.filled.DarkMode
import org.bandev.buddhaquotes.ui.symbols.filled.FormatColorFill
import org.bandev.buddhaquotes.ui.symbols.filled.LightMode
import org.bandev.buddhaquotes.ui.symbols.outlined.BrightnessAuto
import org.bandev.buddhaquotes.ui.symbols.outlined.DarkMode
import org.bandev.buddhaquotes.ui.symbols.outlined.Image
import org.bandev.buddhaquotes.ui.symbols.outlined.Info
import org.bandev.buddhaquotes.ui.symbols.outlined.LightMode
import org.bandev.buddhaquotes.ui.symbols.outlined.LocalLibrary
import org.bandev.buddhaquotes.ui.theme.LeadingCardShape
import org.bandev.buddhaquotes.ui.theme.MiddleCardShape
import org.bandev.buddhaquotes.ui.theme.TrailingCardShape
import org.bandev.buddhaquotes.ui.theme.blend
import org.bandev.buddhaquotes.ui.theme.supportsDynamicColor
import org.bandev.buddhaquotes.ui.widget.modifier.CardGroup

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun SettingsScreen(
    onNavigate: (Scene) -> Unit,
    settingsViewModel: SettingsViewModel = hiltViewModel(),
) {
    val settings by settingsViewModel.settings.observeAsState()
    val dynamicColor by settingsViewModel.dynamicColour.observeAsState(true)
    var themeOptionsVisible by remember { mutableStateOf(true) }
    var imageOptionsVisible by remember { mutableStateOf(false) }
    settings?.let { settings ->
        Column {
            TopAppBar(
                title = {
                    Text(
                        stringResource(R.string.settings),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontWeight = FontWeight.SemiBold
                    )
                },
                modifier = Modifier.shadow(6.dp),
                windowInsets = TopAppBarDefaults.windowInsets.only(
                    WindowInsetsSides.End + WindowInsetsSides.Top
                ),
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surfaceContainerHigh.blend(
                        color = MaterialTheme.colorScheme.surfaceContainer,
                        fraction = 0.5f
                    )
                )
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                CardGroup {
                    SettingsItem(
                        shape = LeadingCardShape,
                        icon = {
                            ElevatedCardIcon(
                                icon = settings.theme.toFilledIcon(),
                                shape = settings.theme.toShape()
                            )
                        },
                        title = {
                            Text(
                                text = stringResource(R.string.theme),
                                style = MaterialTheme.typography.bodyLarge
                            )
                        },
                        subtitle = {
                            Text(
                                text = settings.theme.toFormattedString(),
                                style = MaterialTheme.typography.bodyMedium
                            )
                        },
                        onClick = { themeOptionsVisible = !themeOptionsVisible },
                        contentVisible = themeOptionsVisible
                    ) {
                        OptionView(
                            useCaseState = rememberUseCaseState(),
                            selection = OptionSelection.Single(
                                options = listOf(
                                    Option(
                                        icon = IconSource(if (settings.theme.isLight()) Symbols.Filled.LightMode else Symbols.Outlined.LightMode),
                                        titleText = stringResource(id = R.string.light),
                                        selected = settings.theme.isLight()
                                    ),
                                    Option(
                                        icon = IconSource(if (settings.theme.isDark()) Symbols.Filled.DarkMode else Symbols.Outlined.DarkMode),
                                        titleText = stringResource(id = R.string.dark),
                                        selected = settings.theme.isDark()
                                    ),
                                    Option(
                                        icon = IconSource(if (settings.theme.isSystem()) Symbols.Filled.BrightnessAuto else Symbols.Outlined.BrightnessAuto),
                                        titleText = stringResource(id = R.string.system),
                                        selected = settings.theme.isSystem()
                                    )
                                ),
                                withButtonView = false,
                                onSelectOption = { index, _ ->
                                    settingsViewModel.setTheme(
                                        when (index) {
                                            0 -> Settings.Theme.LIGHT
                                            1 -> Settings.Theme.DARK
                                            else -> Settings.Theme.SYSTEM
                                        }
                                    )
                                }
                            ),
                        )
                    }
                    if (supportsDynamicColor) {
                        SettingsSwitch(
                            checked = dynamicColor,
                            onCheckedChange = { settingsViewModel.setDynamicColour(it) },
                            icon = {
                                ElevatedCardIcon(
                                    icon = Symbols.Filled.FormatColorFill,
                                    shape = MaterialShapes.Cookie12Sided.toShape()
                                )
                            },
                            title = { Text(text = stringResource(R.string.dynamic_colour)) }
                        )
                    }
                    SettingsItem(
                        shape = TrailingCardShape,
                        icon = {
                            ElevatedCardIcon(
                                icon = Symbols.Outlined.Image,
                                shape = MaterialShapes.Clover4Leaf.toShape()
                            )
                        },
                        title = { Text(text = "Background image") },
                        onClick = { imageOptionsVisible = !imageOptionsVisible },
                        contentVisible = imageOptionsVisible
                    ) {
                        AnimatedVisibility(visible = imageOptionsVisible) {
                            ImageSelection(
                                image = settings.image,
                                onImageSelection = {
                                    settingsViewModel.setImage(
                                        Settings.Image.forNumber(
                                            it + 1
                                        )
                                    )
                                }
                            )
                        }
                    }
                }
                CardGroup {
                    SettingsItem(
                        shape = LeadingCardShape,
                        icon = {
                            ElevatedCardIcon(
                                icon = Symbols.Outlined.LocalLibrary,
                                shape = MaterialShapes.ClamShell.toShape()
                            )
                        },
                        title = { Text(text = stringResource(R.string.libraries)) },
                        onClick = { onNavigate(Scene.Libraries) },
                    )
                    SettingsItem(
                        shape = TrailingCardShape,
                        icon = {
                            ElevatedCardIcon(
                                icon = Symbols.Outlined.Info,
                                shape = MaterialShapes.Pill.toShape()
                            )
                        },
                        title = { Text(text = "Version") },
                        subtitle = { Text(text = BuildConfig.VERSION_NAME) },
                        onClick = {},
                    )
                }
            }
        }
    } ?: Box(Modifier.fillMaxSize()) {
        LoadingIndicator(Modifier.align(Alignment.Center))
    }
}

@Composable
fun SettingsItem(
    shape: Shape = MiddleCardShape,
    icon: (@Composable () -> Unit)? = null,
    title: @Composable () -> Unit,
    subtitle: (@Composable () -> Unit)? = null,
    onClick: () -> Unit,
    contentVisible: Boolean = false,
    content: (@Composable () -> Unit)? = null,
) {
    ElevatedCard(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        shape = shape,
        colors = CardDefaults.elevatedCardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerLow)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            if (icon != null) {
                icon()
                Spacer(Modifier.width(16.dp))
            }
            Column {
                title()
                if (subtitle != null) {
                    subtitle()
                }
            }
        }
        if (content != null) {
            AnimatedVisibility(visible = contentVisible) {
                ElevatedCard(
                    modifier = Modifier.padding(16.dp),
                    shape = RoundedCornerShape(24.dp),
                    colors = CardDefaults.elevatedCardColors(containerColor = MaterialTheme.colorScheme.surfaceContainer),
                ) {
                    content()
                }
            }
        }
    }
}

@Composable
fun SettingsSwitch(
    shape: Shape = MiddleCardShape,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    icon: (@Composable () -> Unit)? = null,
    title: @Composable () -> Unit,
    subtitle: (@Composable () -> Unit)? = null,
) {
    ElevatedCard(
        onClick = { onCheckedChange(!checked) },
        shape = shape,
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.elevatedCardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerLow)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            if (icon != null) {
                icon()
                Spacer(Modifier.width(16.dp))
            }
            Column {
                title()
                if (subtitle != null) {
                    subtitle()
                }
            }
            Spacer(Modifier.weight(1f))
            Switch(
                checked = checked,
                onCheckedChange = onCheckedChange,
            )
        }
    }
}
