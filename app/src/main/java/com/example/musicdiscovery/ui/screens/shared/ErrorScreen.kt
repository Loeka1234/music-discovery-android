package com.example.musicdiscovery.ui.screens.shared

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun ErrorScreen(
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    errorMessage: String = "Oops something went wrong..."
) {
    Text(text = errorMessage)
    Button(onClick = retryAction) {
        Text(text = "Try again")
    }
//    TODO: Styling etc
}