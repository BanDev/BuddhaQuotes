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

package org.bandev.buddhaquotes.core

/**
 * Activities is an object dedicated to indexing each activity so other activities know where they
 * have been called from.
 *
 * @author jack.txt
 * @since v1.7.0
 */

object Activities {

    const val MAIN: Int = 0
    const val ABOUT: Int = 1
    const val SETTINGS: Int = 2
    const val OSS_LIBRARIES: Int = 3
    const val ADD_TO_LIST: Int = 4
    const val INTRO: Int = 5
    const val SPLASH: Int = 6

}