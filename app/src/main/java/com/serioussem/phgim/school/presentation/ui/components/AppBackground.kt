package com.serioussem.phgim.school.presentation.ui.components
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.serioussem.phgim.school.R

@Composable
fun AppBackground(
    modifier: Modifier
) {
    Image(
        painter = painterResource(id = R.drawable.app_background),
        contentDescription = null,
        contentScale = ContentScale.FillBounds,
        modifier = modifier,
    )
}