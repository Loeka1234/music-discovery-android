package com.example.musicdiscovery.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.musicdiscovery.MusicDiscoveryAppBar
import com.example.musicdiscovery.R
import com.example.musicdiscovery.navigation.NavigationDestination
import com.example.musicdiscovery.ui.screens.shared.FavoriteArtistsList

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

@Composable
fun StartScreenBody(
    modifier: Modifier = Modifier,
    onNavigateToArtists: (artistName: String) -> Unit,
    onArtistClick: (artistId: Long) -> Unit
) {

    Column(modifier = modifier) {
        SearchArtistBox(onNavigateToArtists = onNavigateToArtists)
        FavoriteArtistsListWrapper(onArtistClick = onArtistClick)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchArtistBox(
    onNavigateToArtists: (artistName: String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.padding(4.dp)) {
        var artistName by remember { mutableStateOf("") }

        Text(
            text = "Search artist",
            style = MaterialTheme.typography.headlineMedium
        )
        OutlinedTextField(
            value = artistName,
            onValueChange = { artistName = it },
            label = { Text("Artist") },
            modifier = Modifier
                .fillMaxWidth()
                .testTag(stringResource(id = R.string.test_tag_search_artists))
        )
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End) {
            Button(onClick = {
                onNavigateToArtists(artistName)
            }) {
                Text(text = stringResource(id = R.string.search_text))
            }
        }
    }
}

@Composable
fun FavoriteArtistsListWrapper(
    onArtistClick: (artistId: Long) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.padding(4.dp)) {
        Text(
            text = "Favorite artists:",
            style = MaterialTheme.typography.headlineMedium
        )
        FavoriteArtistsList(onArtistClick = onArtistClick)
    }
}

