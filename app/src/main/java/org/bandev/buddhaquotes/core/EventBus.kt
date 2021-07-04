package org.bandev.buddhaquotes.core

/** Send **/
sealed class Event {
    object ToListFragment : Event()
    object StartMeditationTimer : Event()
    object RestoreDrawerButton : Event()
}
