package com.example.musicdiscovery

import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.navigation.compose.ComposeNavigator
import org.junit.Rule
import androidx.navigation.testing.TestNavHostController
import com.example.musicdiscovery.ui.screens.StartScreenDestination
import com.example.musicdiscovery.ui.screens.artist.ArtistDetailsDestination
import com.example.musicdiscovery.ui.screens.artists.ArtistsDestination
import org.junit.Before
import org.junit.Test

class MusicDiscoveryNavigationTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private lateinit var navController: TestNavHostController

    @Before
    fun setupMusicDiscoveryNavHost() {
        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current).apply {
                navigatorProvider.addNavigator(ComposeNavigator())
            }
            MusicDiscoveryApp(navController = navController)
        }
    }

    @Test
    fun musicDiscoveryNavHost_verifyStartDestination() {
        navController.assertCurrentRouteName(StartScreenDestination.route)
    }

    @Test
    fun musicDiscoveryHost_verifyBackNavigationNotShownOnStartOrderScreen() {
        val backText = composeTestRule.activity.getString(R.string.back_button)
        composeTestRule.onNodeWithContentDescription(backText).assertDoesNotExist()
    }

    private fun navigateToArtistsScreenFromStartScreen() {
        val testTagSearchArtists =
            composeTestRule.activity.getString(R.string.test_tag_search_artists)
        val searchArtistsInput = composeTestRule.onNodeWithTag(testTagSearchArtists)
        searchArtistsInput.performTextInput("artist")

        val searchArtistButtonText = composeTestRule.activity.getString(R.string.search_text)
        val searchArtistsButton = composeTestRule.onNodeWithText(searchArtistButtonText)
        searchArtistsButton.performClick()
    }

    @Test
    fun musicDiscoveryHost_searchArtist_navigatesToArtistsScreen() {
        navigateToArtistsScreenFromStartScreen()

        navController.assertCurrentRouteName(ArtistsDestination.routeWithArgs)
    }

    @Test
    fun musicDiscoveryHost_clickingArtists_navigatesToArtistDetailsScreen() {
        navigateToArtistsScreenFromStartScreen()

        val testTagArtistCard = composeTestRule.activity.getString(R.string.test_tag_artist)
        composeTestRule.waitUntil {
            composeTestRule.onAllNodesWithTag(testTagArtistCard).fetchSemanticsNodes().size > 1
        }
        composeTestRule.onAllNodesWithTag(testTagArtistCard)
            .onFirst()
            .performClick()

        navController.assertCurrentRouteName(ArtistDetailsDestination.routeWithArgs)
    }
}