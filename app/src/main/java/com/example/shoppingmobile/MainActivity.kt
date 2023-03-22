package com.example.shoppingmobile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Switch
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.example.shoppingmobile.admin.AdminFragment
import com.example.shoppingmobile.basics.BasicsFragment
import com.example.shoppingmobile.user.UserFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private var toolbar: Toolbar? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = findViewById(R.id.toolbar)
        toolbar?.title = "Shopping Mobile"
        setSupportActionBar(toolbar)

        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment, UserFragment())
            commit()
        }

        BottomNavigationView.OnNavigationItemSelectedListener { item ->
            switchMenuItem(item)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)

        val userMode = UserFragment()
        val adminMode = AdminFragment()

        val switchItem = menu?.findItem(R.id.switchMode)
        val switch = switchItem?.actionView?.findViewById<Switch>(R.id.isAdmin)

        switch?.setOnCheckedChangeListener { _, isChecked ->
            switchViewMode(if(isChecked) adminMode else userMode)
        }

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(!switchMenuItem(item)) super.onOptionsItemSelected(item)

        return true
    }

    private fun switchViewMode(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment, fragment)
            commit()
        }
    }

    private fun switchMenuItem(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.catalogs -> {
                // TODO Handle the "Catalogs" item click
                return true
            }
            R.id.categories -> {
                // TODO Handle the "Categories" item click
                return true
            }
            R.id.offers -> {
                // TODO Handle the "Offers" item click
                return true
            }
            R.id.coupons -> {
                // TODO Handle the "Coupons" item click
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