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
import com.example.shoppingmobile.R
import com.example.shoppingmobile.domain.Catalog
import com.example.shoppingmobile.domain.CatalogsAdapter
import com.example.shoppingmobile.service.ApiClient
import com.example.shoppingmobile.service.CatalogService
import kotlinx.coroutines.launch

class CatalogFragment : Fragment() {

    private var submitButton: Button? = null
    private var nameField: EditText? = null
    private var descriptionField: EditText? = null
    private var adapter: CatalogsAdapter? = null
    private var service: CatalogService? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_catalog, container, false)

        service = ApiClient.createService(CatalogService::class.java)

        nameField = view.findViewById(R.id.textName)
        descriptionField = view.findViewById(R.id.textDescription)
        submitButton = view.findViewById(R.id.btnSubmitCatalog)

        submitButton?.setOnClickListener{ submit() }

        return view
    }

    private fun submit(){
        val name = nameField?.text.toString()
        val description = descriptionField?.text.toString()

        lifecycleScope.launch {
            try {
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
}