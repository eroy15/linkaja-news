package com.linkaja.news.model

import com.google.gson.annotations.SerializedName

data class Category(
    @SerializedName("icon")
    val icon: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("id")
    val id: String
)