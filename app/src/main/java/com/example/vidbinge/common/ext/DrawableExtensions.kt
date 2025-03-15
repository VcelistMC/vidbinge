package com.example.vidbinge.common.ext

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.ui.graphics.Color
import androidx.core.graphics.drawable.toBitmap
import androidx.palette.graphics.Palette

@RequiresApi(Build.VERSION_CODES.O)
fun Drawable.getDominantColor(): Color {
    val palette = Palette.Builder(
        toBitmap().copy(Bitmap.Config.RGBA_F16, true)
    )
        .clearFilters()
        .maximumColorCount(8)
        .generate()

    val hsl = palette.dominantSwatch?.hsl
    return if(hsl != null){
        Color.hsl(hsl.first(), hsl[1], hsl[2], 1f)
    }else{
        Color.White
    }
}