package org.bandev.buddhaquotes.items

data class ListIcon(
    val id: Int,
    val drawable: Int,
    val colour: Int,
    var selected: Boolean = false
)