package com.example.shoppingmobile.category

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.shoppingmobile.R
import com.example.shoppingmobile.service.ApiClient
import com.example.shoppingmobile.service.CatalogService
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [CategoryFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CategoryFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var availableCatalogs: ChipGroup? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_category, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        availableCatalogs = view.findViewById(R.id.availableCatalogs)

        val service = ApiClient.createService(CatalogService::class.java)
        lifecycleScope.launch {
            try {
                val catalogs = service.getCatalogs()

                for(catalog in catalogs) {
                    val chip = Chip(requireContext())
                    chip.text = catalog.name
                    chip.tag = catalog
                    chip.isCheckable = true
                    chip.isFocusable = true

                    availableCatalogs?.addView(chip)
                }

            } catch (e: Exception) {
                Toast.makeText(context, "Error loading catalogs: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment CategoryFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CategoryFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}