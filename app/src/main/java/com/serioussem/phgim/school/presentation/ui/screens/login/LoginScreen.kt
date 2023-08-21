package com.serioussem.phgim.school.presentation.ui.screens.login
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import com.serioussem.phgim.school.presentation.ui.components.AppScaffold

@Composable
fun LoginScreen(
    navController: NavController
){
    AppScaffold {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(text = "Login screen")
        }
    }
}