package com.serioussem.phgim.school.presentation.ui.screens.splash
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.serioussem.phgim.school.R
import com.serioussem.phgim.school.presentation.ui.components.AppBackground
import com.serioussem.phgim.school.presentation.ui.components.HorizontalSpacing
import com.serioussem.phgim.school.presentation.ui.components.SplashProgress
import com.serioussem.phgim.school.presentation.ui.components.VerticalSpacing


@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    viewModel: SplashViewModel = hiltViewModel(),
    navController: NavController,
) {
    BackHandler(onBack = {})

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
            SplashInscription(viewModel.loadingProgressState)
            VerticalSpacing(spacing = 32)
            SplashProgress(viewModel.loadingProgressState)
            VerticalSpacing(spacing = 120)
        }
    }
}

@Composable
private fun SplashInscription(
    loadingProgressState: Float
) {
    Row(modifier = Modifier) {
        Text(
            text = stringResource(id = R.string.splash_inscription),
            color = Color.DarkGray,
            fontSize = 16.sp
        )
        HorizontalSpacing(spacing = 8)
        Text(
            text = "${(loadingProgressState * 100).toInt()}%",
            color = Color.DarkGray,
            fontSize = 16.sp
        )
    }
}