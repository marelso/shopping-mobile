package com.example.shoppingmobile.domain

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppingmobile.R
import com.google.android.material.textview.MaterialTextView

class CatalogsAdapter(private val catalogs: List<Catalog>) : RecyclerView.Adapter<CatalogsAdapter.CatalogViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatalogViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.catalog_card, parent, false)
        return CatalogViewHolder(view)
    }

    override fun onBindViewHolder(holder: CatalogViewHolder, position: Int) {
        val catalog = catalogs[position]
        holder.bind(catalog)
    }

    override fun getItemCount(): Int {
        return catalogs.size
    }

    class CatalogViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val catalogNameTextView: TextView = itemView.findViewById(R.id.catalogNameTextView)
        private val catalogDescriptionTextView: TextView = itemView.findViewById(R.id.catalogDescriptionTextView)

        fun bind(catalog: Catalog) {
            catalogNameTextView.text = catalog.name
            catalogDescriptionTextView.text = catalog.description
        }
    }
}