package com.example.musicdiscovery.ui.screens.artists

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.musicdiscovery.MusicDiscoveryAppBar
import com.example.musicdiscovery.R
import com.example.musicdiscovery.model.Artist
import com.example.musicdiscovery.navigation.NavigationDestination
import com.example.musicdiscovery.ui.AppViewModelProvider
import com.example.musicdiscovery.ui.screens.shared.ArtistCard
import com.example.musicdiscovery.ui.screens.shared.ErrorScreen
import com.example.musicdiscovery.ui.screens.shared.LoadingButton
import com.example.musicdiscovery.ui.screens.shared.LoadingScreen

object ArtistsDestination : NavigationDestination {
    override val route = "Artists"
    override val titleRes = R.string.artists_page_title
    const val artistNameArg = "artistName"
    val routeWithArgs = "$route/{$artistNameArg}"
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ArtistsScreen(
    navigateBack: () -> Unit,
    modifier: Modifier = Modifier,
    artistName: String,
    navigateToArtistDetails: (artistId: Long) -> Unit,
    artistsViewModel: ArtistsViewModel =
        viewModel(factory = AppViewModelProvider.Factory)
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            MusicDiscoveryAppBar(
                title = stringResource(ArtistsDestination.titleRes),
                navigateBack = navigateBack,
                scrollBehavior = scrollBehavior
            )
        },
    ) { innerPadding ->
        ArtistsScreenBody(
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize(),
            artistName = artistName,
            navigateToArtistDetails = navigateToArtistDetails,
            artistsViewModel = artistsViewModel
        )
    }
}

@Composable
fun ArtistsScreenBody(
    modifier: Modifier = Modifier,
    artistsViewModel: ArtistsViewModel,
    artistName: String,
    navigateToArtistDetails: (artistId: Long) -> Unit,
) {
    ArtistsScreenSwitch(
        artistsUiState = artistsViewModel.artistsUiState,
        retryAction = { artistsViewModel.getArtists(artistName) },
        modifier,
        onArtistClick = navigateToArtistDetails,
        favoriteArtist = { artistsViewModel.favoriteOrUnfavoriteArtist(it) },
        fetchMoreArtists = artistsViewModel::fetchMoreArtists
    )
}

@Composable
fun ArtistsScreenSwitch(
    artistsUiState: ArtistsUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier,
    onArtistClick: (artistId: Long) -> Unit,
    favoriteArtist: (artist: Artist) -> Unit,
    fetchMoreArtists: () -> Unit,
) {
    when (artistsUiState) {
        is ArtistsUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
        is ArtistsUiState.Success -> ViewArtistsScreen(
            artistsUiState.artistsUiData,
            modifier = modifier.fillMaxSize(),
            favoriteArtist = favoriteArtist,
            onArtistClick = onArtistClick,
            fetchMoreArtists = fetchMoreArtists,
            isFetchingMore = artistsUiState.isFetchingMore,
            hasMore = artistsUiState.hasMore
        )

        is ArtistsUiState.Error -> ErrorScreen(
            retryAction,
            errorMessage = "Oops something went wrong while getting artist..."
        )
    }
}

@Composable
fun ViewArtistsScreen(
    artistsUiData: ArtistsUiData,
    modifier: Modifier = Modifier,
    onArtistClick: (artistId: Long) -> Unit,
    favoriteArtist: (artist: Artist) -> Unit,
    fetchMoreArtists: () -> Unit,
    isFetchingMore: Boolean,
    hasMore: Boolean
) {
    LazyColumn(modifier = modifier) {
        items(artistsUiData.artists) { artist ->
            ArtistCard(
                artistId = artist.id,
                artistPicture = artist.picture,
                artistName = artist.name,
                artistFans = artist.nbFan,
                isFavorite = artistsUiData.favoriteArtists.any { it.id == artist.id },
                favoriteArtist = { favoriteArtist(artist) },
                onArtistClick = onArtistClick,
                modifier = Modifier
                    .padding(8.dp)
                    .testTag(stringResource(id = R.string.test_tag_artist)),
            )
        }
        item {
            if (hasMore)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    LoadingButton(
                        text = "Fetch more...",
                        loadingText = "Fetching more...",
                        onClick = fetchMoreArtists,
                        loading = isFetchingMore
                    )
                }
        }
    }
}