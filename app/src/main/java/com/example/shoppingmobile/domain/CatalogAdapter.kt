package com.example.shoppingmobile.domain

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppingmobile.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class CatalogAdapter : ListAdapter<Catalog, CatalogAdapter.CatalogViewHolder>(CatalogDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatalogViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.catalog_card, parent, false)
        return CatalogViewHolder(view)
    }

    override fun onBindViewHolder(holder: CatalogViewHolder, position: Int) {
        val catalog = getItem(position)
        holder.bind(catalog)
    }

    class CatalogViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(catalog: Catalog) {

            itemView.findViewById<TextView>(R.id.textCatalogId).text = catalog.id.toString()
            itemView.findViewById<TextView>(R.id.textCatalogName).text = catalog.name
            itemView.findViewById<TextView>(R.id.textCatalogDescription).text = catalog.description

            itemView.findViewById<Button>(R.id.btnView).setOnClickListener {
                dialogClick(itemView.context,catalog)
            }
        }

        fun dialogClick(context: Context, catalog: Catalog) {
            val dialogView = LayoutInflater.from(context).inflate(R.layout.catalog_dialog, null)

            val dialog = MaterialAlertDialogBuilder(context, R.style.Theme_ShoppingMobile)
                .setTitle("Are you absolutely sure?")
                .setMessage("This action cannot be undone. This will update the ${catalog.name} category.")
                .setView(dialogView)
                .setPositiveButton("OK") { dialog, which ->
                    // Handle button click
                }
                .setNegativeButton("Cancel") { dialog, which ->
                    // Handle button click
                }
                .create()

            dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)

            dialog.show()
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