package com.example.shoppingmobile.user

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.shoppingmobile.R

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

        givenId = view.findViewById(R.id.givenId)

        givenId?.text = offerId.toString()
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