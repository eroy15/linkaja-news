package com.linkaja.news.api.response

import com.google.gson.annotations.SerializedName
import com.linkaja.news.model.SourceNews

data class SourceListResponse(
    @SerializedName("status")
    val status: String,
    @SerializedName("sources")
    val sources: List<SourceNews>
)