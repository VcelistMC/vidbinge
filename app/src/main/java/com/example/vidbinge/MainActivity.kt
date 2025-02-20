package com.example.vidbinge

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.vidbinge.home.data.model.MovieCarouselItem
import com.example.vidbinge.home.ui.components.MovieCarousel
import com.example.vidbinge.ui.theme.VidBingeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            VidBingeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MovieCarousel(
                        modifier = Modifier.padding(innerPadding).padding(horizontal = 16.dp),
                        movieItems = listOf(
                            MovieCarouselItem(id = 1, imgUrl = "https://pbs.twimg.com/media/D4pgj_BVUAAbCTd?format=jpg&name=900x900"),
                            MovieCarouselItem(id = 2, imgUrl = "https://wallpapersmug.com/download/1600x900/e5a909/star-wars-the-last-jedi-2017-movie-poster-red.jpg"),
                            MovieCarouselItem(id = 3, imgUrl = "https://d1csarkz8obe9u.cloudfront.net/posterpreviews/sci-fi-movie-poster-facebook-video-design-template-be422d6a6c1c2dd418e36389cce9ea13_screen.jpg?ts=1615966947"),
                        ),
                        onCardPressed = {}
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    VidBingeTheme {
        Greeting("Android")
    }
}