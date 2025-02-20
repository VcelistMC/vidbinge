package com.example.vidbinge.home.ui.components

import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.gestures.snapping.rememberSnapFlingBehavior
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.AsyncImage
import com.example.vidbinge.common.ext.noRippleClickable
import com.example.vidbinge.home.data.model.MovieCarouselItem
import kotlin.math.abs


@Composable
fun MovieCarousel(
    modifier: Modifier = Modifier,
    movieItems: List<MovieCarouselItem>,
    onCardPressed: (MovieCarouselItem) -> Unit
) {
    val lazyListState = rememberLazyListState()

    val centerItemIndex by remember {
        derivedStateOf {
            val layoutInfo = lazyListState.layoutInfo
            val visibleItems = layoutInfo.visibleItemsInfo
            val viewportCenter = (layoutInfo.viewportStartOffset + layoutInfo.viewportEndOffset) / 2

            visibleItems.minByOrNull { abs((it.offset + it.size / 2) - viewportCenter) }?.index ?: 0
        }
    }



    Column(modifier, verticalArrangement = Arrangement.Center) {
        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(14.dp),
            state = lazyListState,
            flingBehavior = rememberSnapFlingBehavior(lazyListState)
        )
        {
            items(movieItems, key = { it.id }) { card ->
                AsyncImage(
                    modifier = Modifier
                        .size(width = 320.dp, height = 180.dp)
                        .clip(
                            RoundedCornerShape(8.dp)
                        )
                        .noRippleClickable { onCardPressed(card) },
                    model = card.imgUrl,
                    contentDescription = null,
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
                    Text("•", color = Color.Black, fontSize = 35.sp)
                } else {
                    Text("•", color = Color.Gray, fontSize = 30.sp)
                }

            }
        }
    }

}
