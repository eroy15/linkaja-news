package com.linkaja.news.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.linkaja.news.R
import com.linkaja.news.adapter.CategoryAdapter
import com.linkaja.news.model.Category
import com.linkaja.news.ui.source.SourceNewsActivity
import com.linkaja.news.ui.webview.WebViewActivity
import kotlinx.android.synthetic.main.content_category.*
import kotlinx.android.synthetic.main.content_headline.*
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {

    private lateinit var categoryAdapter: CategoryAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        nsvHome.smoothScrollTo(0, 0)
        rvContent.isNestedScrollingEnabled = false

        val listCategory: List<Category> = listOf(
            Category(R.drawable.ic_bussiness, "Business", "business"),
            Category(R.drawable.ic_entertainment, "Entertainment", "entertainment"),
            Category(R.drawable.ic_health, "Health", "health"),
            Category(R.drawable.ic_general, "General", "general"),
            Category(R.drawable.ic_sport, "Sport", "sport"),
            Category(R.drawable.ic_tech, "Technology", "technology")
        )

        categoryAdapter = CategoryAdapter(listCategory, categoryItemListener)
        rvContent.layoutManager = GridLayoutManager(activity, 3)
        rvContent.adapter = categoryAdapter

        // headlineMock
        val urlImage: String = "https://ichef.bbci.co.uk/news/1024/branded_news/10370/production/_114361466_5ac41654-b5a0-4b01-acff-2c6facee0195.jpg"
        val title: String = "TikTok rejects Microsoft bid at eleventh hour"
        val url: String = "http://www.bbc.co.uk/news/business-54143025"

        Glide.with(iv_thumbnail.context).load(urlImage).into(iv_thumbnail)
        cv_headline.setOnClickListener {
            showWebView(url, title)
        }

    }

    private var categoryItemListener: (Category) -> Unit = {
        val categorySource = Intent(activity, SourceNewsActivity::class.java)
        categorySource.putExtra("category", it.id)
        startActivity(categorySource)
    }

    private fun showWebView(url: String, title: String){
        val webView = Intent(activity, WebViewActivity::class.java)
        webView.putExtra("url", url)
        webView.putExtra("title", title)
        startActivity(webView)
    }


}
