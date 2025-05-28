package org.bandev.buddhaquotes.sheets

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import com.maxkeppeker.sheets.core.models.base.IconSource
import com.maxkeppeker.sheets.core.models.base.UseCaseState
import com.maxkeppeler.sheets.option.OptionDialog
import com.maxkeppeler.sheets.option.models.DisplayMode
import com.maxkeppeler.sheets.option.models.Option
import com.maxkeppeler.sheets.option.models.OptionConfig
import com.maxkeppeler.sheets.option.models.OptionSelection
import org.bandev.buddhaquotes.R
import org.bandev.buddhaquotes.ui.icons.Anahata
import org.bandev.buddhaquotes.ui.icons.DharmaWheel
import org.bandev.buddhaquotes.ui.icons.Icons
import org.bandev.buddhaquotes.ui.icons.Mandala

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageSelectionDialog(
    state: UseCaseState,
    onImageSelection: (Int) -> Unit
) {
    OptionDialog(
        state = state,
        selection = OptionSelection.Single(
            options = listOf(
                Option(
                    icon = IconSource(imageVector = Icons.Anahata),
                    titleText = "Anahata",
                ),
                Option(
                    icon = IconSource(imageVector = Icons.DharmaWheel),
                    titleText = "Dharma Wheel"
                ),
                Option(
                    icon = IconSource(imageVector = Icons.Mandala),
                    titleText = "Mandala"
                ),
                Option(
                    icon = IconSource(drawableRes = R.drawable.image_dharma_wheel),
                    titleText = "Dharma Wheel"
                ),
                Option(
                    icon = IconSource(drawableRes = R.drawable.image_elephant),
                    titleText = "Elephant"
                ),
                Option(
                    icon = IconSource(drawableRes = R.drawable.image_endless_knot),
                    titleText = "Endless Knot"
                ),
                Option(
                    icon = IconSource(drawableRes = R.drawable.image_lamp),
                    titleText = "Lamp"
                ),
                Option(
                    icon = IconSource(drawableRes = R.drawable.image_cancel),
                    titleText = "No image"
                )
            ),
            withButtonView = false,
            onSelectOption = { index, _ ->
                state.hide()
                onImageSelection(index)
            }
        ),
        config = OptionConfig(
            mode = DisplayMode.GRID_VERTICAL,
            gridColumns = 4
        )
    )
}
