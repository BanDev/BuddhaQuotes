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

/**
 * Message is the structure of a typical message to be
 * sent via the [Bus]. A message has a [type] to help
 * the receiving activity or fragment decide what to do
 * with it, and some [data] of any type <[T]>. Sort of
 * like Opcodes and Operands.
 *
 * @author Jack Devey (jack@bandev.uk)
 * @modified 30/07/2021
 * @since 0.0.1
 *
 * @param type The type of message
 * @param data The actual data for the message
 */

data class Message<T>(
    val type: Int,
    val data: T
)
