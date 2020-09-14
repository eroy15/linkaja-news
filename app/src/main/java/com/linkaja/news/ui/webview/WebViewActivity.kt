package com.linkaja.news.ui.webview

import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.webkit.WebViewClient
import com.linkaja.news.R
import com.linkaja.news.ui.BaseActivity
import com.linkaja.news.util.Helper
import kotlinx.android.synthetic.main.activity_webview.*
import kotlinx.android.synthetic.main.fragment_empty_view.*
import kotlinx.android.synthetic.main.toolbar.*


class WebViewActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val url: String? = intent.getStringExtra("url")
        val title: String? = intent.getStringExtra("title")

        setContentView(R.layout.activity_webview)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = title
        webview.webViewClient = WebViewClient()
        webview.settings.javaScriptEnabled = true
        srlWebView.isEnabled = false
        if (Helper.isNetworkAvailable()) {
            llEmptyView.visibility = View.GONE
            webview.visibility = View.VISIBLE
            webview.loadUrl(url)
        } else {
            webview.visibility = View.GONE
            llEmptyView.visibility = View.VISIBLE
            tvTitle.text = getString(R.string.errorConnection)
            tvMessage.text = getString(R.string.errorRefresh)
            ivNoData.setImageResource(R.drawable.ic_offline_mode)
        }
        tvMessage.setOnClickListener {
            srlWebView.isRefreshing = true
            if (Helper.isNetworkAvailable()) {
                srlWebView.isRefreshing = false
                llEmptyView.visibility = View.GONE
                webview.visibility = View.VISIBLE
                webview.loadUrl(url)
            } else {
                srlWebView.isRefreshing = false
                webview.visibility = View.GONE
                llEmptyView.visibility = View.VISIBLE
                tvTitle.text = getString(R.string.errorConnection)
                tvMessage.text = getString(R.string.errorRefresh)
                ivNoData.setImageResource(R.drawable.ic_offline_mode)
            }
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK && webview.canGoBack()) {
            webview.goBack()
            return true
        }
        return super.onKeyDown(keyCode, event)
    }

}
