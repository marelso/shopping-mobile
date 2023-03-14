package com.example.shoppingmobile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.widget.Toolbar

class MainActivity : AppCompatActivity() {

    private var toolbar: Toolbar? = null
    private var inputField: EditText? = null
    private var submitButton: Button? = null
    private var textView: TextView? = null
    private var dataList = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = findViewById(R.id.toolbar)
        inputField = findViewById(R.id.inputField)
        submitButton = findViewById(R.id.submitButton)
        textView = findViewById(R.id.textView)

        submitButton?.setOnClickListener {
            textView?.text = inputField?.text.toString()
            val text = inputField?.text.toString()
            if (text.isNotEmpty()) {
                dataList.add(text)
            }
        }

        toolbar?.title = "Shopping Mobile"
        setSupportActionBar(toolbar)
    }
}