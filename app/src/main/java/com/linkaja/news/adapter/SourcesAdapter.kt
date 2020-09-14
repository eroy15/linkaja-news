package com.linkaja.news.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.linkaja.news.R
import com.linkaja.news.model.SourceNews
import kotlinx.android.synthetic.main.item_source.view.*

class SourcesAdapter(var sourceList: List<SourceNews>, var sourceItemListener: (SourceNews) -> Unit) : RecyclerView.Adapter<SourcesAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_source, parent, false)
        return Holder(itemView)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val source = sourceList[position]
        holder.bindHolder(source)
    }

    override fun getItemCount(): Int {
        return sourceList.size
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindHolder(source: SourceNews) = with(itemView) {
            //Glide.with(iv_thumbnail.context).load(source.icon).into(iv_thumbnail)
            txt_source_name.text = source.name
            txt_source_desc.text = source.description
            txt_source_category.text = source.category
            itemView.setOnClickListener {
                sourceItemListener(source)
            }
        }
    }
}
