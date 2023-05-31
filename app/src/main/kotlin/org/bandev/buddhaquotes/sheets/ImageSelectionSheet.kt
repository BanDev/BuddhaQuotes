package org.bandev.buddhaquotes.sheets

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.rememberCoroutineScope
import com.maxkeppeker.sheets.core.models.base.IconSource
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeler.sheets.option.OptionView
import com.maxkeppeler.sheets.option.models.DisplayMode
import com.maxkeppeler.sheets.option.models.Option
import com.maxkeppeler.sheets.option.models.OptionConfig
import com.maxkeppeler.sheets.option.models.OptionSelection
import kotlinx.coroutines.launch
import org.bandev.buddhaquotes.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ImageSelectionSheet(
    sheetState: SheetState,
    onClose: () -> Unit,
    centerImage: MutableState<Int>
) {
    val scope = rememberCoroutineScope()
    ModalBottomSheet(
        onDismissRequest = onClose,
        sheetState = sheetState
    ) {
        OptionView(
            useCaseState = rememberUseCaseState(),
            selection = OptionSelection.Single(
                options = listOf(
                    Option(
                        icon = IconSource(drawableRes = R.drawable.image_anahata),
                        titleText = "Anahata",
                    ),
                    Option(
                        icon = IconSource(drawableRes = R.drawable.image_bell),
                        titleText = "Bell"
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
                        icon = IconSource(drawableRes = R.drawable.image_lotus),
                        titleText = "Lotus"
                    ),
                    Option(
                        icon = IconSource(drawableRes = R.drawable.image_lotus_water),
                        titleText = "Water Lotus"
                    ),
                    Option(
                        icon = IconSource(drawableRes = R.drawable.image_mandala),
                        titleText = "Mandala"
                    ),
                    Option(
                        icon = IconSource(drawableRes = R.drawable.image_monk),
                        titleText = "Monk"
                    ),
                    Option(
                        icon = IconSource(drawableRes = R.drawable.image_rattle),
                        titleText = "Rattle"
                    ),
                    Option(
                        icon = IconSource(drawableRes = R.drawable.image_shrine),
                        titleText = "Shrine"
                    ),
                    Option(
                        icon = IconSource(drawableRes = R.drawable.image_stupa),
                        titleText = "Stupa"
                    ),
                    Option(
                        icon = IconSource(drawableRes = R.drawable.image_temple),
                        titleText = "Temple"
                    )
                ),
                withButtonView = false,
                onSelectOption = { _, option ->
                    scope.launch { sheetState.hide() }.invokeOnCompletion {
                        if (!sheetState.isVisible) {
                            onClose()
                        }
                    }
                    option.icon?.drawableRes?.let { centerImage.value = it }
                }
            ),
            config = OptionConfig(mode = DisplayMode.GRID_VERTICAL)
        )
    }
}
