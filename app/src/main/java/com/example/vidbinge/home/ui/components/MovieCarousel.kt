package com.example.vidbinge.home.ui.components

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.vidbinge.common.data.coil.TMDbImageSize
import com.example.vidbinge.common.data.models.movie.Movie
import com.example.vidbinge.common.ext.noRippleClickable
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.abs



@Composable
fun MovieCarousel(
    modifier: Modifier = Modifier,
    movieItems: List<Movie>,
    onCardPressed: (Movie) -> Unit
) {
    val lazyListState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    val centerItemIndex by remember {
        derivedStateOf {
            val layoutInfo = lazyListState.layoutInfo
            val visibleItems = layoutInfo.visibleItemsInfo
            val viewportCenter = (layoutInfo.viewportStartOffset + layoutInfo.viewportEndOffset) / 2

            visibleItems.minByOrNull { abs((it.offset + it.size / 2) - viewportCenter) }?.index ?: 0
        }
    }

    LaunchedEffect(centerItemIndex) {
        delay(5000)
        val nextItemToScrollTo = if(centerItemIndex + 1 >= movieItems.size) 0 else centerItemIndex+1
        coroutineScope.launch {
            lazyListState.animateScrollToItem(index = nextItemToScrollTo)
        }
    }



    Column(modifier, verticalArrangement = Arrangement.Center) {
        LazyRow(
            modifier = Modifier,
            horizontalArrangement = Arrangement.spacedBy(14.dp),
            state = lazyListState,
            flingBehavior = rememberSnapFlingBehavior(lazyListState),
        ) {
            items(movieItems, key = { it.id }) { movie ->
                val screenWidth = LocalConfiguration.current.screenWidthDp.dp
                AsyncImage(
                    modifier = Modifier
                        .size(width = screenWidth, height = 200.dp)
                        .noRippleClickable { onCardPressed(movie) },
                    model = movie.backdropFullUrl(TMDbImageSize.LARGE),
                    contentScale = ContentScale.Crop,
                    contentDescription = null,
                    alignment = Alignment.TopStart
                )
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterHorizontally),
            verticalAlignment = Alignment.CenterVertically
        ) {
            repeat(movieItems.size) {
                if (it == centerItemIndex) {
                    Text("•", color = MaterialTheme.colorScheme.onPrimary, fontSize = 35.sp)
                } else {
                    Text("•", color = MaterialTheme.colorScheme.inversePrimary, fontSize = 30.sp)
                }

            }
        }
        AnimatedContent(
            targetState = centerItemIndex
        ) {
            Text(
                modifier = Modifier.padding(horizontal = 12.dp),
                text = movieItems[it].title,
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
        AnimatedContent(
            targetState = centerItemIndex,
        )  {
            Text(
                modifier = Modifier.padding(horizontal = 12.dp),
                text = movieItems[it].overview,
                fontSize = 15.sp,
                color = MaterialTheme.colorScheme.onPrimary,
                maxLines = 4,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}
