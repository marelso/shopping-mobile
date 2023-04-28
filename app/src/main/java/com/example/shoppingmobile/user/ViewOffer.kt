package com.example.shoppingmobile.user

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.util.Log
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.shoppingmobile.R
import com.example.shoppingmobile.domain.category.Category
import com.example.shoppingmobile.domain.offer.Offer
import com.example.shoppingmobile.domain.offer.OfferAdapter
import com.example.shoppingmobile.service.ApiClient
import com.example.shoppingmobile.service.CategoryService
import com.example.shoppingmobile.service.OfferService
import com.google.android.material.button.MaterialButton
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.NumberFormat
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val OFFER_ID = "offerId"

/**
 * A simple [Fragment] subclass.
 * Use the [ViewOffer.newInstance] factory method to
 * create an instance of this fragment.
 */
class ViewOffer : Fragment() {
    private var offerId: Int = 0
    private var givenId: TextView? = null

    private var couponLayout: LinearLayout? = null
    private var copyButton: MaterialButton? = null
    private var offerTitle: TextView? = null
    private var offerDescription: TextView? = null
    private var offerCategories: ChipGroup? = null
    private var offerPrice: TextView? = null

    private var offerRecyclerView: RecyclerView? = null
    private var offerAdapter: OfferAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            offerId = it.getInt(OFFER_ID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_view_offer, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        offerPrice = view.findViewById(R.id.offerPrice)
        couponLayout = view.findViewById(R.id.couponLayout)
        copyButton = view.findViewById(R.id.copyButton)
        offerTitle = view.findViewById(R.id.offerTitle)
        offerDescription = view.findViewById(R.id.offerDescription)
        offerCategories = view.findViewById(R.id.offerCategories)
        offerRecyclerView = view.findViewById(R.id.relatedOffers)
        offerAdapter = OfferAdapter()
        offerRecyclerView?.adapter = offerAdapter

        val service = ApiClient.createService(OfferService::class.java)

        val call = service.find(offerId)
        call.enqueue(object : Callback<Offer> {
            override fun onResponse(call: Call<Offer>, response: Response<Offer>) {
                if (response.isSuccessful) {
                    val offer = response.body() as Offer

                    if (offer != null) {
                        offerTitle?.text = offer.title
                        offerDescription?.text = offer.description

                        val formatter = NumberFormat.getCurrencyInstance(Locale.getDefault())
                        formatter.currency = Currency.getInstance("USD")
                        val formattedPrice = formatter.format(offer.price)

                        offerPrice?.text = formattedPrice

                        if(offer.coupon == null) {
                            couponLayout?.visibility = View.GONE
                        }
                        else {
                            copyButton?.text = offer.coupon.code
                        }

                        var offers = mutableListOf<Offer>()
                        var apiCallCounter = 0
                        val categoryCount = offer.categories.size

                        for (category in offer.categories) {
                            val chip = Chip(requireContext())
                            chip.chipCornerRadius = 5F
                            chip.text = category.name
                            chip.tag = category
                            chip.isFocusable = true
                            offerCategories?.addView(chip)

                            val service = ApiClient.createService(OfferService::class.java)

                            val call = service.getByCategory(category.id)
                            call.enqueue(object : Callback<List<Offer>> {
                                override fun onResponse(call: Call<List<Offer>>, response: Response<List<Offer>>) {
                                    val categoryOffers = response.body() as List<Offer>

                                    if(categoryOffers.isNotEmpty()){
                                        offers.addAll(categoryOffers)
                                    }

                                    apiCallCounter++
                                    if (apiCallCounter == categoryCount) {
                                        offerAdapter?.setData(offers)
                                    }
                                }
                                override fun onFailure(call: Call<List<Offer>>, t: Throwable) {
                                    TODO("Not yet implemented")
                                }
                            })
                        }
                    }
                }
            }

            override fun onFailure(call: Call<Offer>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })


        copyButton?.setOnClickListener {
            val clipboardManager = requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clipData = ClipData.newPlainText("Coupon Code", copyButton?.text)
            clipboardManager.setPrimaryClip(clipData)
            Toast.makeText(requireContext(), "Coupon code copied to clipboard", Toast.LENGTH_SHORT).show()
        }

        offerRecyclerView?.layoutManager = LinearLayoutManager(requireContext())
        offerRecyclerView?.adapter = offerAdapter
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: Int) =
            ViewOffer().apply {
                arguments = Bundle().apply {
                    putInt(OFFER_ID, param1)
                }
            }
    }
}