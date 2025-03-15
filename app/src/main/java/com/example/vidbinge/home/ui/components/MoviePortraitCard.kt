package com.example.vidbinge.home.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import coil3.compose.AsyncImage
import com.example.vidbinge.common.data.coil.TMDbImageSize
import com.example.vidbinge.common.data.models.movie.Movie

@Composable
private fun RatingPill(
    modifier: Modifier = Modifier,
    rating: Float = 4.8f
){
    Row(
        modifier
            .background(color = Color.White, shape = RoundedCornerShape(16.dp))
            .padding(horizontal = 8.dp, vertical = 4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically
    ){
        Icon(imageVector = Icons.Default.Star, contentDescription = null, tint = Color(red = 255, green = 165, blue = 0))
        Text(text = rating.toString(), color = Color.Black)
    }
}

@Composable
private fun TitleBackDrop(
    modifier: Modifier = Modifier,
    text: String = "Mufasa"
){
    Box(modifier.background(color = MaterialTheme.colorScheme.primary).padding(horizontal = 12.dp, vertical = 12.dp)) {
        Text(text, color = MaterialTheme.colorScheme.onPrimary, textAlign = TextAlign.Start, overflow = TextOverflow.Ellipsis, maxLines = 1)
    }
}

@Composable
fun MoviePortraitCard(
    modifier: Modifier = Modifier,
    movieItem: Movie,
){
    ConstraintLayout(modifier.size(width = 150.dp, height = 260.dp).clip(RoundedCornerShape(8.dp))) {
        val (imageRef, ratingPillRef, titleRef) = createRefs()
        AsyncImage(
            modifier = Modifier.constrainAs(imageRef){
                top.linkTo(parent.top)
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }.fillMaxSize(),
            model = movieItem.posterFullPath(TMDbImageSize.LARGE),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )

        RatingPill(
            modifier = Modifier.constrainAs(ratingPillRef){
                top.linkTo(parent.top, 12.dp)
                start.linkTo(parent.start, 12.dp)
            },
            rating = movieItem.voteAverage.toFloat()
        )

        TitleBackDrop(
            modifier = Modifier.constrainAs(titleRef){
                bottom.linkTo(parent.bottom)
                start.linkTo(parent.start)
                end.linkTo(parent.end)
            }.fillMaxWidth(),
            text = movieItem.title
        )
    }
}


@Preview
@Composable
fun MoviePortraitCardPreview(){

}