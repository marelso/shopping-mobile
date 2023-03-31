package com.example.shoppingmobile.domain

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppingmobile.R

class CatalogUserAdapter : ListAdapter<Catalog, CatalogUserAdapter.CatalogViewHolder>(CatalogDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatalogViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.catalog_card_user, parent, false)
        return CatalogViewHolder(view)
    }

    override fun onBindViewHolder(holder: CatalogViewHolder, position: Int) {
        val catalog = getItem(position)
        holder.bind(catalog)
    }

    class CatalogViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(catalog: Catalog) {
            itemView.findViewById<TextView>(R.id.catalogNameTextView).text = catalog.name
            itemView.findViewById<TextView>(R.id.catalogDescriptionTextView).text = catalog.description
        }
    }

    class CatalogDiffCallback : DiffUtil.ItemCallback<Catalog>() {
        override fun areItemsTheSame(oldItem: Catalog, newItem: Catalog): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Catalog, newItem: Catalog): Boolean {
            return oldItem == newItem
        }
    }
}