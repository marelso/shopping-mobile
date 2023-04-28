package com.example.shoppingmobile.user

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppingmobile.R
import com.example.shoppingmobile.catalog.CatalogFragment
import com.example.shoppingmobile.domain.CatalogUserAdapter
import com.example.shoppingmobile.service.ApiClient
import com.example.shoppingmobile.service.CatalogService
import kotlinx.coroutines.launch

class UserFragment : Fragment(R.layout.fragment_user) {
    private var catalogsRecyclerView: RecyclerView? = null
    private var adapter: CatalogUserAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_user, container, false)
        catalogsRecyclerView = view.findViewById(R.id.catalog_recycler_view)
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = CatalogUserAdapter(View.OnClickListener { view ->
            val id = Integer.valueOf(view.findViewById<TextView>(R.id.catalogIdTextView)?.text.toString())
            val fragment = ViewCatalogFragment.newInstance(id)
            switchViewMode(fragment)
        })

        // Create the API service
        val service = ApiClient.createService(CatalogService::class.java)

        catalogsRecyclerView?.layoutManager = LinearLayoutManager(requireContext())
        catalogsRecyclerView?.adapter = adapter

        println(1)
        lifecycleScope.launch {
            try {
                val catalogs = service.getCatalogs()
                adapter?.setData(catalogs.toMutableList())
                adapter?.notifyDataSetChanged()
            } catch (e: Exception) {
                Toast.makeText(context, "Error loading catalogs: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun switchViewMode(fragment: Fragment) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.flFragment, fragment)
            .addToBackStack(null)
            .commit()
    }
}