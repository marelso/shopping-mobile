package com.example.shoppingmobile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppingmobile.adapters.TextAdapter
import com.example.shoppingmobile.view_model.TextViewModel

class MainActivity : AppCompatActivity() {

    private var toolbar: Toolbar? = null
    private var inputField: EditText? = null
    private var submitButton: Button? = null
    private var recyclerView: RecyclerView? = null
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
        recyclerView?.adapter = TextAdapter(viewModel.dataList)
    }

    private fun onSubmitClicked() {
        val text = inputField?.text.toString()

        viewModel.addItem(text)

        recyclerView?.adapter?.notifyItemInserted(viewModel.dataList.size - 1)
        inputField?.text?.clear()
    }
}