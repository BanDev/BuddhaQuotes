package org.bandev.buddhaquotes.items

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import org.bandev.buddhaquotes.R
import org.bandev.buddhaquotes.model.Quote
import java.text.DateFormat
import java.util.Date

@Composable
fun QuoteCard(quote: Quote, date: Date? = null, onSourceClick: () -> Unit) {
    ElevatedCard(Modifier.fillMaxWidth()) {
        ElevatedCard {
            Box {
                Column(
                    Modifier
                        .padding(20.dp)
                        .fillMaxWidth()
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.ic_left_quote),
                        contentDescription = null
                    )
                    AnimatedContent(
                        targetState = quote.text,
                        label = "Animated quote transition"
                    ) { text ->
                        Text(
                            text = text,
                            modifier = Modifier.padding(vertical = 10.dp),
                            style = MaterialTheme.typography.bodyLarge
                        )
                    }
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = stringResource(R.string.attribution_buddha),
                            modifier = Modifier.weight(1f),
                            style = MaterialTheme.typography.labelLarge
                        )
                        Image(
                            painter = painterResource(id = R.drawable.ic_right_quote),
                            contentDescription = null,
                        )
                    }
                }
                Row(
                    Modifier
                        .align(Alignment.TopEnd)
                        .padding(10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    date?.let {
                        Box(
                            modifier = Modifier
                                .defaultMinSize(
                                    minWidth = ButtonDefaults.MinWidth,
                                    minHeight = ButtonDefaults.MinHeight
                                )
                                .padding(horizontal = 5.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = DateFormat.getDateInstance(DateFormat.FULL).format(it),
                                style = MaterialTheme.typography.labelMedium,
                            )
                        }
                    }
                    TextButton(onClick = onSourceClick) {
                        Text(text = "Source")
                    }
                }
            }
        }
    }
}
