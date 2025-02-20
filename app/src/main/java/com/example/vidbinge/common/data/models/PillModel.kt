package com.example.vidbinge.common.data.models


data class PillModel(
    val id: Int,
    val text: String = ""
){
    companion object {
        val mock1 = PillModel(1, "All")
        val mock2 = PillModel(2, "TV")
        val mock3 = PillModel(3, "Movies")
        val mock4 = PillModel(4, "News")
        val mock5 = PillModel(5, "Hubs")
        val mock6 = PillModel(6, "you may also like")
        val mock7 = PillModel(7, "EXTRAS")
        val mockList = listOf(mock1, mock2, mock3, mock4, mock5, mock6, mock7)
    }
}