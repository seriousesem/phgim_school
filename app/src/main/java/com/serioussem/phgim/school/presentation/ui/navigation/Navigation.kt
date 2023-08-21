package com.serioussem.phgim.school.presentation.ui.navigation
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.serioussem.phgim.school.presentation.ui.navigation.ScreensRoute.HOME_SCREEN
import com.serioussem.phgim.school.presentation.ui.navigation.ScreensRoute.LOGIN_SCREEN
import com.serioussem.phgim.school.presentation.ui.navigation.ScreensRoute.SPLASH_SCREEN
import com.serioussem.phgim.school.presentation.ui.screens.home.HomeScreen
import com.serioussem.phgim.school.presentation.ui.screens.login.LoginScreen
import com.serioussem.phgim.school.presentation.ui.screens.splash.SplashScreen

@Composable
fun Navigation(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = SPLASH_SCREEN){
        composable(SPLASH_SCREEN){
            SplashScreen(navController = navController)
        }
        composable(HOME_SCREEN){
            HomeScreen(navController = navController)
        }
        composable(LOGIN_SCREEN){
            LoginScreen(navController = navController)
        }
    }
}
object ScreensRoute {
    const val SPLASH_SCREEN = "splash_screen"
    const val HOME_SCREEN = "home_screen"
    const val LOGIN_SCREEN = "login_screen"
}
