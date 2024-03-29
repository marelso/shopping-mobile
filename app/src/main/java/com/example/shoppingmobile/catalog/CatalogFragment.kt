package com.example.shoppingmobile.catalog

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppingmobile.R
import com.example.shoppingmobile.domain.Catalog
import com.example.shoppingmobile.domain.CatalogAdapter
import com.example.shoppingmobile.service.ApiClient
import com.example.shoppingmobile.service.CatalogService
import kotlinx.coroutines.launch

class CatalogFragment : Fragment() {

    private lateinit var catalogsRecyclerView: RecyclerView

    private var submitButton: Button? = null
    private var nameField: EditText? = null
    private var descriptionField: EditText? = null
    private var adapter: CatalogAdapter? = null
    private var service: CatalogService? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        adapter = CatalogAdapter()
        val view = inflater.inflate(R.layout.fragment_catalog, container, false)

        service = ApiClient.createService(CatalogService::class.java)

        nameField = view.findViewById(R.id.textCatalogName)
        descriptionField = view.findViewById(R.id.textCatalogDescription)
        submitButton = view.findViewById(R.id.btnSubmitCatalog)



        catalogsRecyclerView = view.findViewById(R.id.catalog_recycler_view)
        catalogsRecyclerView.adapter = adapter

        lifecycleScope.launch {
            list()
            submitButton?.setOnClickListener{ submit() }
        }

        return view
    }

    private fun submit(){
        val name = nameField?.text.toString()
        val description = descriptionField?.text.toString()

        lifecycleScope.launch {
            try {
                if(name.isNullOrEmpty() || description.isNullOrEmpty()) {
                    throw Exception("All the fields should be filled.");
                }

                val catalog = service?.create(Catalog(0, name, description))
                val newCatalogs = (adapter?.currentList?.plus(listOf(catalog)))?.sortedBy { it?.id }
                adapter?.submitList(newCatalogs)
                nameField?.text?.clear()
                descriptionField?.text?.clear()
                Toast.makeText(context, "Catalog created!", Toast.LENGTH_SHORT).show()
            } catch (e: Exception) {
                Toast.makeText(context, "Error creating catalog: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private suspend fun list() {
        catalogsRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        try {
            val catalogs = service?.getCatalogs()
            adapter?.submitList(catalogs)
            catalogsRecyclerView.adapter = adapter

            // Call notifyDataSetChanged() to update the view
            adapter?.notifyDataSetChanged()
        } catch (e: Exception) {
            // Handle the error
            Toast.makeText(context, "Error loading catalogs: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }
}