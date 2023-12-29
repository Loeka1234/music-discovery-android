package com.example.musicdiscovery

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.musicdiscovery.navigation.MusicDiscoveryNavHost

@Composable
fun MusicDiscoveryApp(
    navController: NavHostController = rememberNavController()
) {
    MusicDiscoveryNavHost(navController)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MusicDiscoveryAppBar(
    modifier: Modifier = Modifier,
    title: String,
    navigateBack: (() -> Unit)? = null,
    scrollBehavior: TopAppBarScrollBehavior,
) {
    TopAppBar(
        title = { Text(title) },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        modifier = modifier,
        scrollBehavior = scrollBehavior,
        navigationIcon = {
            if (navigateBack != null) {
                IconButton(onClick = navigateBack) {
                    Icon(
                        imageVector = Icons.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            }
        }
    )
}

