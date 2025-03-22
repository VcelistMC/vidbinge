package com.example.vidbinge.details.ui.composables

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SquareButton(
    modifier: Modifier = Modifier,
    icon: @Composable () -> Unit,
    text: String,
    onClick: () -> Unit,
    color: Color
) {
    val mutableInteractionSource = remember { MutableInteractionSource() }
    Column(
        modifier
            .size(100.dp)
            .border(1.dp, color = color, shape = RoundedCornerShape(8.dp))
            .padding(12.dp)
            .clickable(
                onClick = onClick,
                indication = rememberRipple(color = color),
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
            color = color,
            textAlign = TextAlign.Center
        )
    }
}
