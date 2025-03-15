@file:OptIn(ExperimentalLayoutApi::class)

package com.example.vidbinge.details.ui.screens

import android.annotation.SuppressLint
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowLeft
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.asDrawable
import coil3.compose.AsyncImage
import coil3.compose.AsyncImagePainter
import com.example.vidbinge.common.data.coil.TMDbImageSize
import com.example.vidbinge.common.data.models.Cast
import com.example.vidbinge.common.data.models.Genre
import com.example.vidbinge.common.ext.getDominantColor
import com.example.vidbinge.details.ui.viewmodels.MovieDetailsViewModel
import com.example.vidbinge.details.ui.states.MovieDetailsScreenState

@SuppressLint("NewApi")
@Composable
fun MovieDetailsScreen(
    modifier: Modifier = Modifier,
    onBackClicked: () -> Unit
) {
    val viewModel = hiltViewModel<MovieDetailsViewModel>()
    val detailsScreenState = viewModel._movieDetailsScreenState.collectAsStateWithLifecycle()
    if(detailsScreenState.value.isLoading){
        LinearProgressIndicator(modifier = modifier.fillMaxWidth())
    }else{
        MovieDetailsContent(
            modifier = modifier,
            movieDetailsScreenState = detailsScreenState.value,
            onShareClicked = {},
            onWatchlistClicked = {},
            onBackClicked = onBackClicked
        )
    }

}

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MovieDetailsContent(
    modifier: Modifier = Modifier,
    movieDetailsScreenState: MovieDetailsScreenState,
    onShareClicked: () -> Unit,
    onWatchlistClicked: () -> Unit,
    onBackClicked: () -> Unit
) {
    val localResources = LocalContext.current.resources
    val ambientColor = remember { mutableStateOf(Color.White) }

    ConstraintLayout(
        modifier.fillMaxSize().verticalScroll(rememberScrollState())
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.Transparent,
                        ambientColor.value,
                    ),
                    endY = 100f
                )
            )
    ) {
        val (movieImage, movieDetails, backBtn) = createRefs()
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .height(350.dp)
                .constrainAs(movieImage) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            model = movieDetailsScreenState.extraMovieDetails!!.posterFullPath(TMDbImageSize.X_LARGE),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            alignment = Alignment.TopCenter,
            onState = { state ->
                if (state is AsyncImagePainter.State.Success) {
                    val drawable = state.result.image.asDrawable(localResources)
                    ambientColor.value = drawable.getDominantColor()
                }
            }
        )

        BackButton(
            modifier = Modifier.constrainAs(backBtn) {
                top.linkTo(parent.top, 12.dp)
                start.linkTo(parent.start, 12.dp)
            },
            onClick = onBackClicked
        )

        MovieDetailsBlock(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            ambientColor.value,
                        ),
                        endY = 100f
                    )
                )

                .constrainAs(movieDetails) {
                    top.linkTo(movieImage.bottom, (-50).dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }

                .padding(horizontal = 12.dp),
            screenState = movieDetailsScreenState,
            onWatchlistClicked = onWatchlistClicked,
            onShareClicked = onShareClicked
        )
    }
}

