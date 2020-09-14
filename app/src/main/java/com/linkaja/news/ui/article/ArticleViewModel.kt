package com.linkaja.news.ui.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.linkaja.news.api.DataWrapper
import com.linkaja.news.api.response.ArticleListResponse
import com.linkaja.news.repository.ArticleRepo

class ArticleViewModel : ViewModel() {

    private val articleRepo = ArticleRepo()

    fun getListArticle(source: String?): LiveData<DataWrapper<ArticleListResponse>>? {
        return articleRepo.getListArticle(source);
    }
}