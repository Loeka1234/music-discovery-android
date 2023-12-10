package com.example.musicdiscovery.ui.screens.artists

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.musicdiscovery.model.Artist
import com.example.musicdiscovery.ui.screens.shared.CustomCard
import com.example.musicdiscovery.ui.screens.shared.ErrorScreen
import com.example.musicdiscovery.ui.screens.shared.LoadingScreen

@Composable
fun ArtistsScreen(
    modifier: Modifier = Modifier,
    artistName: String,
    onArtistClick: (artistId: Long) -> Unit
) {
    val artistsViewModel: ArtistsViewModel =
        viewModel(factory = ArtistsViewModel.Factory)

    artistsViewModel.initialGetArtists(artistName)

    ArtistsScreenSwitch(
        artistsUiState = artistsViewModel.artistsUiState,
        retryAction = { artistsViewModel.getArtists(artistName) },
        modifier,
        onArtistClick = onArtistClick
    )
}

@Composable
fun ArtistsScreenSwitch(
    artistsUiState: ArtistsUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onArtistClick: (artistId: Long) -> Unit
) {
    when (artistsUiState) {
        is ArtistsUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
        is ArtistsUiState.Success -> ViewArtistsScreen(artistsUiState.artists, modifier = modifier.fillMaxSize(), onArtistClick)
        is ArtistsUiState.Error -> ErrorScreen(retryAction, modifier = modifier.fillMaxSize(), errorMessage = "Oops something went wrong while getting artist...")
    }
}

@Composable
fun ViewArtistsScreen(
    artists: List<Artist>,
    modifier: Modifier = Modifier,
    onArtistClick: (artistId: Long) -> Unit
) {
    LazyColumn(modifier = modifier) {
        items(artists) { artist ->
            ArtistCard(
                artist = artist,
                modifier = Modifier.padding(8.dp),
                onArtistClick
            )
        }
    }
}

@Composable
fun ArtistCard(
    artist: Artist,
    modifier: Modifier = Modifier,
    onArtistClick: (artistId: Long) -> Unit
) {
    CustomCard(
        picture = artist.picture,
        contentDescription = artist.name,
        modifier = modifier,
        onClick = { onArtistClick(artist.id) }
    ) {
        Column(modifier = modifier.padding(vertical = 8.dp)) {
            Text(
                text = artist.name,
                style = MaterialTheme.typography.headlineSmall
            )
            Text(
                text = "Fans: ${artist.nbFan}",
                style = MaterialTheme.typography.labelLarge
            )
        }
    }
}