package org.bandev.buddhaquotes.custom

sealed class SendEvent {
    data class ToListFragment(val boolean: Boolean) : SendEvent()
}

sealed class Notify {
    data class NotifyNewList(val listName: String) : Notify()
}
