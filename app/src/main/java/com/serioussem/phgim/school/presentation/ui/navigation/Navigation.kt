package com.serioussem.phgim.school.presentation.ui.navigation
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.serioussem.phgim.school.presentation.ui.navigation.ScreensRoute.CLASS_SCHEDULE_SCREEN
import com.serioussem.phgim.school.presentation.ui.navigation.ScreensRoute.LOGIN_SCREEN
import com.serioussem.phgim.school.presentation.ui.navigation.ScreensRoute.SPLASH_SCREEN
import com.serioussem.phgim.school.presentation.ui.screens.class_schedule.ClassScheduleScreen
import com.serioussem.phgim.school.presentation.ui.screens.login.LoginScreen
import com.serioussem.phgim.school.presentation.ui.screens.splash.SplashScreen

@Composable
fun Navigation(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = CLASS_SCHEDULE_SCREEN){
        composable(SPLASH_SCREEN){
            SplashScreen(navController = navController)
        }
        composable(LOGIN_SCREEN){
            LoginScreen(navController = navController)
        }
        composable(CLASS_SCHEDULE_SCREEN){
            ClassScheduleScreen(navController = navController)
        }
    }
}
object ScreensRoute {
    const val SPLASH_SCREEN = "splash_screen"
    const val CLASS_SCHEDULE_SCREEN = "class_schedule_screen"
    const val LOGIN_SCREEN = "login_screen"
}
