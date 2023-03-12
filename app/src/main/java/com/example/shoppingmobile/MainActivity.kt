package com.example.shoppingmobile

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
    fun submit(view: View) {
        val inputField = findViewById<EditText>(R.id.inputField)
        val textView = findViewById<TextView>(R.id.textView)
        val text = inputField.text.toString()
        textView.text = text
    }

}