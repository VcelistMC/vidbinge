package com.example.vidbinge.common.ui.navtypes

import android.os.Build
import android.os.Bundle
import androidx.navigation.NavType
import com.example.vidbinge.common.data.models.movie.Movie
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

val MovieType = object : NavType<Movie>(
    isNullableAllowed = false
) {
    override fun get(bundle: Bundle, key: String): Movie? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            bundle.getParcelable(key, Movie::class.java)
        } else {
            @Suppress("DEPRECATION")
            bundle.getParcelable(key)
        }
    }

    override fun parseValue(value: String): Movie {
        return Json.decodeFromString<Movie>(value)
    }

    override fun serializeAsValue(value: Movie): String {
        return Json.encodeToString(value)
    }

    override fun put(bundle: Bundle, key: String, value: Movie) {
        bundle.putParcelable(key, value)
    }
}