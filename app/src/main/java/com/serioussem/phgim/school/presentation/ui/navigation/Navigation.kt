package com.serioussem.phgim.school.presentation.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.serioussem.phgim.school.presentation.ui.navigation.NavigationArgumentsKEY.DAY_INDEX_KEY
import com.serioussem.phgim.school.presentation.ui.navigation.NavigationArgumentsKEY.LESSON_INDEX_KEY
import com.serioussem.phgim.school.presentation.ui.navigation.ScreensRoute.CLASS_SCHEDULE_SCREEN
import com.serioussem.phgim.school.presentation.ui.navigation.ScreensRoute.LESSON_SCREEN
import com.serioussem.phgim.school.presentation.ui.navigation.ScreensRoute.LOGIN_SCREEN
import com.serioussem.phgim.school.presentation.ui.navigation.ScreensRoute.SPLASH_SCREEN
import com.serioussem.phgim.school.presentation.ui.screens.class_schedule.ClassScheduleScreen
import com.serioussem.phgim.school.presentation.ui.screens.lesson.LessonScreen
import com.serioussem.phgim.school.presentation.ui.screens.login.LoginScreen
import com.serioussem.phgim.school.presentation.ui.screens.splash.SplashScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.ClassSchedule.route) {
        composable(route = Screen.Splash.route) {
            SplashScreen(navController = navController)
        }
        composable(route = Screen.Login.route) {
            LoginScreen(navController = navController)
        }
        composable(route = Screen.ClassSchedule.route) {
            ClassScheduleScreen(navController = navController)
        }
        composable(
            route = Screen.Lesson.route,
            arguments = listOf(
                navArgument(LESSON_INDEX_KEY) {
                    type = NavType.IntType
                },
                navArgument(DAY_INDEX_KEY) {
                    type = NavType.IntType
                }
            )
        ) {
            LessonScreen(navController = navController)
        }
    }
}

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

