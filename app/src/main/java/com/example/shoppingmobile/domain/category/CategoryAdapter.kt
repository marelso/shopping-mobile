package com.example.shoppingmobile.domain.category

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppingmobile.R
import com.example.shoppingmobile.basics.TextAdapter
import com.example.shoppingmobile.domain.offer.Offer
import com.example.shoppingmobile.domain.offer.OfferAdapter
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

class CategoryAdapter() : RecyclerView.Adapter<CategoryAdapter.ViewHolder>() {
    private var dataList: MutableList<Category> = mutableListOf()
    private var onClickListener: View.OnClickListener? = null

    fun setData(data: MutableList<Category>) {
        dataList.clear()
        dataList.addAll(data)
        notifyDataSetChanged()
    }

    fun setClickListener(event: View.OnClickListener) {
        onClickListener = event
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var id: TextView = itemView.findViewById(R.id.textCategoryId)
        var name: TextView = itemView.findViewById(R.id.textCategoryName)
        var priority: TextView = itemView.findViewById(R.id.textCategoryPriority)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.category_card, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val category: Category = dataList[position]
        holder.id.text = category.id.toString()
        holder.name.text = category.name
        holder.priority.text = category.priority.toString()

        holder.itemView.setOnClickListener {
            onClickListener?.onClick(it)
        }
    }
}