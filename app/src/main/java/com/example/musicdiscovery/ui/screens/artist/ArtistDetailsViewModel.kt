package com.example.musicdiscovery.ui.screens.artist

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
import com.example.musicdiscovery.model.Track
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

sealed interface ArtistDetailsUiState {
    data class Success(val tracks: List<Track>) : ArtistDetailsUiState
    object Error : ArtistDetailsUiState
    object Loading : ArtistDetailsUiState
}

class ArtistDetailsViewModel(private val deezerArtistRepository : DeezerArtistRepository) : ViewModel() {
    var artistDetailsUiState: ArtistDetailsUiState by mutableStateOf(ArtistDetailsUiState.Loading)
        private set
    var initialExecuted = false

    fun initialGetTracks(artistId: Int) {
        if (initialExecuted) return
        getTracks(artistId)
        initialExecuted = true
    }

    fun getTracks(artistId: Int) {
        viewModelScope.launch {
            artistDetailsUiState = ArtistDetailsUiState.Loading
            artistDetailsUiState = try {
                val tracks = deezerArtistRepository.getArtistTracks(artistId)
                ArtistDetailsUiState.Success(tracks.data)
            } catch (e: IOException) {
                e.printStackTrace()
                ArtistDetailsUiState.Error
            } catch (e: HttpException) {
                e.printStackTrace()
                ArtistDetailsUiState.Error
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MusicDiscoveryApplication)
                val deezerArtistRepository = application.container.deezerArtistRepository
                ArtistDetailsViewModel(deezerArtistRepository = deezerArtistRepository)
            }
        }
    }
}