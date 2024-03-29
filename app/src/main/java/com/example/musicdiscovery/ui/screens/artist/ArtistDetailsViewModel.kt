package com.example.musicdiscovery.ui.screens.artist

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.musicdiscovery.data.DeezerArtistRepository
import com.example.musicdiscovery.model.Artist
import com.example.musicdiscovery.model.Track
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

data class ArtistDetailsData(val artist: Artist, val tracks: List<Track>)

sealed interface ArtistDetailsUiState {
    data class Success(
        val artistDetails: ArtistDetailsData,
        val isFetchingMore: Boolean,
        val hasMore: Boolean
    ) : ArtistDetailsUiState

    data object Error : ArtistDetailsUiState
    data object Loading : ArtistDetailsUiState
}

class ArtistDetailsViewModel(
    savedStateHandle: SavedStateHandle,
    private val deezerArtistRepository: DeezerArtistRepository
) : ViewModel() {
    var artistDetailsUiState: ArtistDetailsUiState by mutableStateOf(ArtistDetailsUiState.Loading)
        private set
    private val artistId: Long =
        checkNotNull(savedStateHandle[ArtistDetailsDestination.artistIdArg])

    private val limit = 10
    private var index = 0

    init {
        getArtistDetails(artistId)
        index += limit
    }

    /**
     * Get the details of an artist, including tracks
     */
    fun getArtistDetails(artistId: Long) {
        viewModelScope.launch {
            artistDetailsUiState = ArtistDetailsUiState.Loading
            artistDetailsUiState = try {
                val artist = deezerArtistRepository.getArtistDetails(artistId)
                val tracks = deezerArtistRepository.getArtistTracks(artistId, limit, index)
                ArtistDetailsUiState.Success(
                    ArtistDetailsData(artist, tracks.data),
                    false,
                    if (tracks.data.isEmpty()) false else tracks.total > tracks.data.count()
                )
            } catch (e: IOException) {
                e.printStackTrace()
                ArtistDetailsUiState.Error
            } catch (e: HttpException) {
                e.printStackTrace()
                ArtistDetailsUiState.Error
            }
        }
    }

    /**
     * Fetch more tracks of the artist
     */
    fun fetchMoreTracks() {
        if (artistDetailsUiState !is ArtistDetailsUiState.Success)
            return

        viewModelScope.launch {
            artistDetailsUiState = ArtistDetailsUiState.Success(
                (artistDetailsUiState as ArtistDetailsUiState.Success).artistDetails,
                true,
                (artistDetailsUiState as ArtistDetailsUiState.Success).hasMore
            )
            artistDetailsUiState = try {
                val newTracks = deezerArtistRepository.getArtistTracks(artistId, limit, index)
                val tracks =
                    (artistDetailsUiState as ArtistDetailsUiState.Success).artistDetails.tracks + newTracks.data
                ArtistDetailsUiState.Success(
                    ArtistDetailsData(
                        (artistDetailsUiState as ArtistDetailsUiState.Success).artistDetails.artist,
                        tracks
                    ), false, newTracks.total > tracks.count()
                )
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