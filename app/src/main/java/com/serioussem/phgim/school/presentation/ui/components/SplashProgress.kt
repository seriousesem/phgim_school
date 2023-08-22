package com.serioussem.phgim.school.presentation.ui.components
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Cyan
import androidx.hilt.navigation.compose.hiltViewModel
import com.serioussem.phgim.school.presentation.ui.screens.splash.SplashViewModel
import com.serioussem.phgim.school.presentation.ui.theme.Green600


@Composable
fun SplashProgress(
    viewModel: SplashViewModel = hiltViewModel()
) {
    val loadingProgress = remember {
        mutableStateOf(viewModel.loadingProgress).value
    }
    
    LinearProgressIndicator(
        progress = loadingProgress.floatValue,
        modifier = Modifier
            .wrapContentSize(Alignment.Center),
//        color = Green600,
    )
}