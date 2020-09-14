package com.linkaja.news.model

import com.google.gson.annotations.SerializedName

data class ArticleSource(
    @SerializedName("id") val id: String,
    @SerializedName("name") val title: String
)
