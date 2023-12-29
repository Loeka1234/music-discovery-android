package com.example.musicdiscovery.ui.screens.shared

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun ErrorScreen(
    retryAction: () -> Unit,
    errorMessage: String = "Oops something went wrong..."
) {
    Text(text = errorMessage)
    Button(onClick = retryAction) {
        Text(text = "Try again")
    }
//    TODO: Styling etc
}