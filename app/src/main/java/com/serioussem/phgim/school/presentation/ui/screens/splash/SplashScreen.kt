package com.serioussem.phgim.school.presentation.ui.screens.splash

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.serioussem.phgim.school.R
import com.serioussem.phgim.school.presentation.ui.components.AppBackground
import com.serioussem.phgim.school.presentation.ui.components.SplashProgress
import com.serioussem.phgim.school.presentation.ui.components.VerticalSpacing

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    viewModel: SplashViewModel = hiltViewModel(),
    navController: NavController
) {
    LaunchedEffect(key1 = false) {
        viewModel.navigateToNextScreen(navController)
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
    ) {
        AppBackground(modifier = Modifier.matchParentSize())
        Column(
            modifier = modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SplashLogo()
            VerticalSpacing(spacing = 32)
            SplashProgress(viewModel.loadingProgressState)
            VerticalSpacing(spacing = 120)
        }
    }
}

@Composable
private fun SplashLogo() {
    Image(
        painter = painterResource(id = R.drawable.splash_logo),
        contentDescription = null,
        modifier = Modifier.size(120.dp),
    )
}