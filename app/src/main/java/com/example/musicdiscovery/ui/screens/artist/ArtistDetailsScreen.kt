package com.example.musicdiscovery.ui.screens.artist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.musicdiscovery.model.Artist

@Composable
fun ArtistDetailsScreen(
    modifier: Modifier = Modifier,
    artistId: Int
) {
    val artistDetailsViewModel: ArtistDetailsViewModel =
        viewModel(factory = ArtistDetailsViewModel.Factory)

    artistDetailsViewModel.initialGetArtistDetails(artistId)

    ArtistDetailsScreenSwitch(
        artistDetailsUiState = artistDetailsViewModel.artistDetailsUiState,
        retryAction = { artistDetailsViewModel.getArtistDetails(artistId) },
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
        is ArtistDetailsUiState.Success -> ViewTracksScreen(artistDetailsUiState.artistDetails, modifier = modifier.fillMaxSize())
        is ArtistDetailsUiState.Error -> ErrorScreen(retryAction, modifier = modifier.fillMaxSize(), errorMessage = "Oops something went wrong while getting tracks...")
    }
}

@Composable
fun ViewTracksScreen(
    artistDetails: ArtistDetailsData,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.Center) {
        ArtistDetails(artist = artistDetails.artist)
        Tracks(tracks = artistDetails.tracks)
    }
}

@Composable
fun ArtistDetails(artist: Artist, modifier: Modifier = Modifier) {
    Column(modifier = modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
        AsyncImage(
            model = artist.picture,
            contentDescription = artist.name,
            modifier = Modifier
                .height(100.dp)
                .width(100.dp)
                .padding(top = 16.dp, bottom = 4.dp)
        )
        Text(
            text = artist.name,
            style = MaterialTheme.typography.headlineLarge
        )
    }
}

@Composable
fun Tracks(
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