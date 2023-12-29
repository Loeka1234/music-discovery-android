package com.example.musicdiscovery.ui

import android.app.Application
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.musicdiscovery.MusicDiscoveryApplication
import com.example.musicdiscovery.ui.screens.artist.ArtistDetailsViewModel
import com.example.musicdiscovery.ui.screens.artists.ArtistsViewModel
import com.example.musicdiscovery.ui.screens.shared.favoriteArtistsList.FavoriteArtistsListViewModel

/**
 * Provides Factory to create instance of ViewModel for the entire Inventory app
 */
object AppViewModelProvider {
    val Factory = viewModelFactory {
        // Initializer for ArtistsViewModel
        initializer {
            ArtistsViewModel(
                this.createSavedStateHandle(),
                musicDiscoveryApplication().container.deezerArtistRepository,
                musicDiscoveryApplication().container.favoriteArtistsRepository
            )
        }
        // Initializer for ArtistDetailsViewModel
        initializer {
            ArtistDetailsViewModel(
                this.createSavedStateHandle(),
                musicDiscoveryApplication().container.deezerArtistRepository
            )
        }
        // Initializer for FavoriteArtistsListViewModel
        initializer {
            FavoriteArtistsListViewModel(
                musicDiscoveryApplication().container.favoriteArtistsRepository
            )
        }
    }
}

/**
 * Extension function to queries for [Application] object and returns an instance of
 * [MusicDiscoveryApplication].
 */
fun CreationExtras.musicDiscoveryApplication(): MusicDiscoveryApplication =
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MusicDiscoveryApplication)