package com.example.vidbinge.home.ui.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.vidbinge.common.data.models.Movie
import com.example.vidbinge.common.data.models.Pill
import com.example.vidbinge.common.ext.debugBorder
import com.example.vidbinge.common.ui.components.PillBar
import com.example.vidbinge.common.ui.components.PillBarTheme
import com.example.vidbinge.home.ui.components.MovieCarousel
import com.example.vidbinge.home.ui.components.MoviePortraitCard

@Composable
fun HomeScreen(

) {

}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreenContent(
    modifier: Modifier = Modifier
) {
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
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(
                    state = rememberScrollState(),
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            PillBar(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp)
                    ,
                choices = Pill.mockList,
                selectedPill = Pill.mock1,
                onChoicePressed = { /* TODO */ },
                pillBarTheme = PillBarTheme(
                    selectedPillBg = MaterialTheme.colorScheme.primary,
                    selectedPillTextColor = MaterialTheme.colorScheme.onPrimary,
                    unselectedPillTextColor = MaterialTheme.colorScheme.primary
                )
            )
            NowPlaying()

            MoviePortraitCardList(
                modifier = Modifier.padding(12.dp),
                title = "Movies For You.",
                movieList = Movie.mockList
            )

            MoviePortraitCardList(
                modifier = Modifier.padding(12.dp),
                title = "Award Winning Movies.",
                movieList = Movie.mockList
            )
        }
    }
}


@Composable
fun MoviePortraitCardList(
    modifier: Modifier = Modifier,
    title: String,
    movieList: List<Movie>
) {
    Column(modifier, Arrangement.spacedBy(12.dp)) {
        Text(
            text = title,
            color = MaterialTheme.colorScheme.primary,
            fontWeight = FontWeight.Bold,
            fontSize = 25.sp
        )
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(movieList){ movie ->
                MoviePortraitCard(
                    movieItem = movie
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
        BadgedBell(
            count = notificationCount
        )
    }
}

@Composable
fun BadgedBell(
    modifier: Modifier = Modifier,
    count: Int = 2
) {
    BadgedBox(
        modifier = modifier,
        badge = {
            Badge {
                Text("$count")
            }
        }
    ) {
        Icon(
            imageVector = Icons.Outlined.Notifications,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
fun NowPlaying(
    modifier: Modifier = Modifier,
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
            movieItems = Movie.mockList,
            onCardPressed = {}
        )
    }
}

@Preview
@Composable
fun HomeScreenPreview() {
    HomeScreenContent()
}