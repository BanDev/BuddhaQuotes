/**

Buddha Quotes
Copyright (C) 2021  BanDev

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <https://www.gnu.org/licenses/>.

 */

package org.bandev.buddhaquotes.bus

import android.util.Log
import androidx.lifecycle.LifecycleOwner
import me.kosert.flowbus.EventsReceiver
import me.kosert.flowbus.GlobalBus
import me.kosert.flowbus.subscribe

/**
 * Bus is a wrapper for the [GlobalBus] object provided by
 * FlowBus [https://github.com/Kosert/FlowBus]. Bus manages
 * posting and receiving [Message] data types across multiple
 * activities and fragments.
 *
 * It is not required to have a running instance of Bus in
 * order to send a [Message], this can be done via
 * [GlobalBus.post] but you must ensure that the data
 * being sent is a [Message].
 *
 * @author Jack Devey (jack@bandev.uk)
 * @modified 30/07/2021
 * @since 0.0.1
 *
 * @param instance The instance from where bus is being called,
 *        in an activity this would be 'this'.
 */

class Bus(private val instance: Any, private val name: String) {

    /**
     * Derive listener and lifecycleOwner from the
     * instance provided at class initialisation.
     */

    private val listener: Listener = instance as Listener
    private val lifecycleOwner: LifecycleOwner = instance as LifecycleOwner
    private val receiver: EventsReceiver = EventsReceiver()
    private val tag = "BanDev-BUS"

    /**
     * Bind a new EventsReceiver class.
     */

    init {
        //this.receiver.bindLifecycle(this.lifecycleOwner)
    }

    /**
     * Post a message to all listening activities
     * & fragments. The message must be of [Message]
     * type with the [Message.data] field being any
     * class.
     *
     * @param message The [Message] to be 'broadcast'
     */

    fun broadcast(message: Message<out Any>) {
        Log.i(tag, "$name has broadcast: $message")
        GlobalBus.post(message)
    }

    /**
     * Listen to all posted messages from other
     * activities & fragments. [mute] be called in
     * onStop() in activity.
     */

    fun listen() {
        Log.i(tag, "$name is listening")
        receiver.subscribe { message: Message<out Any> ->
            Log.i(tag, "$name has recieved: $message")
            listener.onMessageReceived(message)
        }
    }

    /**
     * Mute to all posted messages from other
     * activities & fragments. [listen] must be called in
     * onStop() in activity.
     */

    fun mute() {
        Log.i(tag, "$name has muted")
        receiver.unsubscribe()
    }

    /**
     * Provide communication between this Bus class
     * and the calling instance of either a Fragment
     * or an Activity.
     */

    interface Listener {

        /**
         * When a message has been received from
         * another activity or fragment. The message
         * should be of [Message] type with the
         * [Message.data] field being any class.
         *
         * @param message The [Message] that has been
         *        'received'.
         */

        fun onMessageReceived(message: Message<*>)
    }
}