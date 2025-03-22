@file:OptIn(ExperimentalLayoutApi::class)

package com.example.vidbinge.details.ui.screens

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
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
import androidx.compose.runtime.LaunchedEffect
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
import com.example.vidbinge.details.data.model.Cast
import com.example.vidbinge.common.data.models.Genre
import com.example.vidbinge.common.data.models.RelativeLuma
import com.example.vidbinge.common.ext.relativeLuma
import com.example.vidbinge.common.ui.components.ErrorScreen
import com.example.vidbinge.common.ui.components.LoadingObject
import com.example.vidbinge.details.ui.states.TvShowDetailsScreenState
import com.example.vidbinge.details.ui.viewmodels.TvShowDetailsViewModel
import kotlinx.serialization.Serializable


@Serializable
data class TvShowDetailsDestination(
    val tvShowId: Int
)


@SuppressLint("NewApi")
@Composable
fun TvShowDetailsScreen(
    modifier: Modifier = Modifier,
    onBackClicked: () -> Unit
) {
    val viewModel = hiltViewModel<TvShowDetailsViewModel>()

    val detailsScreenState = viewModel.screenState.collectAsStateWithLifecycle()
    if (detailsScreenState.value.isLoading) {
        LoadingObject()
    } else if(detailsScreenState.value.errorMessage != null){
        ErrorScreen(message = detailsScreenState.value.errorMessage!!)
    } else {
        TvShowDetailsContent(
            modifier = modifier,
            tvShowDetailsScreenState = detailsScreenState.value,
            onShareClicked = {},
            onWatchlistClicked = {},
            onImageLoadedForAmbientColor = viewModel::onImageLoadedForAmbientColor,
            onBackClicked = onBackClicked
        )
    }

}

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun TvShowDetailsContent(
    modifier: Modifier = Modifier,
    tvShowDetailsScreenState: TvShowDetailsScreenState,
    onShareClicked: () -> Unit,
    onWatchlistClicked: () -> Unit,
    onBackClicked: () -> Unit,
    onImageLoadedForAmbientColor: (Drawable) -> Unit
) {
    val localResources = LocalContext.current.resources

    ConstraintLayout(
        modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color.Transparent,
                        tvShowDetailsScreenState.ambientScreenColor,
                    ),
                    endY = 100f
                )
            )
    ) {
        val (tvImage, tvDetails, backBtn) = createRefs()
        AsyncImage(
            modifier = Modifier
                .fillMaxWidth()
                .height(350.dp)
                .constrainAs(tvImage) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            model = tvShowDetailsScreenState.tvShowDetails!!.backdropFullUrl(TMDbImageSize.X_LARGE),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            alignment = Alignment.TopCenter,
            onState = { state ->
                if (state is AsyncImagePainter.State.Success) {
                    val drawable = state.result.image.asDrawable(localResources)
                    onImageLoadedForAmbientColor(drawable)
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

        TvShowDetailsBlock(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color.Transparent,
                            tvShowDetailsScreenState.ambientScreenColor,
                        ),
                        endY = 100f
                    )
                )

                .constrainAs(tvDetails) {
                    top.linkTo(tvImage.bottom, (-50).dp)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                }

                .padding(horizontal = 12.dp),
            screenState = tvShowDetailsScreenState,
            onWatchlistClicked = onWatchlistClicked,
            onShareClicked = onShareClicked
        )
    }
}

@Composable
fun TvShowDetailsBlock(
    modifier: Modifier,
    screenState: TvShowDetailsScreenState,
    onWatchlistClicked: () -> Unit,
    onShareClicked: () -> Unit,
) {
    ConstraintLayout(modifier) {
        val (tvTitle, yearRuntimeRow, overviewText, genresChips, buttonRow, cast) = createRefs()

        val textColorAgainstDominant = if (screenState.ambientScreenColor.relativeLuma == RelativeLuma.BRIGHT)
            MaterialTheme.colorScheme.primary
        else
            MaterialTheme.colorScheme.onPrimary

        Text(
            text = screenState.tvShowDetails!!.title,
            modifier = Modifier.constrainAs(tvTitle) {
                top.linkTo(parent.top, 50.dp)
                start.linkTo(parent.start)
            },
            fontWeight = FontWeight.ExtraBold,
            fontSize = 20.sp,
            color = textColorAgainstDominant
        )

        Text(
            text = "⭐ ${screenState.tvShowDetails.voteAverage}",
            modifier = Modifier.constrainAs(yearRuntimeRow) {
                top.linkTo(tvTitle.bottom, 12.dp)
                start.linkTo(parent.start)
            },
            fontSize = 15.sp,
            color = textColorAgainstDominant
        )

        GenresFlowChips(
            modifier = Modifier.constrainAs(genresChips) {
                top.linkTo(yearRuntimeRow.bottom, 12.dp)
                start.linkTo(parent.start)
            },
            genres = screenState.tvShowDetails.genres,
            onChipClicked = {},
            color = textColorAgainstDominant
        )

        Text(
            text = screenState.tvShowDetails.overview,
            modifier = Modifier
                .fillMaxWidth()
                .constrainAs(overviewText) {
                    top.linkTo(genresChips.bottom, 12.dp)
                    start.linkTo(parent.start)
                },
            fontSize = 15.sp,
            color = textColorAgainstDominant,
            textAlign = TextAlign.Start
        )

        Row(
            modifier = Modifier
                .constrainAs(buttonRow) {
                    top.linkTo(overviewText.bottom, 12.dp)
                    start.linkTo(parent.start)
                }
                .fillMaxWidth()
                .padding(vertical = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            SquareButton(
                Modifier.weight(1f),
                icon = {
                    Icon(
                        imageVector = if (screenState.isAddedToWatchList) Icons.Default.Check else Icons.Default.Add,
                        contentDescription = null,
                        tint = textColorAgainstDominant
                    )
                },
                text = "Watchlist",
                onClick = onWatchlistClicked,
                color = textColorAgainstDominant
            )

            SquareButton(
                Modifier.weight(1f),
                icon = {
                    Icon(
                        imageVector = Icons.Default.Share,
                        contentDescription = null,
                        tint = textColorAgainstDominant
                    )
                },
                text = "Share",
                onClick = onShareClicked,
                color = textColorAgainstDominant
            )
        }
    }
}

