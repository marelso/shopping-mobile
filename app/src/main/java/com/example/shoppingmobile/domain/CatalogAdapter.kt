package com.example.shoppingmobile.domain

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppingmobile.R
import com.example.shoppingmobile.service.ApiClient
import com.example.shoppingmobile.service.CatalogService
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.w3c.dom.Text


class CatalogAdapter : ListAdapter<Catalog, CatalogAdapter.CatalogViewHolder>(CatalogDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatalogViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.catalog_card, parent, false)
        return CatalogViewHolder(view)
    }

    override fun onBindViewHolder(holder: CatalogViewHolder, position: Int) {
        val catalog = getItem(position)
        holder.bind(catalog)
    }

    inner class CatalogViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var service = ApiClient.createService(CatalogService::class.java)

        var textCatalogId: TextView = itemView.findViewById<TextView>(R.id.textCatalogId)
        var textCatalogName: TextView = itemView.findViewById<TextView>(R.id.textCatalogName)
        var textCatalogDescription: TextView = itemView.findViewById<TextView>(R.id.textCatalogDescription)

        fun bind(catalog: Catalog) {
            textCatalogId.text = catalog.id.toString()
            textCatalogName.text = catalog.name
            textCatalogDescription.text = catalog.description

            itemView.findViewById<Button>(R.id.btnView).setOnClickListener {
                viewDetailsClick(itemView.context,catalog)
            }

            itemView.findViewById<ImageButton>(R.id.btnDelete).setOnClickListener {
                CoroutineScope(Dispatchers.Main).launch {
                    val response = service.delete(catalog.id)

                    withContext(Dispatchers.Main) {
                        if (response.isSuccessful) {
                            val position = currentList.indexOf(catalog)
                            if (position != -1) {
                                val newList = currentList.toMutableList()
                                newList.removeAt(position)
                                submitList(newList)
                            }
                            println("OK")
                        } else {
                            println("error")
                        }
                    }
                }
            }
        }

        fun viewDetailsClick(context: Context, catalog: Catalog) {
            val dialogView = LayoutInflater.from(context).inflate(R.layout.catalog_dialog, null)

            var textId: TextView = dialogView.findViewById<TextView>(R.id.textCatalogId)
            var textUpdatedName: EditText = dialogView.findViewById<EditText>(R.id.textCatalogName)
            var textUpdatedDescription: EditText = dialogView.findViewById<EditText>(R.id.textCatalogDescription)

            textId.setText(catalog.id.toString())
            textUpdatedName.setText(catalog.name)
            textUpdatedDescription.setText(catalog.description)

            val dialog = MaterialAlertDialogBuilder(context, R.style.Theme_ShoppingMobile)
                .setTitle("Are you absolutely sure?")
                .setMessage("This action cannot be undone. This will update the ${catalog.name} catalog.")
                .setView(dialogView)
                .setPositiveButton("Update") { dialog, which ->
                    println("Update")

                    CoroutineScope(Dispatchers.Main).launch {
                        var updatedCatalog: Catalog = Catalog(catalog.id, textUpdatedName.text.toString(), textUpdatedDescription.text.toString())
                        val response = service.update(catalog.id, updatedCatalog)

                        withContext(Dispatchers.Main) {
                            if (response.isSuccessful) {
                                textCatalogName.setText(updatedCatalog.name)
                                textCatalogDescription.setText(updatedCatalog.description)
                                println("OK")
                            } else {
                                println("error")
                            }
                        }
                    }
                }
                .setNegativeButton("Cancel") { dialog, which ->
                    println("Cancel")
                }
                .create()

            dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)

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