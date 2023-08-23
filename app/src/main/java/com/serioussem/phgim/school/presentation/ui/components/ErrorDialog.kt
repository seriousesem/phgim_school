package com.serioussem.phgim.school.presentation.ui.components
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Red
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.serioussem.phgim.school.R


@Composable
fun ErrorDialog(errorMessage: String, action: () -> Unit) {
    AlertDialog(
        modifier = Modifier.padding(horizontal = 16.dp),
        onDismissRequest = {},
        confirmButton = {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedButton(onClick = action)
                {
                    Text(text = stringResource(id = R.string.close))
                }
            }
        },
        title = {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = stringResource(id = R.string.error_dialog_title),
                    fontSize = 20.sp,
                    color = Red,
                )
            } },
        text = {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp)
            ) {
                Text(
                    text = errorMessage,
                    modifier = Modifier.verticalScroll(rememberScrollState()),
                    maxLines = 3,
                )
            } },
    )
}