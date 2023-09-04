package com.serioussem.phgim.school.presentation.navigation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.serioussem.phgim.school.presentation.navigation.NavigationArgumentsKEY.CURRENT_WEEK_ID_KEY
import com.serioussem.phgim.school.presentation.navigation.NavigationArgumentsKEY.DAY_INDEX_KEY
import com.serioussem.phgim.school.presentation.navigation.NavigationArgumentsKEY.LESSON_INDEX_KEY
import com.serioussem.phgim.school.presentation.ui.screens.class_schedule.ClassScheduleScreen
import com.serioussem.phgim.school.presentation.ui.screens.lesson.LessonScreen
import com.serioussem.phgim.school.presentation.ui.screens.login.LoginScreen
import com.serioussem.phgim.school.presentation.ui.screens.splash.SplashScreen

@Composable
fun Navigation(showAd: () -> Unit) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.Splash.route) {
        composable(route = Screen.Splash.route) {
            SplashScreen(navController = navController)
        }
        composable(route = Screen.Login.route) {
            LaunchedEffect(Unit){
                showAd()
            }
            LoginScreen(navController = navController)
        }
        composable(route = Screen.ClassSchedule.route) {
            LaunchedEffect(Unit){
                showAd()
            }
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
                },
                navArgument(CURRENT_WEEK_ID_KEY) {
                    type = NavType.StringType
                }
            )
        ) {
            LessonScreen(navController = navController)
        }
    }
}


