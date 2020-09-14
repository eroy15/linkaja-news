package com.linkaja.news.api.request

import com.linkaja.news.api.response.ArticleListResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ArticleApi {

    @GET("top-headlines")
    fun getListArticle(@Query("apiKey") apiKey: String,
                      @Query("sources") sources: String?): Call<ArticleListResponse>

    @GET("top-headlines")
    fun getListArticleSearch(@Query("apiKey") apiKey: String,
                             @Query("sources") sources: String?,
                             @Query("q") q: String?): Call<ArticleListResponse>

}