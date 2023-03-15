package com.example.shoppingmobile.view_model

import androidx.lifecycle.ViewModel

class TextViewModel : ViewModel() {
    private val _dataList = mutableListOf<String>()
    val dataList: MutableList<String> = _dataList

    fun addItem(item: String) {
        if (item.isNotEmpty()) {
            _dataList.add(item)
        }
    }
}