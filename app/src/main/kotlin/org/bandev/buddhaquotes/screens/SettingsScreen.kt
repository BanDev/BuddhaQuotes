package org.bandev.buddhaquotes.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExperimentalMaterial3ExpressiveApi
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialShapes
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.toShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.alorma.compose.settings.storage.base.rememberBooleanSettingState
import com.alorma.compose.settings.ui.SettingsMenuLink
import com.alorma.compose.settings.ui.SettingsSwitch
import com.maxkeppeker.sheets.core.models.base.IconSource
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.option.OptionDialog
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
import org.bandev.buddhaquotes.settings.Settings
import org.bandev.buddhaquotes.sheets.ImageSelectionSheet
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
import org.bandev.buddhaquotes.ui.theme.supportsDynamicColor

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3ExpressiveApi::class)
@Composable
fun SettingsScreen(
    onNavigate: (Scene) -> Unit,
    settingsViewModel: SettingsViewModel = hiltViewModel(),
) {
    val useCaseState = rememberUseCaseState(embedded = false)
    val settings by settingsViewModel.settings.observeAsState(Settings.getDefaultInstance())
    var imageSheetVisible by rememberSaveable { mutableStateOf(false) }
    val imageSheetState = rememberModalBottomSheetState()
    Column {
        SettingsMenuLink(
            icon = {
                ElevatedCardIcon(
                    icon = settings.theme.toFilledIcon(),
                    shape = settings.theme.toShape()
                )
            },
            title = { Text(text = stringResource(R.string.theme)) },
            subtitle = { Text(text = settings.theme.toFormattedString()) },
            onClick = useCaseState::show,
        )
        if (supportsDynamicColor) {
            SettingsSwitch(
                state = rememberBooleanSettingState(defaultValue = settings.dynamicColor),
                icon = {
                    ElevatedCardIcon(
                        icon = Symbols.Filled.FormatColorFill,
                        shape = MaterialShapes.Cookie12Sided.toShape()
                    )
                },
                title = { Text(text = stringResource(R.string.dynamic_colour)) },
                onCheckedChange = { settingsViewModel.setDynamicColour(it) }
            )
        }
        SettingsMenuLink(
            icon = {
                ElevatedCardIcon(
                    icon = Symbols.Outlined.Image,
                    shape = MaterialShapes.PixelCircle.toShape()
                )
            },
            title = { Text(text = "Background image") },
            onClick = { imageSheetVisible = true },
        )
        HorizontalDivider()
        SettingsMenuLink(
            icon = {
                ElevatedCardIcon(
                    icon = Symbols.Outlined.LocalLibrary,
                    shape = MaterialShapes.ClamShell.toShape()
                )
            },
            title = { Text(text = stringResource(R.string.libraries)) },
            onClick = { onNavigate(Scene.Libraries) },
        )
        SettingsMenuLink(
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

    OptionDialog(
        state = useCaseState,
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
        )
    )

    if (imageSheetVisible) {
        ImageSelectionSheet(
            sheetState = imageSheetState,
            onClose = { imageSheetVisible = false },
            onImageSelection = { settingsViewModel.setImage(Settings.Image.forNumber(it)) }
        )
    }
}
