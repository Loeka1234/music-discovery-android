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
import com.example.musicdiscovery.data.Event
import com.example.musicdiscovery.data.FavoriteArtistsRepository
import com.example.musicdiscovery.entity.FavoriteArtistEntity
import com.example.musicdiscovery.model.Artist
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

data class ArtistsUiData(
    val artists: List<Artist>,
    val favoriteArtists: List<FavoriteArtistEntity>
)

sealed interface ArtistsUiState {
    data class Success(val artistsUiData: ArtistsUiData) : ArtistsUiState
    object Error : ArtistsUiState
    object Loading : ArtistsUiState
}

class ArtistsViewModel(
    private val deezerArtistRepository : DeezerArtistRepository,
    private val favoriteArtistsRepository: FavoriteArtistsRepository
) : ViewModel() {
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
                val favoriteArtists = favoriteArtistsRepository.getAllFavoriteArtistsStream()
                ArtistsUiState.Success(ArtistsUiData(artists.data, favoriteArtists.first()))
            } catch (e: IOException) {
                e.printStackTrace()
                ArtistsUiState.Error
            } catch (e: HttpException) {
                e.printStackTrace()
                ArtistsUiState.Error
            }
        }
    }

    fun favoriteOrUnfavoriteArtist(artist: Artist) {
        if (artistsUiState !is ArtistsUiState.Success) return

        viewModelScope.launch {
            var result = favoriteArtistsRepository.insertOrDeleteFavoriteArtistIfExists(
                FavoriteArtistEntity(artist.id, artist.name, artist.picture)
            )

            var cloned =
                ArrayList((artistsUiState as ArtistsUiState.Success).artistsUiData.favoriteArtists)

            if (result == Event.INSERTED) {
                cloned.add(FavoriteArtistEntity(artist.id, artist.name, artist.picture))
            } else {
                cloned = ArrayList(cloned.filter { it.id != artist.id })
            }

            artistsUiState = ArtistsUiState.Success(
                ArtistsUiData(
                    (artistsUiState as ArtistsUiState.Success).artistsUiData.artists,
                    cloned
                )
            )
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MusicDiscoveryApplication)
                val deezerArtistRepository = application.container.deezerArtistRepository
                val favoriteArtistsRepository = application.container.favoriteArtistsRepository
                ArtistsViewModel(deezerArtistRepository = deezerArtistRepository, favoriteArtistsRepository = favoriteArtistsRepository)
            }
        }
    }
}