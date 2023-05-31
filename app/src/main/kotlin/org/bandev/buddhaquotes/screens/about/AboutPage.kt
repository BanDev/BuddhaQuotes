package org.bandev.buddhaquotes.screens.about

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import org.bandev.buddhaquotes.BuildConfig
import org.bandev.buddhaquotes.R

@Composable
fun AboutPage() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
            Image(
                painter = painterResource(id = R.drawable.ic_buddha),
                contentDescription = null,
                modifier = Modifier.size(60.dp)
            )
            Column(verticalArrangement = Arrangement.Center) {
                Text(
                    text = stringResource(id = R.string.app_name),
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    text = BuildConfig.VERSION_NAME,
                    style = MaterialTheme.typography.labelLarge
                )
            }
        }
        Divider(Modifier.fillMaxWidth())
        Text(text = stringResource(id = R.string.app_description))
        Divider(Modifier.fillMaxWidth())
        Text(
            text = stringResource(id = R.string.acknowledgements),
            fontWeight = FontWeight.SemiBold,
            style = MaterialTheme.typography.headlineSmall
        )
        Text(text = "Launcher icon - Freepik via Flaticon")
        Text(text = "RealBuddhaQuotes.com")
    }
}
