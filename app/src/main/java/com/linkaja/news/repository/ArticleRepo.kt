package com.linkaja.news.repository

import androidx.lifecycle.MutableLiveData
import com.linkaja.news.LinkAjaNewsApplication
import com.linkaja.news.api.DataWrapper
import com.linkaja.news.api.ResponseHandler
import com.linkaja.news.api.request.ArticleApi
import com.linkaja.news.api.response.ArticleListResponse
import com.linkaja.news.data.Constant

class ArticleRepo {
    private var articleApi: ArticleApi? = null
    private val ApiKey: String = Constant.API_KEY

    init {
        articleApi = LinkAjaNewsApplication.getInstance().retrofit.create(ArticleApi::class.java)
    }

    fun getListArticle(source: String?): MutableLiveData<DataWrapper<ArticleListResponse>> {
        val data = MutableLiveData<DataWrapper<ArticleListResponse>>()
        data.value = DataWrapper.loading(null)
        articleApi?.getListArticle(ApiKey, source)!!.enqueue(ResponseHandler(data))
        return data
    }
}