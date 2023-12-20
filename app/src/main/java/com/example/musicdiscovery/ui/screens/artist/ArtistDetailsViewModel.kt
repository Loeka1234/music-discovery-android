package com.example.musicdiscovery.ui.screens.artist

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.musicdiscovery.MusicDiscoveryApplication
import com.example.musicdiscovery.data.DeezerArtistRepository
import com.example.musicdiscovery.model.Artist
import com.example.musicdiscovery.model.Track
import com.example.musicdiscovery.ui.screens.artists.ArtistsDestination
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

data class ArtistDetailsData (val artist: Artist, val tracks: List<Track>)

sealed interface ArtistDetailsUiState {
    data class Success(val artistDetails: ArtistDetailsData) : ArtistDetailsUiState
    object Error : ArtistDetailsUiState
    object Loading : ArtistDetailsUiState
}

class ArtistDetailsViewModel(
    savedStateHandle: SavedStateHandle,
    private val deezerArtistRepository : DeezerArtistRepository
) : ViewModel() {
    var artistDetailsUiState: ArtistDetailsUiState by mutableStateOf(ArtistDetailsUiState.Loading)
        private set
    private val artistId: Long = checkNotNull(savedStateHandle[ArtistDetailsDestination.artistIdArg])

    init {
        getArtistDetails(artistId)
    }

    fun getArtistDetails(artistId: Long) {
        viewModelScope.launch {
            artistDetailsUiState = ArtistDetailsUiState.Loading
            artistDetailsUiState = try {
                val artist = deezerArtistRepository.getArtistDetails(artistId)
                val tracks = deezerArtistRepository.getArtistTracks(artistId)
                ArtistDetailsUiState.Success(ArtistDetailsData(artist, tracks.data))
            } catch (e: IOException) {
                e.printStackTrace()
                ArtistDetailsUiState.Error
            } catch (e: HttpException) {
                e.printStackTrace()
                ArtistDetailsUiState.Error
            }
        }
    }
}