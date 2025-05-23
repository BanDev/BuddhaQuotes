package org.bandev.buddhaquotes

enum class SceneName { HOME, LISTS, FAVOURITES, DAILYQUOTE, MEDITATE, SETTINGS, ABOUT }

sealed class Scene(val id: SceneName, val route: String) {
    data object Home : Scene(SceneName.HOME, "home")
    data object Favourites : Scene(SceneName.FAVOURITES, "favourites")
    data object DailyQuote : Scene(SceneName.DAILYQUOTE, "dailyquote")
    data object Meditate : Scene(SceneName.MEDITATE, "meditate")
    data object Settings : Scene(SceneName.SETTINGS, "settings")
    data object About : Scene(SceneName.ABOUT, "about")
}

val scenes = listOf(
    Scene.Home,
    Scene.Favourites,
    Scene.DailyQuote,
    Scene.Meditate,
    Scene.Settings,
    Scene.About
)