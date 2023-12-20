package com.example.musicdiscovery.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.musicdiscovery.ui.screens.StartScreen
import com.example.musicdiscovery.ui.screens.StartScreenDestination
import com.example.musicdiscovery.ui.screens.artist.ArtistDetailsDestination
import com.example.musicdiscovery.ui.screens.artist.ArtistDetailsScreen
import com.example.musicdiscovery.ui.screens.artists.ArtistsDestination
import com.example.musicdiscovery.ui.screens.artists.ArtistsScreen

@Composable
fun MusicDiscoveryNavHost(
    navController: NavHostController,
) {
    NavHost (
        navController = navController,
        startDestination = StartScreenDestination.route,
    ) {
        composable(
            route = ArtistsDestination.routeWithArgs,
            arguments = listOf(navArgument(ArtistsDestination.artistNameArg) { type = NavType.StringType }),
        ) {
            val artistName = it.arguments?.getString(ArtistsDestination.artistNameArg)

            if (artistName == null)
                navController.navigate(StartScreenDestination.route)
            else {
                ArtistsScreen(
                    modifier = Modifier
                        .fillMaxSize(),
                    artistName = artistName,
                    navigateBack = { navController.popBackStack() },
                    navigateToArtistDetails = { navController.navigate("${ArtistDetailsDestination.route}/$it") },
                )
            }
        }

        composable(
            route = ArtistDetailsDestination.routeWithArgs,
            arguments = listOf(navArgument(ArtistDetailsDestination.artistIdArg) { type = NavType.LongType }),
        ) {
            val artistId = it.arguments?.getLong(ArtistDetailsDestination.artistIdArg)

            if (artistId == null)
                navController.navigate(StartScreenDestination.route)
            else {
                ArtistDetailsScreen(
                    modifier = Modifier
                        .fillMaxSize(),
                    artistId = artistId,
                    navigateUp = { navController.navigateUp() }
                )
            }
        }

        composable(route = StartScreenDestination.route) {
            StartScreen(
                modifier = Modifier
                    .fillMaxSize(),
                onNavigateToArtists = { navController.navigate("${ArtistsDestination.route}/$it") },
                onArtistClick = { navController.navigate("${ArtistDetailsDestination.route}/$it") }
            )
        }
    }
}