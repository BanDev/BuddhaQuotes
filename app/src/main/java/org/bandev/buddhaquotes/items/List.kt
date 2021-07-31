package org.bandev.buddhaquotes.items

data class List(
    var id: Int,
    val title: String,
    var count: Int,
    val system: Boolean,
    val icon: ListIcon
)