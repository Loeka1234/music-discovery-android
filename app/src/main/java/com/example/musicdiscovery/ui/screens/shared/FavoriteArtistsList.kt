package com.example.musicdiscovery.ui.screens.shared

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.musicdiscovery.ui.AppViewModelProvider

@Composable
fun FavoriteArtistsList(
    modifier: Modifier = Modifier,
    onArtistClick: (artistId: Long) -> Unit,
    favoriteArtistsListViewModel: FavoriteArtistsListViewModel =
        viewModel(factory = AppViewModelProvider.Factory)
) {
    val favoriteArtistsListUiState by favoriteArtistsListViewModel.favoriteArtistsListUiState.collectAsState()

    LazyColumn(modifier = modifier) {
        items(favoriteArtistsListUiState.favoriteArtists) { favoriteArtist ->
            ArtistCard(
                artistId = favoriteArtist.id,
                artistPicture = favoriteArtist.picture,
                artistName = favoriteArtist.name,
                onArtistClick = onArtistClick,
                modifier = Modifier.padding(8.dp),
            )
        }
    }

}

