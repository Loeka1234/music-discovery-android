package com.example.musicdiscovery.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StartScreen(
    modifier: Modifier = Modifier,
    onNavigateToArtists: (artistName: String) -> Unit
) {
    val navController = rememberNavController()
    var artistName by remember { mutableStateOf("") }

    Column (modifier = modifier) {
        Text(text = "Search artist")
        OutlinedTextField(
            value = artistName,
            onValueChange = { artistName = it },
            label = { Text("Artist") },
            modifier = Modifier.fillMaxWidth()
        )
        Button( onClick = {
            onNavigateToArtists(artistName)
        }) {
            Text(text = "Search")
        }
    }
}

