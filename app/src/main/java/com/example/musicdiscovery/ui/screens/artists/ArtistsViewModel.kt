package com.example.musicdiscovery.ui.screens.artists

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
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
    data class Success(
        val artistsUiData: ArtistsUiData,
        val isFetchingMore: Boolean,
        val hasMore: Boolean
    ) : ArtistsUiState

    data object Error : ArtistsUiState
    data object Loading : ArtistsUiState
}

class ArtistsViewModel(
    savedStateHandle: SavedStateHandle,
    private val deezerArtistRepository: DeezerArtistRepository,
    private val favoriteArtistsRepository: FavoriteArtistsRepository
) : ViewModel() {
    var artistsUiState: ArtistsUiState by mutableStateOf(ArtistsUiState.Loading)
        private set

    private val artistName: String =
        checkNotNull(savedStateHandle[ArtistsDestination.artistNameArg])

    private val limit = 10
    private var index = 0

    init {
        getArtists(artistName)
        index += limit
    }

    fun getArtists(artistName: String) {
        viewModelScope.launch {
            artistsUiState = ArtistsUiState.Loading
            artistsUiState = try {
                val artists = deezerArtistRepository.searchArtist(artistName, limit, index)
                val favoriteArtists = favoriteArtistsRepository.getAllFavoriteArtistsStream()
                ArtistsUiState.Success(
                    ArtistsUiData(artists.data, favoriteArtists.first()),
                    false,
                    artists.total > artists.data.count()
                )
            } catch (e: IOException) {
                e.printStackTrace()
                ArtistsUiState.Error
            } catch (e: HttpException) {
                e.printStackTrace()
                ArtistsUiState.Error
            }
        }
    }


    fun fetchMoreArtists() {
        if (artistsUiState !is ArtistsUiState.Success) return

        viewModelScope.launch {
            artistsUiState = ArtistsUiState.Success(
                (artistsUiState as ArtistsUiState.Success).artistsUiData,
                true,
                (artistsUiState as ArtistsUiState.Success).hasMore
            )
            artistsUiState = try {
                val newArtists = deezerArtistRepository.searchArtist(artistName, limit, index)
                val artists =
                    (artistsUiState as ArtistsUiState.Success).artistsUiData.artists + newArtists.data
                index += limit
                ArtistsUiState.Success(
                    ArtistsUiData(
                        artists,
                        (artistsUiState as ArtistsUiState.Success).artistsUiData.favoriteArtists
                    ),
                    false,
                    newArtists.total > artists.count()
                )
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
            val result = favoriteArtistsRepository.insertOrDeleteFavoriteArtistIfExists(
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
                ),
                false,
                (artistsUiState as ArtistsUiState.Success).hasMore
            )
        }
    }
}