package com.example.shoppingmobile.user

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppingmobile.R
import com.example.shoppingmobile.domain.CatalogAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

class UserFragment : Fragment(R.layout.fragment_user) {
    private lateinit var recyclerView: RecyclerView
    private lateinit var catalogAdapter: CatalogAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_catalog, container, false)

        recyclerView = view.findViewById(R.id.catalog_list)
        recyclerView.layoutManager = LinearLayoutManager(activity)

        catalogAdapter = CatalogAdapter(emptyList())
        recyclerView.adapter = catalogAdapter

        // Fetch the catalogs using the CatalogService
        val service = RetrofitClient.retrofit.create(CatalogService::class.java)

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val catalogs = service.getCatalogs()

                // Update the UI on the main thread
                withContext(Dispatchers.Main) {
                    catalogAdapter = CatalogAdapter(catalogs)
                    recyclerView.adapter = catalogAdapter
                }
            } catch (e: HttpException) {
                // Handle HTTP exceptions (e.g. 404, 500)
                withContext(Dispatchers.Main) {
                    // Show an error message to the user
                    // (e.message() contains the error message from the server)
                }
            } catch (e: IOException) {
                // Handle network IO exceptions (e.g. no internet connection)
                withContext(Dispatchers.Main) {
                    // Show an error message to the user
                }
            }
        }

        return view
    }
}