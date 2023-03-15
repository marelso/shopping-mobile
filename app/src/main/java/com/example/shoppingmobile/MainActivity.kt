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
    private var recyclerView: RecyclerView? = null
    private var textAdapter: TextAdapter = TextAdapter()
    private lateinit var viewModel: TextViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = findViewById(R.id.toolbar)
        inputField = findViewById(R.id.inputField)
        submitButton = findViewById(R.id.submitButton)
        recyclerView = findViewById(R.id.recyclerView)
        viewModel = ViewModelProvider(this).get(TextViewModel::class.java)


        toolbar?.title = "Shopping Mobile"
        setSupportActionBar(toolbar)

        submitButton?.setOnClickListener { onSubmitClicked() }

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
}