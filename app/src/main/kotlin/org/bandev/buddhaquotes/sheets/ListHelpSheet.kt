package org.bandev.buddhaquotes.sheets

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.rememberLottieComposition
import com.maxkeppeker.sheets.core.models.base.Header
import com.maxkeppeker.sheets.core.models.base.rememberUseCaseState
import com.maxkeppeker.sheets.core.utils.BaseConstants
import com.maxkeppeler.sheets.info.InfoView
import com.maxkeppeler.sheets.info.models.InfoBody
import com.maxkeppeler.sheets.info.models.InfoSelection
import kotlinx.coroutines.launch
import org.bandev.buddhaquotes.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListHelpSheet(
    sheetState: SheetState,
    onClose: () -> Unit
) {
    val listAnimation by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.lists))
    val scope = rememberCoroutineScope()
    ModalBottomSheet(
        onDismissRequest = onClose,
        sheetState = sheetState
    ) {
        InfoView(
            useCaseState = rememberUseCaseState(),
            selection = InfoSelection(
                negativeButton = null,
                positiveButton = BaseConstants.DEFAULT_POSITIVE_BUTTON,
                onPositiveClick = {
                    scope.launch { sheetState.hide() }.invokeOnCompletion {
                        if (!sheetState.isVisible) {
                            onClose()
                        }
                    }
                }
            ),
            header = Header.Custom {
                LottieAnimation(
                    composition = listAnimation,
                    modifier = Modifier.height(150.dp)
                )
                Text(
                    text = "List help",
                    modifier = Modifier.padding(16.dp),
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.SemiBold,
                    style = MaterialTheme.typography.titleLarge,
                )
                Divider()
            },
            body = InfoBody.Default(
                bodyText = """
                    These are your lists. You can access your favourite quotes from here as well as create new lists to categorise quotes into groups.
                    
                    New lists can be created by pressing the add (➕) button at the bottom of the screen and typing in a name.
                """.trimIndent()
            )
        )
    }
}
