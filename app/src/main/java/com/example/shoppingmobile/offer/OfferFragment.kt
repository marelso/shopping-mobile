package com.example.shoppingmobile.offer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppingmobile.R
import com.example.shoppingmobile.domain.category.Category
import com.example.shoppingmobile.domain.category.CategoryAdapter
import com.example.shoppingmobile.domain.offer.Offer
import com.example.shoppingmobile.service.ApiClient
import com.example.shoppingmobile.service.CategoryService
import com.example.shoppingmobile.service.OfferService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [OfferFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class OfferFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    private var offersRecyclerView: RecyclerView? = null
    private var adapter: OfferAdapter? = null

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
        return inflater.inflate(R.layout.fragment_offer, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        offersRecyclerView = view.findViewById(R.id.offersRecyclerView)
        adapter = OfferAdapter()

        loadOffers()


        offersRecyclerView?.adapter = adapter
        offersRecyclerView?.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun loadOffers() {
        val service = ApiClient.createService(OfferService::class.java)
        val call = service.get()

        call.enqueue(object : Callback<List<Offer>> {
            override fun onResponse(
                call: Call<List<Offer>>,
                response: Response<List<Offer>>
            ) {
                val offers = response.body() as List<Offer>

                if(offers.isNotEmpty()) {
                    adapter?.setData(offers.toMutableList())
                }
            }

            override fun onFailure(call: Call<List<Offer>>, t: Throwable) {
                TODO("Not yet implemented")
            }

        })
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment OfferFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            OfferFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}