package org.bandev.buddhaquotes.core

/** Send **/
sealed class SendEvent {
    data class ToListFragment(val boolean: Boolean) : SendEvent()
}
