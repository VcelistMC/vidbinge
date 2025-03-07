package com.example.vidbinge.common.data.models


data class Pill(
    val id: Int,
    val text: String = ""
){
    companion object {
        val mock1 = Pill(1, "All")
        val mock2 = Pill(2, "TV")
        val mock3 = Pill(3, "Movies")
        val mock4 = Pill(4, "News")
        val mock5 = Pill(5, "Hubs")
        val mock6 = Pill(6, "you may also like")
        val mock7 = Pill(7, "EXTRAS")
        val mockList = listOf(mock1, mock2, mock3, mock4, mock5, mock6, mock7)
    }
}