package com.linkaja.news.repository

import androidx.lifecycle.MutableLiveData
import com.linkaja.news.LinkAjaNewsApplication
import com.linkaja.news.api.DataWrapper
import com.linkaja.news.api.ResponseHandler
import com.linkaja.news.api.request.SourceApi
import com.linkaja.news.api.response.SourceListResponse
import com.linkaja.news.data.Constant

class SourceRepo {
    private var sourceApi: SourceApi? = null
    private val ApiKey: String = Constant.API_KEY

    init {
        sourceApi = LinkAjaNewsApplication.getInstance().retrofit.create(SourceApi::class.java)
    }

    fun getListSource(category: String?, language: String): MutableLiveData<DataWrapper<SourceListResponse>> {
        val data = MutableLiveData<DataWrapper<SourceListResponse>>()
        data.value = DataWrapper.loading(null)
        sourceApi?.getListSource(ApiKey, category, language)!!.enqueue(ResponseHandler(data))
        return data
    }
}