package com.example.shoppingmobile.admin

import android.os.Bundle
import android.view.*
import android.widget.Switch
import androidx.fragment.app.Fragment
import com.example.shoppingmobile.R
import com.example.shoppingmobile.basics.BasicsFragment
import com.example.shoppingmobile.catalog.CatalogFragment
import com.example.shoppingmobile.category.CategoryFragment
import com.example.shoppingmobile.coupon.CouponFragment
import com.example.shoppingmobile.offer.OfferFragment
import com.example.shoppingmobile.user.UserFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

/**
 * A simple [Fragment] subclass.
 * Use the [AdminFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class AdminFragment : Fragment(R.layout.fragment_admin) {

    private lateinit var bottomNavigationView: BottomNavigationView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        switchViewMode(CatalogFragment())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bottomNavigationView = view.findViewById(R.id.bottom_navigation)

        bottomNavigationView.setOnNavigationItemSelectedListener {
            item -> switchMenuItem(item)
        }
    }

    private fun switchViewMode(fragment: Fragment) {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.flManage, fragment)
            .addToBackStack(null)
            .commit()
    }

    private fun switchMenuItem(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.catalogs -> {
                switchViewMode(CatalogFragment())
                return true
            }
            R.id.categories -> {
                switchViewMode(CategoryFragment())
                return true
            }
            R.id.offers -> {
                switchViewMode(OfferFragment())
                return true
            }
            R.id.coupons -> {
                switchViewMode(CouponFragment())
                return true
            }
            R.id.basics -> {
                switchViewMode(BasicsFragment())
                return true
            }
            else -> return false
        }
    }
}