package com.example.musicdiscovery.ui.screens.shared.FavoriteArtistsList

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.musicdiscovery.entity.FavoriteArtistEntity
import com.example.musicdiscovery.ui.screens.artists.ArtistsViewModel
import com.example.musicdiscovery.ui.screens.shared.ArtistCard
import com.example.musicdiscovery.ui.screens.shared.CustomCard
import com.example.musicdiscovery.ui.screens.shared.LoadingScreen

@Composable
fun FavoriteArtistsList(
    modifier: Modifier = Modifier,
    onArtistClick: (artistId: Long) -> Unit
) {
    val artistsViewModel: FavoriteArtistsListViewModel =
        viewModel(factory = FavoriteArtistsListViewModel.Factory)

    var favoriteArtistsListUiState = artistsViewModel.favoriteArtistsListUiState

    when (favoriteArtistsListUiState) {
        is FavoriteArtistsListUiState.Success ->
            FavoriteArtistsListFetched(
                modifier = modifier,
                favoriteArtists = favoriteArtistsListUiState.favoriteArtists,
                onArtistClick = onArtistClick)
        is FavoriteArtistsListUiState.Loading ->
            LoadingScreen(modifier)
    }
}

@Composable
fun FavoriteArtistsListFetched(
    modifier: Modifier = Modifier,
    favoriteArtists: List<FavoriteArtistEntity>,
    onArtistClick: (artistId: Long) -> Unit
) {
    LazyColumn(modifier = modifier) {
        items(favoriteArtists) { favoriteArtist ->
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