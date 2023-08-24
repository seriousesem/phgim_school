package com.serioussem.phgim.school.presentation.ui.components
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.graphics.Color.Companion.Transparent


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    modifier: Modifier = Modifier,
    title: String,
    navigationIcon: @Composable () -> Unit
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = title,
            )
        },
        navigationIcon = navigationIcon,
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = Transparent,
            navigationIconContentColor = DarkGray,
            titleContentColor = DarkGray
        )
    )
}