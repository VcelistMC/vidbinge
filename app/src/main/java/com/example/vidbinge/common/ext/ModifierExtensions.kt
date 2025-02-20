package com.example.vidbinge.common.ext

import android.os.Build
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun Modifier.noRippleClickable(onClick: () -> Unit): Modifier{
    val mutableInteraction = remember { MutableInteractionSource() }
    return this.clickable(
        onClick = onClick,
        interactionSource = mutableInteraction,
        indication = null
    )
}

@Composable
fun Modifier.debugBorder(color: Color = Color.Red): Modifier {
    return this.border(width = 1.dp, color = color)
}