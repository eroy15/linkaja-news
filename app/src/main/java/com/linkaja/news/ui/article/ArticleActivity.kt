package com.linkaja.news.ui.source

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.linkaja.news.R
import com.linkaja.news.adapter.ArticleAdapter
import com.linkaja.news.api.DataWrapper
import com.linkaja.news.api.response.ArticleListResponse
import com.linkaja.news.model.Article
import com.linkaja.news.ui.BaseActivity
import com.linkaja.news.ui.webview.WebViewActivity
import kotlinx.android.synthetic.main.fragment_empty_view.*
import kotlinx.android.synthetic.main.fragment_empty_view.view.*
import kotlinx.android.synthetic.main.fragment_list.*
import kotlinx.android.synthetic.main.toolbar.*
import java.util.ArrayList

class ArticleActivity : BaseActivity() {

    private lateinit var articleViewModel: ArticleViewModel
    private lateinit var articleAdapter: ArticleAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val source: String? = intent.getStringExtra("source")
        setContentView(R.layout.activity_article)

        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        srlList.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimaryDark, R.color.colorAccent)

        articleViewModel = ViewModelProvider(this).get(ArticleViewModel::class.java)
        srlList.setOnRefreshListener {
            articleViewModel.getListArticle(source)?.observe(this, articleListObserver)
        }

        articleAdapter = ArticleAdapter(ArrayList(), articleItemListener)
        val dividerVerticalDecoration = DividerItemDecoration(rvList.context, DividerItemDecoration.VERTICAL)
        rvList.addItemDecoration(dividerVerticalDecoration)
        rvList.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rvList.adapter = articleAdapter

        articleViewModel.getListArticle(source)?.observe(this, articleListObserver)
    }

    private val articleListObserver = Observer<DataWrapper<ArticleListResponse>> {
        when (it.status) {
            DataWrapper.Status.ERROR -> {
                srlList.isRefreshing = false
                rvList.visibility = View.GONE
                llEmptyView.visibility = View.VISIBLE
                val tvErrorTitle = llEmptyView.tvMessage
                tvErrorTitle.text = it.exception?.message
            }
            DataWrapper.Status.LOADING -> {
                llEmptyView.visibility = View.GONE
                srlList.isRefreshing = true
            }
            DataWrapper.Status.SUCCESS -> {
                srlList.isRefreshing = false
                if (it.data!!.articles!!.isEmpty()) {
                    llEmptyView.visibility = View.VISIBLE
                    srlList.visibility = View.VISIBLE
                    tvMessage.text = "Artikel Kosong. Coba lagi."
                }else {
                    articleAdapter.articleList = it.data!!.articles
                    articleAdapter.notifyDataSetChanged()
                    rvList.visibility = View.VISIBLE
                    llEmptyView.visibility = View.GONE
                }
            }
        }
    }

    private var articleItemListener:(Article) -> Unit = {
        val detailArticle = Intent(this, WebViewActivity::class.java)
        detailArticle.putExtra("url", it.url)
        detailArticle.putExtra("title", it.title)
        startActivity(detailArticle)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)

        val searchItem: MenuItem? = menu?.findItem(R.id.action_search)
        val searchView: SearchView? = searchItem?.actionView as SearchView
        searchView!!.queryHint = "Cari Artikel ..."

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(s: String): Boolean {
                //articleAdapter.filter.filter(s)
                return false
            }
        })

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.action_search) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}