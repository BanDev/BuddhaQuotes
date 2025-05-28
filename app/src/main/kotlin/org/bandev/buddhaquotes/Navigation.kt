package org.bandev.buddhaquotes

import kotlinx.serialization.Serializable

sealed class Scene {
    @Serializable
    data object Home : Scene()

    @Serializable
    data object Quotes : Scene()

    @Serializable
    data object Favourites : Scene()

    @Serializable
    data object Meditate : Scene()

    @Serializable
    data object DailyQuote : Scene()

    @Serializable
    data object Settings : Scene()

    @Serializable
    data object About : Scene()

    @Serializable
    data object Libraries : Scene()
}
