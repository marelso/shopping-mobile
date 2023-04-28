package com.example.shoppingmobile.domain.category

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppingmobile.R
import com.example.shoppingmobile.basics.TextAdapter
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

class CategoryAdapter(private val categories: List<Category>) : BaseAdapter() {

    override fun getCount(): Int {
        return categories.size
    }

    override fun getItem(position: Int): Any {
        return categories[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val chip = convertView as? Chip ?: LayoutInflater.from(parent?.context)
            .inflate(R.layout.category_chip, parent, false) as Chip

        val category = getItem(position) as Category
        chip.text = category.name
        chip.tag = category

        chip.setOnClickListener {
            val selectedCategory = it.tag as Category
            // do something with the selected category
        }

        return chip
    }
}