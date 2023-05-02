package com.example.shoppingmobile.offer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.lifecycle.lifecycleScope
import com.example.shoppingmobile.R
import com.example.shoppingmobile.domain.Catalog
import com.example.shoppingmobile.domain.CatalogAdapter
import com.example.shoppingmobile.domain.CatalogSpinnerAdapter
import com.example.shoppingmobile.service.ApiClient
import com.example.shoppingmobile.service.CatalogService
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.google.android.material.chip.ChipGroup
import kotlinx.coroutines.launch

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [NewOfferFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class NewOfferFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var catalogStep: MaterialCardView? = null
    private var catalogStepIc: ImageButton? = null
    private var catalogContentText: TextView? = null
    private var selectCatalog: Spinner? = null
    private var buttonCatalog: MaterialButton? = null
    private var progressCatalog: View? = null

    private var categoriesStep: MaterialCardView? = null
    private var categoriesStepIc: ImageButton? = null
    private var categoriesProgress: View? = null
    private var categoriesContentText: TextView? = null
    private var categoriesChips: ChipGroup? = null
    private var buttonBack: MaterialButton? = null
    private var buttonContinue: MaterialButton? = null

    private var offerStep: MaterialCardView? = null
    private var offerStepIc: ImageButton? = null
    private var offerProgress: View? = null
    private var offerContentText: TextView? = null
    private var selectedCoupon: Spinner? = null
    private var buttonBackToCategories: MaterialButton? = null
    private var buttonSubmit: MaterialButton? = null

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
        return inflater.inflate(R.layout.fragment_new_offer, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        offerStep = view.findViewById(R.id.offerStep)
        offerStepIc = view.findViewById(R.id.offerStepIc)
        offerProgress = view.findViewById(R.id.offerProgress)
        selectedCoupon = view.findViewById(R.id.selectedCoupon)
        offerContentText = view.findViewById(R.id.offerContentText)
        buttonBackToCategories = view.findViewById(R.id.buttonBackToCategories)
        buttonSubmit = view.findViewById(R.id.buttonSubmit)

        offerStep?.setOnClickListener {
            doCategoriesStepDone()
            doCatalogStepDone()
            doOfferStepEditing()
        }

        buttonContinue?.setOnClickListener {
            submit()
        }
        buttonBackToCategories?.setOnClickListener {
            doCatalogStepDone()
            doCategoriesStepEditing()
            doOfferStepPending()
        }

        categoriesStep = view.findViewById(R.id.categoriesStep)
        categoriesStepIc = view.findViewById(R.id.categoriesStepIc)
        categoriesProgress = view.findViewById(R.id.categoriesProgress)
        categoriesContentText = view.findViewById(R.id.categoriesContentText)
        categoriesChips = view.findViewById(R.id.categoriesChips)
        buttonContinue = view.findViewById(R.id.buttonContinue)
        buttonBack = view.findViewById(R.id.buttonBack)

        categoriesStep?.setOnClickListener {
            doCatalogStepDone()
            doCategoriesStepEditing()
            doOfferStepPending()
        }

        buttonContinue?.setOnClickListener {
            doCatalogStepDone()
            doCategoriesStepDone()
            doOfferStepEditing()
        }
        buttonBack?.setOnClickListener {
            doCatalogStepEditing()
            doCategoriesStepPending()
            doOfferStepPending()
        }

        catalogStep = view.findViewById(R.id.catalogStep)
        catalogStepIc = view.findViewById(R.id.catalogStepIc)
        catalogContentText = view.findViewById(R.id.catalogContentText)
        progressCatalog = view.findViewById(R.id.catalogProgress)
        selectCatalog = view.findViewById(R.id.catalog_spinner)
        buttonCatalog = view.findViewById(R.id.buttonNext)

        buttonCatalog?.setOnClickListener {
            doCatalogStepDone()
            doCategoriesStepEditing()
            doOfferStepPending()
        }

        catalogStep?.setOnClickListener {
            doCatalogStepEditing()
            doCategoriesStepPending()
            doOfferStepPending()
        }

        lifecycleScope.launch {
            selectCatalog?.adapter = CatalogSpinnerAdapter(requireContext()
                , android.R.layout.simple_spinner_item
                , loadCatalogs()
            )
        }

        selectCatalog?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                val selectedCatalog = view?.tag as Catalog
                val selectedIndex = position

                println(selectedIndex)
                // Do something with the selected catalog and index
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Do something when nothing is selected
            }
        }

    }

    private suspend fun loadCatalogs(): List<Catalog> {
        val service = ApiClient.createService(CatalogService::class.java)

        return service.getCatalogs()
    }

    private fun doCategoriesStepDone() {
        buttonBack?.visibility = View.GONE
        buttonContinue?.visibility = View.GONE
        categoriesProgress?.visibility = View.GONE
        categoriesChips?.visibility = View.GONE
        categoriesContentText?.visibility = View.GONE
        categoriesStepIc?.setImageResource(R.drawable.ic_done)
    }

    private fun doCategoriesStepPending() {
        buttonBack?.visibility = View.GONE
        buttonContinue?.visibility = View.GONE
        categoriesProgress?.visibility = View.GONE
        categoriesChips?.visibility = View.GONE
        categoriesContentText?.visibility = View.GONE
        categoriesStepIc?.setImageResource(R.drawable.ic_pending)
    }

    private fun doCategoriesStepEditing() {
        buttonBack?.visibility = View.VISIBLE
        buttonContinue?.visibility = View.VISIBLE
        categoriesProgress?.visibility = View.VISIBLE
        categoriesChips?.visibility = View.VISIBLE
        categoriesContentText?.visibility = View.VISIBLE
        categoriesStepIc?.setImageResource(R.drawable.ic_current)
    }

    private fun submit() {
        buttonBack?.visibility = View.GONE
        buttonContinue?.visibility = View.GONE
        categoriesProgress?.visibility = View.GONE
        categoriesChips?.visibility = View.GONE
        categoriesContentText?.visibility = View.GONE
        categoriesStepIc?.setImageResource(R.drawable.ic_done)
    }

    private fun doOfferStepPending() {
        buttonBackToCategories?.visibility = View.GONE
        buttonSubmit?.visibility = View.GONE
        offerProgress?.visibility = View.GONE
        selectedCoupon?.visibility = View.GONE
        offerContentText?.visibility = View.GONE
        offerStepIc?.setImageResource(R.drawable.ic_pending)
    }

    private fun doOfferStepEditing() {
        buttonBackToCategories?.visibility = View.VISIBLE
        buttonSubmit?.visibility = View.VISIBLE
        offerProgress?.visibility = View.VISIBLE
        selectedCoupon?.visibility = View.VISIBLE
        offerContentText?.visibility = View.VISIBLE
        offerStepIc?.setImageResource(R.drawable.ic_current)
    }

    private fun doCatalogStepDone() {
        buttonCatalog?.visibility = View.GONE
        progressCatalog?.visibility = View.GONE
        selectCatalog?.visibility = View.GONE
        catalogContentText?.visibility = View.GONE
        catalogStepIc?.setImageResource(R.drawable.ic_done)
    }

    private fun doCatalogStepEditing() {
        buttonCatalog?.visibility = View.VISIBLE
        progressCatalog?.visibility = View.VISIBLE
        selectCatalog?.visibility = View.VISIBLE
        catalogContentText?.visibility = View.VISIBLE
        catalogStepIc?.setImageResource(R.drawable.ic_current)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment NewOfferFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String?, param2: String?) =
            NewOfferFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}