package org.bandev.buddhaquotes.core

/** Send **/
sealed class SendEvent {
    data class ToListFragment(val boolean: Boolean) : SendEvent()
}

/** Notify **/
sealed class Notify {
    data class NotifyNewList(val listName: String) : Notify()
}
