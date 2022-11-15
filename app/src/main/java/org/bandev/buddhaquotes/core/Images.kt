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

import org.bandev.buddhaquotes.R

object Images {

    fun heart(liked: Boolean): Int {
        return if (liked) R.drawable.ic_heart_red else R.drawable.ic_heart_outline
    }

    const val ANAHATA: Int = 0
    const val BELL: Int = 1
    const val ELEPHANT: Int = 3
    const val ENDLESS_KNOT: Int = 4
    const val LAMP: Int = 5
    const val LOTUS: Int = 6
    const val MANDALA: Int = 7
    const val MONK: Int = 8
    const val RATTLE: Int = 9
    const val SHRINE: Int = 10
    const val STUPA: Int = 11
    const val TEMPLE: Int = 12
    const val LOTUS_WATER: Int = 13
    const val DHARMA_WHEEL: Int = 14
    const val NO_IMAGE: Int = 15

}