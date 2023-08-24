package com.serioussem.phgim.school.presentation.ui.components

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun VerticalDivider(){
    Divider(
        modifier = Modifier.fillMaxHeight().width(1.dp)
    )
}