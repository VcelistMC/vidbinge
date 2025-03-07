package com.example.vidbinge.common.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.vidbinge.common.data.models.Pill
import java.util.Locale


data class PillBarTheme (
    val selectedPillBg: Color = Color.Gray,
    val selectedPillTextColor: Color = Color.Black,
    val unselectedPillBg: Color = Color.Transparent,
    val unselectedPillTextColor: Color = Color.Red,
)

@Composable
private fun Pill(
    modifier: Modifier = Modifier,
    pillBarTheme: PillBarTheme = PillBarTheme(),
    content: Pill,
    isSelected: Boolean
){
    if(isSelected){
        Text(
            modifier = modifier.background(
                color = pillBarTheme.selectedPillBg,
                shape = RoundedCornerShape(24.dp)
            ).padding(horizontal = 16.dp, vertical = 8.dp),
            text = content.text.uppercase(Locale.ROOT),
            color = pillBarTheme.selectedPillTextColor
        )
    }else {
        Text(
            modifier = modifier.background(
                color = pillBarTheme.unselectedPillBg,
                shape = RoundedCornerShape(24.dp)
            ).padding(horizontal = 16.dp, vertical = 8.dp),
            text = content.text.uppercase(Locale.ROOT),
            color = pillBarTheme.unselectedPillTextColor
        )
    }
}

@Composable
fun PillBar(
    modifier: Modifier = Modifier,
    choices: List<Pill>,
    selectedPill: Pill,
    onChoicePressed: (Pill) -> Unit,
    pillBarTheme: PillBarTheme = PillBarTheme()
){
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(choices){ choice ->
            Pill(
                modifier = Modifier.clickable {
                    onChoicePressed(choice)
                },
                content = choice,
                isSelected = choice == selectedPill,
                pillBarTheme = pillBarTheme
            )
        }
    }
}



@Preview
@Composable
fun PillBarPreview(){
    PillBar(
        modifier = Modifier,
        choices = Pill.mockList,
        onChoicePressed = {},
        selectedPill = Pill.mock3
    )
}