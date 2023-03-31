package com.example.shoppingmobile.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppingmobile.R
import com.example.shoppingmobile.domain.CatalogsAdapter
import com.example.shoppingmobile.service.ApiClient
import com.example.shoppingmobile.service.CatalogService
import kotlinx.coroutines.launch

class UserFragment : Fragment(R.layout.fragment_user) {
    private lateinit var catalogsRecyclerView: RecyclerView

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_user, container, false)
        catalogsRecyclerView = view.findViewById(R.id.catalog_recycler_view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Create the API service
        val apiService = ApiClient.createService(CatalogService::class.java)

        catalogsRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        lifecycleScope.launch {
            try {
                // Call the API to get the list of catalogs
                val catalogs = apiService.getCatalogs()

                // Set the CatalogsAdapter on the RecyclerView
                val adapter = CatalogsAdapter(catalogs)
                catalogsRecyclerView.adapter = adapter

                // Call notifyDataSetChanged() to update the view
                adapter.notifyDataSetChanged()
            } catch (e: Exception) {
                // Handle the error
                Toast.makeText(context, "Error loading catalogs: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }
}