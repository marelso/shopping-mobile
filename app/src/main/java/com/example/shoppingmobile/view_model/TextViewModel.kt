package com.example.shoppingmobile.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TextViewModel : ViewModel() {
    private val _dataList = MutableLiveData<MutableList<String>>(mutableListOf())
    val dataList: LiveData<MutableList<String>> = _dataList

    fun addItem(item: String) {
        if (item.isNotEmpty()) {
            _dataList.value?.add(item)
            _dataList.postValue(_dataList.value)
        }
    }
}