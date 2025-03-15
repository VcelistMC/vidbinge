package com.example.vidbinge

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.vidbinge.common.data.models.movie.Movie
import com.example.vidbinge.common.data.models.movie.MovieDetails
import com.example.vidbinge.common.ui.navtypes.MovieType
import com.example.vidbinge.details.ui.screens.MovieDetailsScreen
import com.example.vidbinge.home.ui.screens.HomeScreen
import com.example.vidbinge.ui.theme.old.VidBingeTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.serialization.Serializable
import kotlin.reflect.typeOf

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            VidBingeTheme {
                val navController = rememberNavController()

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    NavHost(navController = navController, startDestination = HomeDestination){
                        composable<HomeDestination> {
                            HomeScreen(
                                modifier = Modifier.padding(innerPadding),
                                viewModel = hiltViewModel(),
                                onMovieClicked = { movie ->
                                    navController.navigate(MovieDetailsDestination(movie.id))
                                }
                            )
                        }

                        composable<MovieDetailsDestination>{
                            MovieDetailsScreen(
                                Modifier.padding(innerPadding),
                                onBackClicked = {
                                    navController.popBackStack()
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}


@Serializable
object HomeDestination


@Serializable
data class MovieDetailsDestination(
    val movieId: Int
)