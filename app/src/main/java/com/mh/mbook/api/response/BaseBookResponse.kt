package com.mh.mbook.api.response

data class BaseBookResponse(
    val topSale: List<BookResponse>,
    val topRating: List<BookResponse>,
    val topNew: List<BookResponse>,
    val topComic: List<BookResponse>,
)