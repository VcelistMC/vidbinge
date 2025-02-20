package com.example.vidbinge.common.utils

import android.graphics.ColorSpace.Rgb
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.colorspace.ColorSpace

fun getRelativeLuminance(color: Color): Float {
    val constrainedRed = color.red
    val constrainedGreen = color.green
    val constrainedBlue = color.blue

    val relativeLuminance = 0.2126 * constrainedRed + 0.7152 * constrainedGreen + 0.0722 * constrainedBlue

    return relativeLuminance.toFloat()
}

fun main(){
    print(getRelativeLuminance(Color(0xFFf5e7e8)))
}