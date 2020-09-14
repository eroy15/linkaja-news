package com.linkaja.news.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.linkaja.news.R
import com.linkaja.news.model.Article
import com.linkaja.news.util.Helper
import kotlinx.android.synthetic.main.item_article.view.*

class ArticleAdapter(var articleList: List<Article>, var articleItemListener: (Article) -> Unit) : RecyclerView.Adapter<ArticleAdapter.Holder>() {

    //var articleListFilter: List<Article> = articleList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_article, parent, false)
        return Holder(itemView)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val article = articleList[position]
        holder.bindHolder(article)
    }

    override fun getItemCount(): Int {
        return articleList.size
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindHolder(article: Article) = with(itemView) {
            Glide.with(iv_thumbnail.context).load(article.urlToImage).into(iv_thumbnail)
            txt_title.text = article.title
            txt_desc.text = article.description
            txt_date.text = Helper.getStringFormatDate(article.publishedAt)
            itemView.setOnClickListener {
                articleItemListener(article)
            }
        }
    }

//    override fun getFilter(): Filter {
//        return object : Filter() {
//            override fun performFiltering(constraint: CharSequence?): FilterResults {
//                val charSearch = constraint.toString()
//                if (charSearch.isNullOrEmpty()) {
//                    articleListFilter = articleList
//                } else {
//                    articleListFilter = articleList.filter { it.title.contains(charSearch) }
//                }
//                val filterResults = FilterResults()
//                filterResults.values = articleListFilter
//
//                return filterResults
//            }
//
//            @Suppress("UNCHECKED_CAST")
//            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
//                articleListFilter = results?.values as List<Article>
//                notifyDataSetChanged()
//            }
//
//        }
//    }
}
