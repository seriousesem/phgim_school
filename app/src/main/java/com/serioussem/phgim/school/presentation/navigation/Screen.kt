package com.serioussem.phgim.school.presentation.navigation
import com.serioussem.phgim.school.presentation.navigation.NavigationArgumentsKEY.DAY_INDEX_KEY
import com.serioussem.phgim.school.presentation.navigation.NavigationArgumentsKEY.LESSON_INDEX_KEY
import com.serioussem.phgim.school.presentation.navigation.ScreensRoute.CLASS_SCHEDULE_SCREEN
import com.serioussem.phgim.school.presentation.navigation.ScreensRoute.LESSON_SCREEN
import com.serioussem.phgim.school.presentation.navigation.ScreensRoute.LOGIN_SCREEN
import com.serioussem.phgim.school.presentation.navigation.ScreensRoute.SPLASH_SCREEN

sealed class Screen(val route: String) {
    object Splash : Screen(route = SPLASH_SCREEN)
    object Login : Screen(route = LOGIN_SCREEN)
    object ClassSchedule : Screen(route = CLASS_SCHEDULE_SCREEN)
    object Lesson : Screen(route = LESSON_SCREEN) {
        fun putArguments(dayIndex: Int, lessonIndex: Int) = "lesson_screen/$dayIndex/$lessonIndex"
    }
}

object ScreensRoute {
    const val SPLASH_SCREEN = "splash_screen"
    const val LOGIN_SCREEN = "login_screen"
    const val CLASS_SCHEDULE_SCREEN = "class_schedule_screen"
    const val LESSON_SCREEN = "lesson_screen/{$DAY_INDEX_KEY}/{$LESSON_INDEX_KEY}"
}

object NavigationArgumentsKEY {
    const val DAY_INDEX_KEY = "day_index_key"
    const val LESSON_INDEX_KEY = "lesson_index_key"
}