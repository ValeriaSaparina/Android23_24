package com.example.homework3.models

import androidx.annotation.DrawableRes
import java.util.Date

data class NewsData(
    val id: Int,
    val title: String,
    val description: String,
    @DrawableRes val img: Int,
    var isFav: Boolean = false,
)
