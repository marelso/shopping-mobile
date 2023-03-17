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
        return when (item.itemId) {
            R.id.catalogs -> {
                // TODO Handle the "Catalogs" item click
                true
            }
            R.id.categories -> {
                // TODO Handle the "Categories" item click
                true
            }
            R.id.offers -> {
                // TODO Handle the "Offers" item click
                true
            }
            R.id.coupons -> {
                // TODO Handle the "Coupons" item click
                true
            }
            R.id.basics -> {
                switchViewMode(BasicsFragment())
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun switchViewMode(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment, fragment)
            commit()
        }
    }
}