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
import com.example.vidbinge.home.data.model.MoviePortraitItem
import com.example.vidbinge.home.ui.components.MovieCarousel
import com.example.vidbinge.home.ui.components.MoviePortraitCard
import com.example.vidbinge.ui.theme.VidBingeTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            VidBingeTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    MoviePortraitCard(
                        modifier = Modifier.padding(innerPadding).padding(horizontal = 20.dp),
                        movieItem = MoviePortraitItem.moana2
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