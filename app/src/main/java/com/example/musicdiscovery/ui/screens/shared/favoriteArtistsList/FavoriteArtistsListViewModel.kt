package com.example.musicdiscovery.ui.screens.shared.favoriteArtistsList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicdiscovery.data.FavoriteArtistsRepository
import com.example.musicdiscovery.entity.FavoriteArtistEntity
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

data class FavoriteArtistsListUiState(val favoriteArtists: List<FavoriteArtistEntity> = listOf())

class FavoriteArtistsListViewModel(
    private val favoriteArtistsRepository: FavoriteArtistsRepository
): ViewModel() {
    /**
     * The list of items are retrieved from [favoriteArtistsRepository] and mapped to
     * [FavoriteArtistsListUiState]
     */
    val favoriteArtistsListUiState: StateFlow<FavoriteArtistsListUiState> =
        favoriteArtistsRepository.getAllFavoriteArtistsStream().map { FavoriteArtistsListUiState(it) }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = FavoriteArtistsListUiState()
            )

    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }
}