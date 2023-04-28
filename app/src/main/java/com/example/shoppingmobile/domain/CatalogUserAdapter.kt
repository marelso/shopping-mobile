package com.example.shoppingmobile.domain

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppingmobile.R

class CatalogUserAdapter(private val onClickListener: View.OnClickListener) : RecyclerView.Adapter<CatalogUserAdapter.ViewHolder>() {

    private var dataList: MutableList<Catalog> = mutableListOf()

    fun setData(data: MutableList<Catalog>) {
        dataList.clear()
        dataList.addAll(data)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.catalog_card_user, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val catalog: Catalog = dataList[position]
        holder.textId.text = catalog.id.toString()
        holder.textName.text = catalog.name
        holder.textDescription.text = catalog.description

        holder.itemView.setOnClickListener {
            onClickListener.onClick(it)
        }
    }

    override fun getItemCount() = dataList.size

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textId: TextView = view.findViewById(R.id.catalogIdTextView)
        val textName: TextView = view.findViewById(R.id.catalogNameTextView)
        val textDescription: TextView = view.findViewById(R.id.catalogDescriptionTextView)
    }
}