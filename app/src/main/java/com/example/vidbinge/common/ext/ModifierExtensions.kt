package com.example.vidbinge.common.ext

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

@Composable
fun Modifier.noRippleClickable(onClick: () -> Unit): Modifier{
    val mutableInteraction = remember { MutableInteractionSource() }
    return this.clickable(
        onClick = onClick,
        interactionSource = mutableInteraction,
        indication = null
    )

}