@Composable
fun MovieDetailsBlock(
    modifier: Modifier,
    screenState: MovieDetailsScreenState,
    onWatchlistClicked: () -> Unit,
    onShareClicked: () -> Unit
) {
    ConstraintLayout(modifier) {
        val (movieTitle, yearRuntimeRow, overviewText, genresChips, buttonRow, cast) = createRefs()

        Text(
            text = screenState.extraMovieDetails!!.title,
            modifier = Modifier.constrainAs(movieTitle) {
                top.linkTo(parent.top, 50.dp)
                start.linkTo(parent.start)
            },
            fontWeight = FontWeight.ExtraBold,
            fontSize = 20.sp,
            color = MaterialTheme.colorScheme.onPrimary
        )

        Text(
            text = "${screenState.extraMovieDetails.releaseYear} • ${screenState.extraMovieDetails.runtimeAsString()} • ⭐ ${screenState.extraMovieDetails.voteAverage}",
            modifier = Modifier.constrainAs(yearRuntimeRow) {
                top.linkTo(movieTitle.bottom, 12.dp)
                start.linkTo(parent.start)
            },
            fontSize = 15.sp,
            color = MaterialTheme.colorScheme.onPrimary
        )

        GenresFlowChips(
            modifier = Modifier.constrainAs(genresChips) {
                top.linkTo(yearRuntimeRow.bottom, 12.dp)
                start.linkTo(parent.start)
            },
            genres = screenState.extraMovieDetails.genres,
            onChipClicked = {}
        )

        Text(
            text = screenState.extraMovieDetails.overview,
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(overviewText) {
                    top.linkTo(genresChips.bottom, 12.dp)
                    start.linkTo(parent.start)
                },
            fontSize = 15.sp,
            color = MaterialTheme.colorScheme.onPrimary,
            textAlign = TextAlign.Start
        )

        Row(
            modifier = Modifier
                .constrainAs(buttonRow) {
                    top.linkTo(overviewText.bottom, 12.dp)
                    start.linkTo(parent.start)
                }
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            SquareButton(
                Modifier.weight(1f),
                icon = {
                    Icon(
                        imageVector = if (false) Icons.Default.Check else Icons.Default.Add,
                        contentDescription = null,
                        tint = Color.White
                    )
                },
                text = "Watchlist",
                onClick = onWatchlistClicked
            )

            SquareButton(
                Modifier.weight(1f),
                icon = {
                    Icon(
                        imageVector = Icons.Default.Share,
                        contentDescription = null,
                        tint = Color.White
                    )
                },
                text = "Share",
                onClick = onShareClicked
            )
        }

        if (screenState.isCastListLoading) {
            LinearProgressIndicator(
                Modifier
                    .fillMaxWidth()
                    .constrainAs(cast) {
                        top.linkTo(buttonRow.bottom, 12.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .padding(bottom = 20.dp))
        } else {
            Cast(
                modifier = Modifier
                    .constrainAs(cast) {
                        top.linkTo(buttonRow.bottom, 12.dp)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .padding(bottom = 20.dp),
                cast = screenState.castList
            )
        }
    }
}

@Composable
fun GenresFlowChips(
    modifier: Modifier = Modifier,
    genres: List<Genre>,
    onChipClicked: (Genre) -> Unit
) {
    FlowRow(modifier) {
        genres.forEach { genre ->
            SuggestionChip(
                onClick = { onChipClicked(genre) },
                label = { Text(genre.genre) },
                colors = SuggestionChipDefaults.suggestionChipColors(
                    labelColor = MaterialTheme.colorScheme.onPrimary,
                    containerColor = Color.Transparent
                )
            )
            Spacer(Modifier.width(6.dp))
        }
    }
}

@Composable
fun BackButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    IconButton(
        modifier = modifier.background(
            color = Color.White,
            shape = CircleShape
        ),
        onClick = onClick,
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.KeyboardArrowLeft,
            contentDescription = null,
            tint = Color.Black
        )
    }
}

@Composable
fun Cast(
    modifier: Modifier = Modifier,
    cast: List<Cast>
) {
    Column(
        modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = "Cast",
            fontSize = 18.sp,
            fontWeight = FontWeight.SemiBold,
            color = MaterialTheme.colorScheme.onPrimary
        )

        cast.forEach {
            CastItem(castItem = it)
        }

    }
}

@Preview
@Composable
fun CastItem(
    modifier: Modifier = Modifier,
    castItem: Cast = Cast.mockList.first()
) {
    Row(
        modifier,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        AsyncImage(
            modifier = Modifier
                .size(30.dp)
                .clip(CircleShape),
            model = castItem.profileFullPath(TMDbImageSize.SMALL),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        Text(
            text = castItem.name,
            fontSize = 15.sp,
            fontWeight = FontWeight.Normal,
            color = MaterialTheme.colorScheme.onPrimary,
            textDecoration = TextDecoration.Underline,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center
        )
        Text(
            text = "as",
            fontSize = 15.sp,
            fontWeight = FontWeight.Normal,
            color = MaterialTheme.colorScheme.onPrimary,
            textAlign = TextAlign.Center
        )
        Text(
            text = castItem.character,
            fontSize = 15.sp,
            fontWeight = FontWeight.Normal,
            color = MaterialTheme.colorScheme.onPrimary,
            textDecoration = TextDecoration.Underline,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Center
        )

    }
}

@Composable
fun SquareButton(
    modifier: Modifier = Modifier,
    icon: @Composable () -> Unit,
    text: String,
    onClick: () -> Unit
) {
    val mutableInteractionSource = remember { MutableInteractionSource() }
    Column(
        modifier
            .size(100.dp)
            .border(1.dp, color = Color.White, shape = RoundedCornerShape(8.dp))
            .padding(12.dp)
            .clickable(
                onClick = onClick,
                indication = rememberRipple(color = MaterialTheme.colorScheme.onPrimary),
                interactionSource = mutableInteractionSource
            ),
        verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterVertically),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        icon()

        Text(
            text = text,
            modifier = Modifier.fillMaxWidth(),
            fontSize = 15.sp,
            fontWeight = FontWeight.Light,
            color = Color.White,
            textAlign = TextAlign.Center
        )
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Preview
@Composable
fun MovieDetailsPreview() {

}