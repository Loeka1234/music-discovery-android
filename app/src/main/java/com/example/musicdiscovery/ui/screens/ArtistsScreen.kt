package com.example.musicdiscovery.ui.screens

import android.widget.ImageView
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.musicdiscovery.model.Artist
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage

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
        retryAction = { artistsViewModel.getArtists(artistName) },
        modifier
    )
}

@Composable
fun ArtistsScreenWithViewModel(
    artistsUiState: ArtistsUiState,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier
) {
    when (artistsUiState) {
        is ArtistsUiState.Loading -> LoadingScreen(modifier = modifier.fillMaxSize())
        is ArtistsUiState.Success -> ViewArtistsScreen(artistsUiState.artists, modifier = modifier.fillMaxSize())
        is ArtistsUiState.Error -> ErrorScreen(retryAction, modifier = modifier.fillMaxSize())
    }
}

@Composable
fun LoadingScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxHeight().fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CircularProgressIndicator(
            modifier = Modifier.width(64.dp),
        )
    }
}

@Composable
fun ViewArtistsScreen(artists: List<Artist>, modifier: Modifier = Modifier) {
    LazyColumn(modifier = modifier) {
        items(artists) { artist ->
            ArtistCard(
                artist = artist,
                modifier = Modifier.padding(8.dp)
            )
        }
    }
}

@Composable
fun ErrorScreen(retryAction: () -> Unit, modifier: Modifier = Modifier) {
    Text(text = "Oops something went wrong while getting artist...")
    Button(onClick = retryAction) {
        Text(text = "Try again")
    }
//    TODO: Styling etc
}

@Composable
fun ArtistCard(artist: Artist, modifier: Modifier = Modifier) {
    Card(modifier = modifier.fillMaxWidth()) {
        Row (verticalAlignment = Alignment.CenterVertically) {
            AsyncImage (
                model = artist.pictureMedium,
                contentDescription = artist.name,
                modifier = Modifier
                    .padding(8.dp)
                    .clip(RoundedCornerShape(0.dp, 8.dp, 0.dp, 8.dp)),
                contentScale = ContentScale.Crop,
            )
            Column(modifier = Modifier.padding(vertical = 16.dp, horizontal = 8.dp)) {
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
}