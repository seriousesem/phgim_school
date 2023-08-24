package com.serioussem.phgim.school.presentation.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun MenuIconButton() {
    IconButton(
        onClick = { /*TODO*/ },
    )
    {
        Icon(
            imageVector = Icons.Outlined.Menu,
            contentDescription = null,
            modifier = Modifier.size(28.dp)
        )
    }
}