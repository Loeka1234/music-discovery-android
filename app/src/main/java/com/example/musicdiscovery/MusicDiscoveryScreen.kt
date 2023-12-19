package com.example.musicdiscovery

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.navArgument
import androidx.navigation.navOptions
import com.example.musicdiscovery.ui.screens.artists.ArtistsScreen
import com.example.musicdiscovery.ui.screens.StartScreen
import com.example.musicdiscovery.ui.screens.artist.ArtistDetailsScreen

/**
 * enum values that represent the screens in the app
 */
enum class MusicDiscoveryScreen(@StringRes val title: Int, @StringRes val route: Int) {
    Start(title = R.string.index_page_title, route = R.string.index_page_route),
    ArtistsPage(title = R.string.artists_page_title, route = R.string.artists_page_route),
    ArtistDetailsPage(title = R.string.artist_details_title, route = R.string.artist_details_route)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MusicDiscoveryAppBar(
    currentScreen: MusicDiscoveryScreen,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(
        title = { Text(stringResource(currentScreen.title)) },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MusicDiscoveryApp(
    navController: NavHostController = rememberNavController()
) {
    // Get current back stack entry
    val backStackEntry by navController.currentBackStackEntryAsState()
    // Get the name of the current screen
    val currentScreen = MusicDiscoveryScreen.values().find {
        stringResource(id = it.route) == backStackEntry?.destination?.route
    } ?: MusicDiscoveryScreen.Start

    Scaffold (
        topBar = {
            MusicDiscoveryAppBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() }
            )
        }
    ) { innerPadding ->
        val homeRoute = stringResource(id = MusicDiscoveryScreen.Start.route)
        val artistsPageRoute = stringResource(id = MusicDiscoveryScreen.ArtistsPage.route)
        val artistDetailsRoute = stringResource(id = MusicDiscoveryScreen.ArtistDetailsPage.route)

        NavHost (
            navController = navController,
            startDestination = homeRoute,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(
                route = artistsPageRoute,
                arguments = listOf(navArgument("artistName") { type = NavType.StringType }),
            ) {
                val artistName = it.arguments?.getString("artistName")

                if (artistName == null)
                    navController.navigate(homeRoute)
                else {
                    ArtistsScreen(
                        modifier = Modifier
                            .fillMaxSize(),
                        artistName,
                        onArtistClick = { artistId -> navController.navigate("Artist?artistId=${artistId}") }
                    )
                }
            }

            composable(
                route = artistDetailsRoute,
                arguments = listOf(navArgument("artistId") { type = NavType.IntType }),
            ) {
                val artistId = it.arguments?.getInt("artistId")

                if (artistId == null)
                    navController.navigate(homeRoute)
                else {
                    ArtistDetailsScreen(
                        modifier = Modifier
                            .fillMaxSize(),
                        artistId
                    )
                }
            }

            composable(route = homeRoute) {
                StartScreen(
                    modifier = Modifier
                        .fillMaxSize(),
                    onNavigateToArtists = { artistName -> navController.navigate("Artists?artistName=${artistName}") },
                    onArtistClick = { artistId -> navController.navigate("Artist?artistId=${artistId}")}
                )
            }
        }
    }
}

