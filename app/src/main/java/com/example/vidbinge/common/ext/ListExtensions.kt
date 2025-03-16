package com.example.vidbinge.common.ext

import kotlin.math.min

fun<T> List<T>.takeAtMost(n: Int): List<T> {
    val maxSize = min(n, size)
    return if (maxSize == size) this else subList(0, maxSize)
}
