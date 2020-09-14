package com.linkaja.news.ui.source

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.linkaja.news.api.DataWrapper
import com.linkaja.news.api.response.SourceListResponse
import com.linkaja.news.repository.SourceRepo

class SourceNewsViewModel : ViewModel() {

    private val sourceRepo = SourceRepo()

    fun getListSource(category: String?): LiveData<DataWrapper<SourceListResponse>>? {
        return sourceRepo.getListSource(category, "en");
    }
}