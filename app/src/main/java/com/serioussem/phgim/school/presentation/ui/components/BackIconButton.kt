package com.serioussem.phgim.school.presentation.ui.components
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun BackIconButton() {
    IconButton(
        onClick = { /*TODO*/ },
    )
    {
        Icon(
            imageVector = Icons.Outlined.ArrowBack,
            contentDescription = null,
            modifier = Modifier.size(28.dp)
        )
    }
}