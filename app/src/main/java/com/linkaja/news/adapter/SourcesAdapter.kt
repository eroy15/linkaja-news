package com.linkaja.news.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.linkaja.news.R
import com.linkaja.news.model.SourceNews
import kotlinx.android.synthetic.main.item_source.view.*

class SourcesAdapter(var sourceList: List<SourceNews>, var sourceItemListener: (SourceNews) -> Unit) : RecyclerView.Adapter<SourcesAdapter.Holder>(), Filterable {

    var sourceListFilter: List<SourceNews> = sourceList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_source, parent, false)
        return Holder(itemView)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        if(sourceListFilter.isEmpty()){
            sourceListFilter = sourceList
        }

        val source = sourceListFilter[position]
        holder.bindHolder(source)
    }

    override fun getItemCount(): Int {
        return sourceListFilter.size
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindHolder(source: SourceNews) = with(itemView) {
            txt_source_name.text = source.name
            txt_source_desc.text = source.description
            txt_source_category.text = source.category
            itemView.setOnClickListener {
                sourceItemListener(source)
            }
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    sourceListFilter = sourceList
                } else {
                    sourceListFilter = sourceList.filter { it.name.contains(charSearch) }
                }
                val filterResults = FilterResults()
                filterResults.values = sourceListFilter

                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                sourceListFilter = results?.values as List<SourceNews>
                notifyDataSetChanged()
            }

        }
    }
}
