package com.linkaja.news.api

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.linkaja.news.util.Helper
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ResponseHandler<T>(var data: MutableLiveData<DataWrapper<T>>) : Callback<T> {

    override fun onFailure(call: Call<T>?, t: Throwable?) {
        var exception = java.lang.Exception(t!!.message)
        if (!Helper.isNetworkAvailable())
            exception = java.lang.Exception("Terjadi kesalahan koneksi.\nPeriksa jaringan internet Anda atau coba beberapa saat lagi.")
        t.printStackTrace()
        data.value = DataWrapper.error(exception)
    }

    override fun onResponse(call: Call<T>?, response: Response<T>?) {
        Log.d("Response Handler", "response successful?: " + response!!.isSuccessful)
        if (response.isSuccessful) {
            data.value = DataWrapper.success(response.body())
        } else {
            try {
                val message = java.lang.Exception(JSONObject(response.errorBody()!!.string()).getString("message"))
                data.value = DataWrapper.error(message)
            } catch (err: Exception) {
                data.value = DataWrapper.error(err)
            }
        }
    }
}