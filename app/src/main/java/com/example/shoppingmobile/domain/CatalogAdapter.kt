package com.example.shoppingmobile.domain

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppingmobile.R
import com.google.android.material.card.MaterialCardView
import com.google.android.material.textview.MaterialTextView

class CatalogAdapter(private val catalogs: List<Catalog>) :
    RecyclerView.Adapter<CatalogAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.catalog_card, parent, false) as MaterialCardView
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(catalogs[position])
    }

    override fun getItemCount(): Int = catalogs.size

    class ViewHolder(itemView: MaterialCardView) : RecyclerView.ViewHolder(itemView) {
        private val nameView: MaterialTextView = itemView.findViewById(R.id.catalog_name)
        private val descriptionView: MaterialTextView =
            itemView.findViewById(R.id.catalog_description)

        fun bind(catalog: Catalog) {
            nameView.text = catalog.name
            descriptionView.text = catalog.description
        }
    }
}