package org.bandev.buddhaquotes.architecture

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.EmojiEvents
import androidx.compose.material.icons.rounded.EmojiSymbols
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material.icons.rounded.FormatQuote
import androidx.compose.material.icons.rounded.History
import androidx.compose.material.icons.rounded.HistoryEdu
import androidx.compose.material.icons.rounded.Interests
import androidx.compose.material.icons.rounded.Subject
import androidx.compose.material.icons.rounded.ThumbUp
import org.bandev.buddhaquotes.items.ListIcon
import org.bandev.buddhaquotes.ui.theme.bandev
import org.bandev.buddhaquotes.ui.theme.blueAccent
import org.bandev.buddhaquotes.ui.theme.favourite
import org.bandev.buddhaquotes.ui.theme.greenAccent
import org.bandev.buddhaquotes.ui.theme.lightBlueAccent
import org.bandev.buddhaquotes.ui.theme.orangeAccent
import org.bandev.buddhaquotes.ui.theme.tealAccent
import org.bandev.buddhaquotes.ui.theme.violetAccent
import org.bandev.buddhaquotes.ui.theme.yellowAccent

object ListMapper {
    val listIcons: List<ListIcon> = listOf(
        ListIcon(0, Icons.Rounded.FavoriteBorder, favourite),
        ListIcon(1, Icons.Rounded.Subject, blueAccent),
        ListIcon(2, Icons.Rounded.HistoryEdu, greenAccent),
        ListIcon(3, Icons.Rounded.History, yellowAccent),
        ListIcon(4, Icons.Rounded.Interests, tealAccent),
        ListIcon(5, Icons.Rounded.FormatQuote, violetAccent),
        ListIcon(6, Icons.Rounded.EmojiSymbols, orangeAccent),
        ListIcon(7, Icons.Rounded.ThumbUp, lightBlueAccent),
        ListIcon(8, Icons.Rounded.EmojiEvents, bandev)
    )
}
