package com.example.shoppingmobile.domain

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class CatalogSpinnerAdapter (context: Context, resource: Int, catalogs: List<Catalog>) :
    ArrayAdapter<Catalog>(context, resource, catalogs) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getView(position, convertView, parent)
        view.tag = getItem(position)
        (view as TextView).text = getItem(position)?.name
        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = super.getDropDownView(position, convertView, parent)
        view.tag = getItem(position)
        (view as TextView).text = getItem(position)?.name
        return view
    }

    init {
        setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
    }
}