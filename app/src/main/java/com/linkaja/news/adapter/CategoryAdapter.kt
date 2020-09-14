package com.linkaja.news.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.linkaja.news.R
import com.linkaja.news.model.Category
import kotlinx.android.synthetic.main.item_menu.view.*

class CategoryAdapter(var categoryList: ArrayList<Category>, var categoryItemListener: (Category) -> Unit) : RecyclerView.Adapter<CategoryAdapter.Holder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_menu, parent, false)
        return Holder(itemView)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val category = categoryList[position]
        holder.bindHolder(category)
    }

    override fun getItemCount(): Int {
        return categoryList.size
    }

    inner class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindHolder(category: Category) = with(itemView) {
            Glide.with(iv_thumbnail.context).load(category.icon).into(iv_thumbnail)
            txt_title.text = category.name
            card_view.setOnClickListener {
                categoryItemListener(category)
            }

        }
    }
}
