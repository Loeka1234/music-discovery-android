package com.example.musicdiscovery.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier

@Composable
fun StartScreen(
    modifier: Modifier = Modifier
) {
    Column (modifier = modifier) {
        Text(text = "Search artist")
        SearchArtistField()
        Button( onClick = { /* TODO */ }) {
            Text(text = "Search")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchArtistField() {
    var artistText by remember { mutableStateOf("") }

    OutlinedTextField(
        value = artistText,
        onValueChange = { artistText = it },
        label = { Text("Artist") },
        modifier = Modifier.fillMaxWidth()
    )
}
