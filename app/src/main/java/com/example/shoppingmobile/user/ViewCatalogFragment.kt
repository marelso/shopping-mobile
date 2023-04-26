package com.example.shoppingmobile.user

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppingmobile.R
import com.example.shoppingmobile.domain.CatalogUserAdapter
import com.example.shoppingmobile.domain.category.Category
import com.example.shoppingmobile.domain.offer.Offer
import com.example.shoppingmobile.domain.offer.OfferAdapter
import com.example.shoppingmobile.service.ApiClient
import com.example.shoppingmobile.service.CategoryService
import com.example.shoppingmobile.service.OfferService
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_CATALOG_ID = "catalogId"

class ViewCatalogFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var catalogId: Int = 0

    private var categoryUnderline: View? = null
    private var noCategorySelected: TextView? = null
    private var currentCategory: TextView? = null
    private var availableCategories: ChipGroup? = null

    private var offerRecyclerView: RecyclerView? = null
    private var offerAdapter: OfferAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            catalogId = it.getInt(ARG_CATALOG_ID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_view_catalog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        offerAdapter = OfferAdapter()

        availableCategories = view.findViewById(R.id.availableCategories)
        noCategorySelected = view.findViewById(R.id.textNoCategorySelect)
        offerRecyclerView = view.findViewById(R.id.offerRecyclerView)
        currentCategory = view.findViewById(R.id.category)
        categoryUnderline = view.findViewById(R.id.categoryUnderline)

        val service = ApiClient.createService(CategoryService::class.java)

        val call = service.get(catalogId)
        call.enqueue(object : Callback<List<Category>> {
            override fun onResponse(call: Call<List<Category>>, response: Response<List<Category>>) {
                if (response.isSuccessful) {
                    val categories = response.body() as List<Category>

                    if(categories.isEmpty()) {
                        noCategorySelected?.text = "No categories available at this time."
                    }

                    for (category in categories) {
                        val chip = Chip(requireContext())
                        chip.text = category.name
                        chip.tag = category
                        chip.isClickable = true
                        chip.isCheckable = true
                        chip.isFocusable = true
                        chip.setOnClickListener {
                            val selectedCategoryId = it.tag as? Category ?: return@setOnClickListener
                            Log.d("Selected Category", category.name)
                            currentCategory?.text = selectedCategoryId.name
                        }
                        availableCategories?.addView(chip)
                    }
                } else {
                    //TODO handle error
                }
            }

            override fun onFailure(call: Call<List<Category>>, t: Throwable) {
                //TODO handle error
            }
        })

        availableCategories?.setOnCheckedChangeListener { group, checkedId ->
            chipCheckedChange(group, checkedId)
        }

        offerRecyclerView?.layoutManager = LinearLayoutManager(requireContext())
        offerRecyclerView?.adapter = offerAdapter
    }

    private fun chipCheckedChange(group: ChipGroup, checkedId: Int) {
        if (checkedId == View.NO_ID) {
            noCategorySelected?.text = "Select any category..."
            noCategorySelected?.visibility = View.VISIBLE
            currentCategory?.visibility = View.INVISIBLE
            categoryUnderline?.visibility = View.INVISIBLE
            offerRecyclerView?.adapter = OfferAdapter()
        } else {
            noCategorySelected?.visibility = View.GONE
            currentCategory?.visibility = View.VISIBLE
            categoryUnderline?.visibility = View.VISIBLE
            loadOffersByCategory()
        }

        var selectedChipId: Int? = null
        selectedChipId?.let { group.findViewById<Chip>(it)?.isChecked = false }
        selectedChipId = checkedId
        selectedChipId.let { group.findViewById<Chip>(it)?.isChecked = true }
    }

    private fun loadOffersByCategory() {
        val selectedChipId = availableCategories?.checkedChipId
        if (selectedChipId != View.NO_ID) {
            val selectedChip = selectedChipId?.let { view?.findViewById<Chip>(it) }
            val category = selectedChip?.tag as Category

            val service = ApiClient.createService(OfferService::class.java)

            val call = service.getByCategory(category.id)
            call.enqueue(object : Callback<List<Offer>> {
                override fun onResponse(call: Call<List<Offer>>, response: Response<List<Offer>>) {
                    if (response.isSuccessful) {
                        val offers = response.body() as List<Offer>

                        if(offers.isEmpty()) {
                            offerRecyclerView?.adapter = OfferAdapter()
                            noCategorySelected?.text = "No offers available at this time."
                            noCategorySelected?.visibility = View.VISIBLE
                            return
                        }

                        offerAdapter?.setData(offers.toMutableList())
                        noCategorySelected?.visibility = View.GONE
                    } else {
                        noCategorySelected?.text = "Cannot load offers."
                        noCategorySelected?.visibility = View.VISIBLE
                        return
                    }
                }

                override fun onFailure(call: Call<List<Offer>>, t: Throwable) {
                    TODO("Not yet implemented")
                }
            })
        }

        offerRecyclerView?.adapter = offerAdapter
    }

    companion object {
        @JvmStatic
        fun newInstance(catalogId: Int) =
            ViewCatalogFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_CATALOG_ID, catalogId)
                }
            }
    }
}