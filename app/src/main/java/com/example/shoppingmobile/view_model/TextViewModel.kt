package com.example.shoppingmobile.view_model

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class TextViewModel : ViewModel() {
    private val _dataList = MutableStateFlow<List<String>>(emptyList())
    val dataList: StateFlow<List<String>> = _dataList

    fun addItem(item: String) {
        if (item.isNotEmpty()) {
            _dataList.value += item
        }
    }
}