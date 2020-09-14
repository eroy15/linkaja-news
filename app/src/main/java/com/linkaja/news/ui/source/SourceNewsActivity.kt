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
import com.linkaja.news.adapter.SourcesAdapter
import com.linkaja.news.api.DataWrapper
import com.linkaja.news.api.response.SourceListResponse
import com.linkaja.news.model.SourceNews
import com.linkaja.news.ui.BaseActivity
import kotlinx.android.synthetic.main.fragment_empty_view.*
import kotlinx.android.synthetic.main.fragment_empty_view.view.*
import kotlinx.android.synthetic.main.fragment_list.*
import kotlinx.android.synthetic.main.toolbar.*
import java.util.*


class SourceNewsActivity : BaseActivity() {

    private lateinit var sourceViewModel: SourceNewsViewModel
    private lateinit var sourceAdapter: SourcesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val category: String? = intent.getStringExtra("category")
        setContentView(R.layout.activity_sources)

        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

        srlList.setColorSchemeResources(R.color.colorPrimary, R.color.colorPrimaryDark, R.color.colorAccent)

        sourceViewModel = ViewModelProvider(this).get(SourceNewsViewModel::class.java)
        srlList.setOnRefreshListener {
            sourceViewModel.getListSource(category)?.observe(this, sourceListObserver)
        }

        sourceAdapter = SourcesAdapter(ArrayList(), sourceItemListener)
        val dividerVerticalDecoration = DividerItemDecoration(rvList.context, DividerItemDecoration.VERTICAL)
        rvList.addItemDecoration(dividerVerticalDecoration)
        rvList.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
        rvList.adapter = sourceAdapter

        sourceViewModel.getListSource(category)?.observe(this, sourceListObserver)
    }

    private val sourceListObserver = Observer<DataWrapper<SourceListResponse>> {
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
                if (it.data!!.sources!!.isEmpty()) {
                    llEmptyView.visibility = View.VISIBLE
                    srlList.visibility = View.VISIBLE
                    tvMessage.text = "Sumber Berita Kosong. Coba lagi."
                }else {
                    sourceAdapter.sourceList = it.data!!.sources
                    sourceAdapter.filter.filter("")
                    rvList.visibility = View.VISIBLE
                    llEmptyView.visibility = View.GONE
                }
            }
        }
    }

    private var sourceItemListener:(SourceNews) -> Unit = {
        val source = Intent(this, ArticleActivity::class.java)
        source.putExtra("source", it.id)
        startActivity(source)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)

        val searchItem: MenuItem? = menu?.findItem(R.id.action_search)
        val searchView: SearchView? = searchItem?.actionView as SearchView
        searchView!!.queryHint = "Cari Sumber Berita ..."

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(s: String): Boolean {
                sourceAdapter.filter.filter(s)
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