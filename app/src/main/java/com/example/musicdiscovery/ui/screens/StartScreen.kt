package com.example.musicdiscovery.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import com.example.musicdiscovery.MusicDiscoveryAppBar
import com.example.musicdiscovery.R
import com.example.musicdiscovery.navigation.NavigationDestination
import com.example.musicdiscovery.ui.screens.shared.favoriteArtistsList.FavoriteArtistsList

object StartScreenDestination : NavigationDestination {
    override val route = "Start"
    override val titleRes = R.string.index_page_title
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StartScreen(
    modifier: Modifier = Modifier,
    onNavigateToArtists: (artistName: String) -> Unit,
    onArtistClick: (artistId: Long) -> Unit,
) {
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior()

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            MusicDiscoveryAppBar(
                title = stringResource(StartScreenDestination.titleRes),
                scrollBehavior = scrollBehavior
            )
        },
    ) { innerPadding ->
        StartScreenBody(
            modifier = modifier
                .padding(innerPadding)
                .fillMaxSize(),
            onNavigateToArtists = onNavigateToArtists,
            onArtistClick = onArtistClick
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StartScreenBody(
    modifier: Modifier = Modifier,
    onNavigateToArtists: (artistName: String) -> Unit,
    onArtistClick: (artistId: Long) -> Unit
) {
    var artistName by remember { mutableStateOf("") }

    Column (modifier = modifier) {
        Text(
            text = "Search artist",
            style = MaterialTheme.typography.headlineMedium
        )
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

        Text(
            text = "Favorite artists:",
            style = MaterialTheme.typography.headlineMedium
        )
        FavoriteArtistsList(onArtistClick = onArtistClick)
    }
}

