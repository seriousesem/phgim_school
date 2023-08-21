package com.serioussem.phgim.school.presentation.ui.components
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Cyan



@Composable
fun SplashProgress() {
    LinearProgressIndicator(
        modifier = Modifier
            .wrapContentSize(Alignment.Center),
        color = Cyan,
    )
}