package com.example.musicdiscovery.ui.screens.artists

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.musicdiscovery.MusicDiscoveryApplication
import com.example.musicdiscovery.data.DeezerArtistRepository
import com.example.musicdiscovery.model.Artist
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface ArtistsUiState {
    data class Success(val artists: List<Artist>) : ArtistsUiState
    object Error : ArtistsUiState
    object Loading : ArtistsUiState
}

class ArtistsViewModel(private val deezerArtistRepository : DeezerArtistRepository) : ViewModel() {
    var artistsUiState: ArtistsUiState by mutableStateOf(ArtistsUiState.Loading)
        private set
    var initialExecuted = false;

    fun initialGetArtists(artistName: String) {
        if (!initialExecuted)
            getArtists(artistName)
        initialExecuted = true
    }

    fun getArtists(artistName: String) {
        viewModelScope.launch {
            artistsUiState = ArtistsUiState.Loading
            artistsUiState = try {
                val artists = deezerArtistRepository.searchArtist(artistName)
                ArtistsUiState.Success(artists.data)
            } catch (e: IOException) {
                e.printStackTrace()
                ArtistsUiState.Error
            } catch (e: HttpException) {
                e.printStackTrace()
                ArtistsUiState.Error
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MusicDiscoveryApplication)
                val deezerArtistRepository = application.container.deezerArtistRepository
                ArtistsViewModel(deezerArtistRepository = deezerArtistRepository)
            }
        }
    }
}