package com.example.vidbinge.details.ui.composables

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.vidbinge.common.data.models.Genre

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun GenresFlowChips(
    modifier: Modifier = Modifier,
    genres: List<Genre>,
    onChipClicked: (Genre) -> Unit,
    color: Color
) {
    FlowRow(modifier) {
        genres.forEach { genre ->
            SuggestionChip(
                onClick = { onChipClicked(genre) },
                label = { Text(genre.genre) },
                colors = SuggestionChipDefaults.suggestionChipColors(
                    labelColor = color,
                    containerColor = Color.Transparent
                )
            )
            Spacer(Modifier.width(6.dp))
        }
    }
}