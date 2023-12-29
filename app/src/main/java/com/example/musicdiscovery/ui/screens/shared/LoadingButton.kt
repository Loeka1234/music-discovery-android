package com.example.musicdiscovery.ui.screens.shared

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun LoadingButton(
    text: String, loadingText: String, onClick: () -> Unit, loading: Boolean
) {
    Button(onClick = { if (!loading) onClick() }) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (loading) CircularProgressIndicator(
                modifier = Modifier.size(ButtonDefaults.IconSize),
                color = MaterialTheme.colorScheme.onPrimary,
                strokeWidth = 2.dp
            )
            Text(text = if (loading) loadingText else text)
        }
    }
}

@Preview
@Composable
fun LoadingButtonPreview() {
    LoadingButton(
        text = "Fetch more...", loadingText = "Fetching more...", onClick = {}, loading = false
    )
}

@Preview
@Composable
fun LoadingButtonPreviewLoading() {
    LoadingButton(
        text = "Fetch more...", loadingText = "Fetching more...", onClick = {}, loading = true
    )
}