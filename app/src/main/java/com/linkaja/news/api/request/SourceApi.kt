package com.linkaja.news.api.request

import com.linkaja.news.api.response.SourceListResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface SourceApi {

    @GET("sources")
    fun getListSource(@Query("apiKey") apiKey: String,
                              @Query("category") category: String?,
                              @Query("language") language: String): Call<SourceListResponse>

}