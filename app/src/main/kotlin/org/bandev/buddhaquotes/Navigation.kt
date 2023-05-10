package org.bandev.buddhaquotes

enum class SceneName { HOME, LISTS, INSIDELIST, DAILYQUOTE, MEDITATE, SETTINGS, ABOUT  }

sealed class Scene(val id: SceneName, val route: String) {
    object Home : Scene(SceneName.HOME, "home")
    object Lists : Scene(SceneName.LISTS, "lists")
    object InsideList : Scene(SceneName.INSIDELIST, "insidelist")
    object DailyQuote : Scene(SceneName.DAILYQUOTE, "dailyquote")
    object Meditate: Scene(SceneName.MEDITATE, "meditate")
    object Settings : Scene(SceneName.SETTINGS, "settings")
    object About: Scene(SceneName.ABOUT, "about")
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