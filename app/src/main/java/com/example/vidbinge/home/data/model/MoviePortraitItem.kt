package com.example.vidbinge.home.data.model

data class MoviePortraitItem(
    val rating: Float,
    val imgUrl: String,
    val name: String
){
    companion object {
        val mufasa = MoviePortraitItem(
            rating = 7.4f,
            imgUrl = "https://image.tmdb.org/t/p/original/9bXHaLlsFYpJUutg4E6WXAjaxDi.jpg",
            name = "Mufasa: The Lion King"
        )

        val moana2 = MoviePortraitItem(
            rating = 7.2f,
            imgUrl = "https://image.tmdb.org/t/p/original/aLVkiINlIeCkcZIzb7XHzPYgO6L.jpg",
            name = "Moana 2"
        )
    }
}
