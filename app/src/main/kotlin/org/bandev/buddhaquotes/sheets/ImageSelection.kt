package org.bandev.buddhaquotes.sheets

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import com.maxkeppeker.sheets.core.models.base.IconSource
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.option.OptionView
import com.maxkeppeler.sheets.option.models.DisplayMode
import com.maxkeppeler.sheets.option.models.Option
import com.maxkeppeler.sheets.option.models.OptionConfig
import com.maxkeppeler.sheets.option.models.OptionSelection
import org.bandev.buddhaquotes.settings.Settings
import org.bandev.buddhaquotes.ui.icons.Anahata
import org.bandev.buddhaquotes.ui.icons.DharmaWheel
import org.bandev.buddhaquotes.ui.icons.Icons
import org.bandev.buddhaquotes.ui.icons.MandalaVariant1
import org.bandev.buddhaquotes.ui.icons.MandalaVariant3
import org.bandev.buddhaquotes.ui.symbols.Symbols
import org.bandev.buddhaquotes.ui.symbols.filled.Block

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageSelection(
    image: Settings.Image? = null,
    onImageSelection: (Int) -> Unit
) {
    OptionView(
        useCaseState = rememberUseCaseState(),
        selection = OptionSelection.Single(
            options = listOf(
                Option(
                    icon = IconSource(imageVector = Icons.Anahata),
                    titleText = "Anahata",
                    selected = listOf(
                        Settings.Image.UNRECOGNIZED,
                        Settings.Image.UNDEFINED,
                        Settings.Image.ANAHATA
                    ).any { image == it },
                ),
                Option(
                    icon = IconSource(imageVector = Icons.DharmaWheel),
                    titleText = "Dharma Wheel",
                    selected = image == Settings.Image.DHARMA_WHEEL,
                ),
                Option(
                    icon = IconSource(imageVector = Icons.MandalaVariant1),
                    titleText = "Mandala",
                    selected = image == Settings.Image.MANDALA_1,
                ),
                Option(
                    icon = IconSource(imageVector = Icons.MandalaVariant3),
                    titleText = "Mandala",
                    selected = image == Settings.Image.MANDALA_3,
                ),
                Option(
                    icon = IconSource(imageVector = Symbols.Filled.Block),
                    titleText = "None",
                    selected = image == Settings.Image.NONE
                )
            ),
            withButtonView = false,
            onSelectOption = { index, _ ->
                onImageSelection(index)
            }
        ),
        config = OptionConfig(
            mode = DisplayMode.GRID_VERTICAL,
        )
    )
}
