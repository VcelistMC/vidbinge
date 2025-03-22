package com.example.vidbinge.home.ui.screens

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.vidbinge.common.data.models.movie.Movie
import com.example.vidbinge.common.data.models.PillChoices
import com.example.vidbinge.common.data.models.tvshow.TvShow
import com.example.vidbinge.common.ui.components.ErrorScreen
import com.example.vidbinge.common.ui.components.PillBar
import com.example.vidbinge.common.ui.components.PillBarTheme
import com.example.vidbinge.home.ui.components.MovieCarousel
import com.example.vidbinge.home.ui.components.MoviePortraitCard
import com.example.vidbinge.home.ui.components.TVCarousel
import com.example.vidbinge.home.ui.components.TVPortraitCard
import com.example.vidbinge.home.ui.effects.HomeScreenEffect
import com.example.vidbinge.home.ui.intents.HomeScreenIntent
import com.example.vidbinge.home.ui.states.HomeScreenState
import com.example.vidbinge.home.ui.viewmodels.HomeScreenViewModel
import kotlinx.serialization.Serializable

@Serializable
object HomeDestination

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onMovieClicked: (Movie) -> Unit,
    onTvShowClicked: (TvShow) -> Unit,
    viewModel: HomeScreenViewModel
) {
    val state by viewModel.screenState.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.screenEffects.collect { effect ->
            when(effect){
                is HomeScreenEffect.ShowToast -> {
                    Toast.makeText(context, effect.message, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 12.dp, horizontal = 12.dp),
                userName = "Peter",
                notificationCount = 2
            )
        }
    ) { paddingValues ->
        if(state.errorMessage != null){
            ErrorScreen(
                Modifier.padding(paddingValues),
                message = state.errorMessage!!
            )
        }else{
            HomeScreenContent(
                modifier = Modifier.padding(paddingValues),
                homeScreenState = state,
                onPillPressed = { viewModel.handleIntent(HomeScreenIntent.SwitchPill(it)) },
                onMoviePressed = onMovieClicked,
                onTvShowClicked = onTvShowClicked
            )
        }
    }
}

@Composable
fun HomeScreenContent(
    modifier: Modifier = Modifier,
    homeScreenState: HomeScreenState,
    onPillPressed: (PillChoices) -> Unit,
    onMoviePressed: (Movie) -> Unit,
    onTvShowClicked: (TvShow) -> Unit,
) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(
                    state = rememberScrollState(),
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            PillBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                choices = PillChoices.entries,
                selectedPill = homeScreenState.selectedPill,
                onChoicePressed = onPillPressed,
                pillBarTheme = PillBarTheme(
                    selectedPillBg = MaterialTheme.colorScheme.primary,
                    selectedPillTextColor = MaterialTheme.colorScheme.onPrimary,
                    unselectedPillTextColor = MaterialTheme.colorScheme.primary
                )
            )
            if (!homeScreenState.isLoading) {
                AnimatedVisibility(homeScreenState.selectedPill != PillChoices.TV) {
                    NowPlayingMovies(
                        movieList = homeScreenState.homeScreenMovies.nowPlayingMovies,
                        onNowPlayingMoviePressed = onMoviePressed
                    )
                }

                AnimatedVisibility(homeScreenState.selectedPill == PillChoices.TV) {
                    NowPlayingTv(
                        tvList = homeScreenState.homescreenTV.nowAiringShows,
                        onNowPlayingTvPressed = onTvShowClicked
                    )
                }

                AnimatedVisibility(
                    homeScreenState.selectedPill == PillChoices.ALL ||
                            homeScreenState.selectedPill == PillChoices.MOVIES
                ) {
                    MoviePortraitCardList(
                        modifier = Modifier.padding(12.dp),
                        title = "Popular Movies",
                        movieList = homeScreenState.homeScreenMovies.popularMovies,
                        onMovieCardPressed = onMoviePressed
                    )
                }

                AnimatedVisibility(
                    homeScreenState.selectedPill == PillChoices.ALL ||
                            homeScreenState.selectedPill == PillChoices.TV
                ) {
                    TVPortraitCardList(
                        modifier = Modifier.padding(12.dp),
                        title = "Popular TV Shows",
                        tvList = homeScreenState.homescreenTV.popularShows,
                        onTvCardPressed = onTvShowClicked
                    )
                }


                AnimatedVisibility(
                    homeScreenState.selectedPill == PillChoices.ALL ||
                        homeScreenState.selectedPill == PillChoices.MOVIES
                ) {
                    MoviePortraitCardList(
                        modifier = Modifier.padding(12.dp),
                        title = "Top Rated Movies",
                        movieList = homeScreenState.homeScreenMovies.topRatedMovies,
                        onMovieCardPressed = onMoviePressed
                    )
                }


                AnimatedVisibility(
                    homeScreenState.selectedPill == PillChoices.ALL ||
                            homeScreenState.selectedPill == PillChoices.TV
                ) {
                    TVPortraitCardList(
                        modifier = Modifier.padding(12.dp),
                        title = "Top Rated TV Shows",
                        tvList = homeScreenState.homescreenTV.topRatedShows,
                        onTvCardPressed = onTvShowClicked
                    )
                }

            } else {
                LinearProgressIndicator(Modifier.fillMaxWidth())
            }

        }
    }



@Composable
fun MoviePortraitCardList(
    modifier: Modifier = Modifier,
    title: String,
    movieList: List<Movie>,
    onMovieCardPressed: (Movie) -> Unit
) {
    Column(modifier, Arrangement.spacedBy(12.dp)) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start,
            text = title,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold,
            fontSize = 25.sp
        )
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(movieList) { movie ->
                MoviePortraitCard(
                    modifier = Modifier.clickable { onMovieCardPressed(movie) },
                    movieItem = movie,
                )
            }
        }
    }

}

@Composable
fun TVPortraitCardList(
    modifier: Modifier = Modifier,
    title: String,
    tvList: List<TvShow>,
    onTvCardPressed: (TvShow) -> Unit
) {
    Column(modifier, Arrangement.spacedBy(12.dp)) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Start,
            text = title,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold,
            fontSize = 25.sp
        )
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(tvList) { tv ->
                TVPortraitCard(
                    modifier = Modifier.clickable { onTvCardPressed(tv) },
                    tvItem = tv,
                )
            }
        }
    }

}

@Composable
fun TopAppBar(
    modifier: Modifier = Modifier,
    userName: String,
    notificationCount: Int
) {
    Row(
        modifier,
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Hello, $userName",
            fontWeight = FontWeight.Bold,
            fontSize = 18.sp
        )
    }
}

@Composable
fun NowPlayingMovies(
    modifier: Modifier = Modifier,
    movieList: List<Movie>,
    onNowPlayingMoviePressed: (Movie) -> Unit
) {
    Column(
        modifier
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(vertical = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            "Now Playing in Theaters",
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp),
            color = MaterialTheme.colorScheme.onPrimary
        )
        MovieCarousel(
            modifier = Modifier
                .fillMaxWidth(),
            movieItems = movieList,
            onCardPressed = onNowPlayingMoviePressed
        )
    }
}

@Composable
fun NowPlayingTv(
    modifier: Modifier = Modifier,
    tvList: List<TvShow>,
    onNowPlayingTvPressed: (TvShow) -> Unit
) {
    Column(
        modifier
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(vertical = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            "Airing Now on TV",
            fontSize = 25.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp),
            color = MaterialTheme.colorScheme.onPrimary
        )
        TVCarousel(
            modifier = Modifier
                .fillMaxWidth(),
            tvItems = tvList,
            onCardPressed = onNowPlayingTvPressed
        )
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
}