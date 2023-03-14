package com.example.shoppingmobile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppingmobile.adapters.TextAdapter

class MainActivity : AppCompatActivity() {

    private var toolbar: Toolbar? = null
    private var inputField: EditText? = null
    private var submitButton: Button? = null
    private var dataList = mutableListOf<String>()
    private var recyclerView: RecyclerView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = findViewById(R.id.toolbar)
        inputField = findViewById(R.id.inputField)
        submitButton = findViewById(R.id.submitButton)
        recyclerView = findViewById(R.id.recyclerView)


        toolbar?.title = "Shopping Mobile"
        setSupportActionBar(toolbar)

        submitButton?.setOnClickListener {
            val text = inputField?.text.toString()
            if (text.isNotEmpty()) {
                dataList.add(text)
                recyclerView?.adapter?.notifyItemInserted(dataList.size - 1)
                inputField?.text?.clear()
            }
        }

        recyclerView?.layoutManager = LinearLayoutManager(this)
        recyclerView?.adapter = TextAdapter(dataList)
    }
}