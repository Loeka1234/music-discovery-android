package com.example.musicdiscovery.ui.test

import androidx.lifecycle.SavedStateHandle
import com.example.musicdiscovery.fake.FakeDeezerArtistRepository
import com.example.musicdiscovery.fake.FakeFavoriteArtistsRepository
import com.example.musicdiscovery.ui.screens.artists.ArtistsUiState
import com.example.musicdiscovery.ui.screens.artists.ArtistsViewModel
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description

class ArtistsViewModelTest {
    private lateinit var artistsViewModel: ArtistsViewModel

    private val artistsCount = 20
    @Before
    fun setUp() {
        artistsViewModel = ArtistsViewModel(
            SavedStateHandle(mapOf(Pair("artistName", "artist"))),
            FakeDeezerArtistRepository(artistsCount),
            FakeFavoriteArtistsRepository()
        )
    }

    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule(UnconfinedTestDispatcher())

    @Test
    fun artistsViewModel_Initialization_ArtistsAndFavoriteArtistsLoaded() {
        assertTrue(artistsViewModel.artistsUiState is ArtistsUiState.Success)
        assertEquals(
            0,
            (artistsViewModel.artistsUiState as ArtistsUiState.Success).artistsUiData.favoriteArtists.count()
        )
        assertEquals(
            10,
            (artistsViewModel.artistsUiState as ArtistsUiState.Success).artistsUiData.artists.count()
        )
        assertTrue((artistsViewModel.artistsUiState as ArtistsUiState.Success).hasMore)
    }

    @Test
    fun artistsViewModel_fetchMore_FetchesMoreArtists() {
        artistsViewModel.fetchMoreArtists()
        assertTrue(artistsViewModel.artistsUiState is ArtistsUiState.Success)
        assertEquals(
            0,
            (artistsViewModel.artistsUiState as ArtistsUiState.Success).artistsUiData.favoriteArtists.count()
        )
        assertEquals(
            20,
            (artistsViewModel.artistsUiState as ArtistsUiState.Success).artistsUiData.artists.count()
        )
        assertFalse((artistsViewModel.artistsUiState as ArtistsUiState.Success).hasMore)
    }

    @Test
    fun artistsViewModel_favoriteOrUnfavoriteArtist_RemovesOrAddsArtistToFavorites() {
        val artist = (artistsViewModel.artistsUiState as ArtistsUiState.Success).artistsUiData.artists[0]

        artistsViewModel.favoriteOrUnfavoriteArtist(artist)

        assertEquals(artist.id, (artistsViewModel.artistsUiState as ArtistsUiState.Success).artistsUiData.favoriteArtists[0].id)

        artistsViewModel.favoriteOrUnfavoriteArtist(artist)
        assertEquals(0, (artistsViewModel.artistsUiState as ArtistsUiState.Success).artistsUiData.favoriteArtists.count())
    }
}

@ExperimentalCoroutinesApi
class MainCoroutineRule(private val dispatcher: TestDispatcher = StandardTestDispatcher()) :
    TestWatcher() {

    override fun starting(description: Description?) {
        super.starting(description)
        Dispatchers.setMain(dispatcher)
    }

    override fun finished(description: Description?) {
        super.finished(description)
        Dispatchers.resetMain()
    }
}