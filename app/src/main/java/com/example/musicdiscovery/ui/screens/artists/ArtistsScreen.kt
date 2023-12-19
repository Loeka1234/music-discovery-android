package com.example.musicdiscovery.ui.screens.artists

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.musicdiscovery.model.Artist
import com.example.musicdiscovery.ui.screens.shared.ArtistCard
import com.example.musicdiscovery.ui.screens.shared.CustomCard
import com.example.musicdiscovery.ui.screens.shared.ErrorScreen
import com.example.musicdiscovery.ui.screens.shared.FavoriteButton
import com.example.musicdiscovery.ui.screens.shared.LoadingScreen
import kotlinx.coroutines.launch

@Composable
fun ArtistsScreen(
    modifier: Modifier = Modifier,
    artistName: String,
    onArtistClick: (artistId: Long) -> Unit
) {
    val coroutineScope = rememberCoroutineScope()

    val artistsViewModel: ArtistsViewModel =
        viewModel(factory = ArtistsViewModel.Factory)

    artistsViewModel.initialGetArtists(artistName)

    ArtistsScreenSwitch(
        artistsUiState = artistsViewModel.artistsUiState,
        retryAction = { artistsViewModel.getArtists(artistName) },
        modifier,
        onArtistClick = onArtistClick,
        favoriteArtist = { artistsViewModel.favoriteOrUnfavoriteArtist(it) }
    )
}

@Composable
fun ArtistsScreenSwitch(
    artistsUiState: ArtistsUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onArtistClick: (artistId: Long) -> Unit,
    favoriteArtist: (artist: Artist) -> Unit
) {
    when (artistsUiState) {
        is ArtistsUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
        is ArtistsUiState.Success -> ViewArtistsScreen(artistsUiState.artistsUiData, modifier = modifier.fillMaxSize(), favoriteArtist = favoriteArtist, onArtistClick = onArtistClick)
        is ArtistsUiState.Error -> ErrorScreen(retryAction, modifier = modifier.fillMaxSize(), errorMessage = "Oops something went wrong while getting artist...")
    }
}

@Composable
fun ViewArtistsScreen(
    artistsUiData: ArtistsUiData,
    modifier: Modifier = Modifier,
    onArtistClick: (artistId: Long) -> Unit,
    favoriteArtist: (artist: Artist) -> Unit
) {
    LazyColumn(modifier = modifier) {
        items(artistsUiData.artists) { artist ->
            ArtistCard(
                artistId = artist.id,
                artistPicture = artist.picture,
                artistName = artist.name,
                artistFans = artist.nbFan,
                isFavorite = artistsUiData.favoriteArtists.any { it.id == artist.id },
                favoriteArtist = { favoriteArtist(artist)  },
                onArtistClick = onArtistClick,
                modifier = Modifier.padding(8.dp),
            )
        }
    }
}