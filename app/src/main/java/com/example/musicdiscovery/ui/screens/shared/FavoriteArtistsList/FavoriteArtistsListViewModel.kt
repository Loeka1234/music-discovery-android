package com.example.musicdiscovery.ui.screens.shared.FavoriteArtistsList

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.musicdiscovery.MusicDiscoveryApplication
import com.example.musicdiscovery.data.FavoriteArtistsRepository
import com.example.musicdiscovery.entity.FavoriteArtistEntity
import com.example.musicdiscovery.ui.screens.artists.ArtistsUiData
import com.example.musicdiscovery.ui.screens.artists.ArtistsViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

sealed interface FavoriteArtistsListUiState {
    data class Success(val favoriteArtists: List<FavoriteArtistEntity>) : FavoriteArtistsListUiState
    object Loading : FavoriteArtistsListUiState
}

class FavoriteArtistsListViewModel(
    private val favoriteArtistsRepository: FavoriteArtistsRepository
): ViewModel() {
    var favoriteArtistsListUiState: FavoriteArtistsListUiState by mutableStateOf(
        FavoriteArtistsListUiState.Loading)
        private set

    init {
        viewModelScope.launch {
            favoriteArtistsListUiState = FavoriteArtistsListUiState.Loading
            val favoriteArtists = favoriteArtistsRepository.getAllFavoriteArtistsStream().first()
            favoriteArtistsListUiState = FavoriteArtistsListUiState.Success(favoriteArtists)
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MusicDiscoveryApplication)
                val favoriteArtistsRepository = application.container.favoriteArtistsRepository
                FavoriteArtistsListViewModel(favoriteArtistsRepository = favoriteArtistsRepository)
            }
        }
    }
}