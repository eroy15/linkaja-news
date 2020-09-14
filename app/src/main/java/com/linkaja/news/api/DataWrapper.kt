package com.linkaja.news.api

class DataWrapper<T> private constructor(val status: Status, val data: T?, val exception: Exception?) {
    enum class Status {
        SUCCESS, ERROR, LOADING
    }

    companion object {
        fun <T> success(data: T?): DataWrapper<T> {
            return DataWrapper(Status.SUCCESS, data, null)
        }

        fun <T> error(exception: Exception?): DataWrapper<T> {
            return DataWrapper(Status.ERROR, null, exception)
        }

        fun <T> loading(data: T?): DataWrapper<T> {
            return DataWrapper(Status.LOADING, data, null)
        }
    }
}