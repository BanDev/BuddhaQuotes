package org.bandev.buddhaquotes.fragments

sealed class MessageEvent {
    data class MessageA(val msg: Boolean) : MessageEvent()

    private var message: String? = null

    open fun messagedEvent(message: String?) {
        this.message = message
    }
}
