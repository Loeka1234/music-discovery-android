package com.example.musicdiscovery.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun ArtistsScreen(
    modifier: Modifier = Modifier,
    artistName: String
) {
    val artistsViewModel: ArtistsViewModel =
        viewModel(factory = ArtistsViewModel.Factory)

    artistsViewModel.initialGetArtists(artistName)

    ArtistsScreenWithViewModel(
        artistsUiState = artistsViewModel.artistsUiState,
        retryAction = artistsViewModel::getArtists,
        modifier
    )
}

@Composable
fun ArtistsScreenWithViewModel(
    artistsUiState: ArtistsUiState,
    retryAction: (artistName: String) -> Unit,
    modifier: Modifier = Modifier
) {
    when (artistsUiState) {
        is ArtistsUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
        is ArtistsUiState.Success -> ViewArtistsScreen(modifier = modifier.fillMaxSize())
        is ArtistsUiState.Error -> ErrorScreen(retryAction, modifier = modifier.fillMaxSize())
    }
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    CircularProgressIndicator(
        modifier = modifier.width(32.dp),
    )
}

@Composable
fun ViewArtistsScreen(modifier: Modifier = Modifier) {
    Text(text = "View artists")
}

@Composable
fun ErrorScreen(retryAction: (artistName: String) -> Unit, modifier: Modifier = Modifier) {
    Text(text = "Error")
}