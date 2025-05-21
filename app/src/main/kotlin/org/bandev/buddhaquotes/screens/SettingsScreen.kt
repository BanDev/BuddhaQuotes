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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.alorma.compose.settings.ui.SettingsMenuLink
import com.maxkeppeker.sheets.core.models.base.IconSource
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.option.OptionDialog
import com.maxkeppeler.sheets.option.models.Option
import com.maxkeppeler.sheets.option.models.OptionSelection
import org.bandev.buddhaquotes.R
import org.bandev.buddhaquotes.datastore.settings.SettingsViewModel
import org.bandev.buddhaquotes.datastore.settings.toFormattedString
import org.bandev.buddhaquotes.datastore.settings.toIcon
import org.bandev.buddhaquotes.settings.Settings

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(viewModel: SettingsViewModel = hiltViewModel()) {
    val useCaseState = rememberUseCaseState(embedded = false)
    val themeLive: Settings by viewModel.settings.observeAsState(Settings.getDefaultInstance())
    Column {
        SettingsMenuLink(
            icon = { Icon(imageVector = themeLive.theme.toIcon(), contentDescription = null) },
            title = { Text(text = stringResource(R.string.theme)) },
            subtitle = { Text(text = themeLive.theme.toFormattedString()) },
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
                viewModel.setTheme(Settings.Theme.entries.first { it.ordinal == index + 1})
            }
        )
    )
}