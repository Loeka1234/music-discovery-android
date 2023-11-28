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
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import com.example.musicdiscovery.ui.screens.StartScreen

/**
 * enum values that represent the screens in the app
 */
enum class MusicDiscoveryScreen(@StringRes val title: Int) {
    Start(title = R.string.index_page_title),
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
    val currentScreen = MusicDiscoveryScreen.valueOf(
        backStackEntry?.destination?.route ?: MusicDiscoveryScreen.Start.name
    )

    Scaffold (
        topBar = {
            MusicDiscoveryAppBar(
                currentScreen = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() }
            )
        }
    ) { innerPadding ->
        NavHost (
            navController = navController,
            startDestination = MusicDiscoveryScreen.Start.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = MusicDiscoveryScreen.Start.name) {
                StartScreen(
                    modifier = Modifier
                        .fillMaxSize()
                )
            }
        }
    }
}
