package com.example.vidbinge.common.ext

import androidx.compose.ui.graphics.Color
import com.example.vidbinge.common.data.models.RelativeLuma

val Color.relativeLuma: RelativeLuma
    get() {
        val constrainedRed = red
        val constrainedGreen = green
        val constrainedBlue = blue

        val relativeLuminance = 0.2126 * constrainedRed + 0.7152 * constrainedGreen + 0.0722 * constrainedBlue

        val floatRelativeLuma = relativeLuminance.toFloat()
        return if(floatRelativeLuma > 0.5f) RelativeLuma.BRIGHT else RelativeLuma.DARK
    }