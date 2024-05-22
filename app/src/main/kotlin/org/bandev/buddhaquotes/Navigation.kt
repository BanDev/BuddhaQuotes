package org.bandev.buddhaquotes

enum class SceneName { HOME, LISTS, INSIDELIST, DAILYQUOTE, MEDITATE, SETTINGS, ABOUT  }

sealed class Scene(val id: SceneName, val route: String) {
    data object Home : Scene(SceneName.HOME, "home")
    data object Lists : Scene(SceneName.LISTS, "lists")
    data object InsideList : Scene(SceneName.INSIDELIST, "insidelist")
    data object DailyQuote : Scene(SceneName.DAILYQUOTE, "dailyquote")
    data object Meditate: Scene(SceneName.MEDITATE, "meditate")
    data object Settings : Scene(SceneName.SETTINGS, "settings")
    data object About: Scene(SceneName.ABOUT, "about")
}

val scenes = listOf(
    Scene.Home,
    Scene.Lists,
    Scene.InsideList,
    Scene.DailyQuote,
    Scene.Meditate,
    Scene.Settings,
    Scene.About
)