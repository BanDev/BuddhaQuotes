package org.bandev.buddhaquotes.core

class GoodTime {

    fun convertToString(minutes: Long, seconds: Long): String {
        return if (minutes > 0) {
            // There is atleast a minute
            if (seconds == 0L) {
                // There is no seconds e.g. exactly 5 mins
                "$minutes minutes"
            } else {
                // There is atleast a second
                "$minutes minutes and $seconds seconds"
            }
        } else {
            // There is less than a minute
            "$seconds seconds"
        }
    }
}