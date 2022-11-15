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

import android.content.Context
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LifecycleOwner
import me.kosert.flowbus.EventsReceiver
import me.kosert.flowbus.GlobalBus
import me.kosert.flowbus.bindLifecycle
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

class Bus(
    private val instance: Any,
    private val name: String,
) {

    /**
     * Derive listener from the instance provided
     * at class initialisation.
     */

    private val lifeCycleOwner = instance as LifecycleOwner
    private val receiver: EventsReceiver = EventsReceiver()
    private lateinit var router: (type: MessageType) -> ((message: Message<*>) -> Any)?
    private lateinit var prevMessage: Message<*>
    private val tag = "BanDev-BUS"


    /**
     * Broadcast a message to all listening activities
     * & fragments. The message must be of [Message]
     * type with the [Message.data] field being any
     * class.
     *
     * @param message The [Message] to be 'broadcast'
     */

    fun broadcast(message: Message<out Any>) {
        if (isDuplicate(message)) {
            output("$name tried to send: $message, it was a duplicate")
            return
        }
        output("$name has broadcast: $message")
        GlobalBus.post(message)
        prevMessage = message
    }

    /**
     * Provides the ability to use custom router function
     * in order to route messages without needing to
     * use the [Listener] interface.
     *
     * @param router The function you want to use
     */

    fun attachRouter(router: (type: MessageType) -> ((message: Message<*>) -> Any)?) {
        output("$name has attatched a router: $router")
        this.router = router
    }

    /**
     * Listen to all posted messages from other
     * activities & fragments. If a custom router has
     * been provided to this class, the message
     * will be sent there, else the message will
     * be sent through the listener.
     *
     * [listen] must be called in onStart(), and [mute] must
     * be called in onStop()
     */

    fun listen() {
        Log.i(tag, "$name is listening")
        receiver.bindLifecycle(lifeCycleOwner)
        if (this::router.isInitialized) {
            receiver.subscribe { message: Message<out Any> ->
                output("$name has received: $message via custom router")
                instance to router(message.type)?.let { it(message) }
            }
        } else {
            val listener = instance as Listener
            receiver.subscribe { message: Message<out Any> ->
                output("$name has received: $message via listener interface")
                listener.onMessageReceived(message)
            }
        }
    }

    /**
     * Mute all broadcast messages from other
     * activities & fragments.
     *
     * [listen] must be called in onStart(), and [mute] must
     * be called in onStop()
     */

    fun mute() {
        output("$name has muted")
        receiver.unsubscribe()
    }

    /**
     * Determine if a message is a duplicate and
     * should be ignored.
     *
     * @param message The [Message] that needs to
     *        be checked.
     */

    private fun isDuplicate(message: Message<*>): Boolean {
        if(message.type == MessageType.LIKE_UPDATE) return false
        return this::prevMessage.isInitialized
            && message.type == prevMessage.type
            && message.data == prevMessage.data
    }

    /**
     * Decide where to output some info based on
     * if this instance was setup in debug mode.
     *
     * @param string The [String] to output
     */

    private fun output(string: String) {
        Log.i(tag, string)
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