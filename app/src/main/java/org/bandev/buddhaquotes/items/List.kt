package org.bandev.buddhaquotes.items

data class List(
    val id: Int,
    val title: String,
    val count: Int,
    val system: Boolean,
    val icon: ListIcon
)