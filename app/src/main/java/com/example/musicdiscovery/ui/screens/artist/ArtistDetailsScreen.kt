package com.example.musicdiscovery.ui.screens.artist

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.musicdiscovery.model.Track
import com.example.musicdiscovery.ui.screens.shared.CustomCard
import com.example.musicdiscovery.ui.screens.shared.ErrorScreen
import com.example.musicdiscovery.ui.screens.shared.LoadingScreen
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.unit.dp

@Composable
fun ArtistDetailsScreen(
    modifier: Modifier = Modifier,
    artistId: Int
) {
    val artistDetailsViewModel: ArtistDetailsViewModel =
        viewModel(factory = ArtistDetailsViewModel.Factory)

    artistDetailsViewModel.initialGetTracks(artistId)

    ArtistDetailsScreenSwitch(
        artistDetailsUiState = artistDetailsViewModel.artistDetailsUiState,
        retryAction = { artistDetailsViewModel.getTracks(artistId) },
        modifier
    )
}

@Composable
fun ArtistDetailsScreenSwitch(
    artistDetailsUiState: ArtistDetailsUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier
) {
    when (artistDetailsUiState) {
        is ArtistDetailsUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
        is ArtistDetailsUiState.Success -> ViewTracksScreen(artistDetailsUiState.tracks, modifier = modifier.fillMaxSize())
        is ArtistDetailsUiState.Error -> ErrorScreen(retryAction, modifier = modifier.fillMaxSize(), errorMessage = "Oops something went wrong while getting tracks...")
    }
}

@Composable
fun ViewTracksScreen(
    tracks: List<Track>,
    modifier: Modifier = Modifier
) {
    LazyColumn(modifier = modifier) {
        items(tracks) { track ->
            CustomCard(
                picture = track.album.cover,
                contentDescription = track.title,
                modifier = Modifier.padding(8.dp)
            ) {
                Text(
                    text = track.title,
                    style = MaterialTheme.typography.headlineSmall
                )
            }
        }
    }
}