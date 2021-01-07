package org.bandev.buddhaquotes.core

class GoodTime {

    fun convertToString(minutes: Long, seconds: Long): String {
        return if (minutes > 0) {
            // There is at least a minute
            if (seconds == 0L) {
                // There are no seconds e.g. exactly 5 mins
                "$minutes minutes"
            } else {
                // There is at least a second
                "$minutes minutes and $seconds seconds"
            }
        } else {
            // There is less than a minute
            "$seconds seconds"
        }
    }
}