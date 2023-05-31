package org.bandev.buddhaquotes.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Brightness6
import androidx.compose.material.icons.rounded.DarkMode
import androidx.compose.material.icons.rounded.LightMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.alorma.compose.settings.ui.SettingsMenuLink
import com.maxkeppeker.sheets.core.models.base.IconSource
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.option.OptionDialog
import com.maxkeppeler.sheets.option.models.Option
import com.maxkeppeler.sheets.option.models.OptionSelection
import org.bandev.buddhaquotes.R
import org.bandev.buddhaquotes.settings.Settings
import org.bandev.buddhaquotes.settings.SettingsViewModel
import org.bandev.buddhaquotes.settings.toIcon
import org.bandev.buddhaquotes.settings.toString

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(viewModel: SettingsViewModel = SettingsViewModel(LocalContext.current)) {
    val useCaseState = rememberUseCaseState(embedded = false)
    val theme = viewModel.getThemeLive()
    Column {
        SettingsMenuLink(
            icon = { Icon(imageVector = theme.toIcon(), contentDescription = null) },
            title = { Text(text = stringResource(R.string.theme)) },
            subtitle = { Text(text = theme.toString()) },
            onClick = useCaseState::show,
        )
    }

    OptionDialog(
        state = useCaseState,
        selection = OptionSelection.Single(
            options = listOf(
                Option(
                    icon = IconSource(Icons.Rounded.LightMode),
                    titleText = stringResource(id = R.string.light)
                ),
                Option(
                    icon = IconSource(Icons.Rounded.DarkMode),
                    titleText = stringResource(id = R.string.dark)
                ),
                Option(
                    icon = IconSource(Icons.Rounded.Brightness6),
                    titleText = stringResource(id = R.string.system)
                )
            ),
            withButtonView = false,
            onSelectOption = { index, _ ->
                viewModel.setTheme(Settings.Theme.values().first { it.ordinal == index + 1})
            }
        )
    )
}