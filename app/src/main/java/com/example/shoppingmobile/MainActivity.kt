package com.example.shoppingmobile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppingmobile.adapters.TextAdapter
import com.example.shoppingmobile.view_model.TextViewModel
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private var toolbar: Toolbar? = null
    private var inputField: EditText? = null
    private var submitButton: Button? = null
    private var switchButton: Button? = null
    private var recyclerView: RecyclerView? = null
    private var textAdapter: TextAdapter = TextAdapter()
    private lateinit var viewModel: TextViewModel
    private var isAdmin: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = findViewById(R.id.toolbar)
        inputField = findViewById(R.id.inputField)
        submitButton = findViewById(R.id.submitButton)
        switchButton = findViewById(R.id.btnSwitchMode)
        recyclerView = findViewById(R.id.recyclerView)
        viewModel = ViewModelProvider(this).get(TextViewModel::class.java)


        toolbar?.title = "Shopping Mobile"
        setSupportActionBar(toolbar)

        submitButton?.setOnClickListener { onSubmitClicked() }

        switchButton?.setOnClickListener { switchViewMode() }

        recyclerView?.layoutManager = LinearLayoutManager(this)
        recyclerView?.adapter = textAdapter

        lifecycleScope.launch {
            viewModel.dataList.collect { data ->
                textAdapter.setData(data.toMutableList())
            }
        }

    }

    private fun onSubmitClicked() {
        val text = inputField?.text.toString()

        viewModel.addItem(text)

        inputField?.text?.clear()
    }

    private fun switchViewMode() {
        val userMode = UserFragment()
        val adminMode = AdminFragment()

        this.isAdmin = !this.isAdmin
        supportFragmentManager.beginTransaction().apply {
            when(isAdmin) {
                true -> {
                    replace(R.id.flFragment, adminMode)
                    commit()
                }
                else -> {
                    replace(R.id.flFragment, userMode)
                    commit()
                }
            }
        }
    }
}