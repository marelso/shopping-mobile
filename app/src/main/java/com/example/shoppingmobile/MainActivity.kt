package com.example.shoppingmobile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import com.example.shoppingmobile.admin.AdminFragment
import com.example.shoppingmobile.basics.BasicsFragment
import com.example.shoppingmobile.user.UserFragment

class MainActivity : AppCompatActivity() {

    private var toolbar: Toolbar? = null
    private var switchButton: Button? = null
    private var isAdmin: Boolean = false
    private var basicsButton: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = findViewById(R.id.toolbar)
        toolbar?.title = "Shopping Mobile"
        setSupportActionBar(toolbar)

        switchButton = findViewById(R.id.btnSwitchMode)
        basicsButton = findViewById(R.id.btnBasicsMode)





        val userMode = UserFragment()
        val adminMode = AdminFragment()
        val basicsMode = BasicsFragment()

        switchButton?.setOnClickListener {
            this.isAdmin = !this.isAdmin
            switchViewMode(if(this.isAdmin) adminMode else userMode)
        }

        basicsButton?.setOnClickListener { switchViewMode(basicsMode) }

    }

    private fun switchViewMode(fragment: Fragment) {
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.flFragment, fragment)
            commit()
        }
    }
}