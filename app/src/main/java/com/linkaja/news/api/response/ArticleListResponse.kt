package com.linkaja.news.api.response

import com.google.gson.annotations.SerializedName
import com.linkaja.news.model.Article

data class ArticleListResponse(
    @SerializedName("status")
    val status: String,
    @SerializedName("totalResults")
    val totalResults: Int,
    @SerializedName("articles")
    val articles: List<Article>
)