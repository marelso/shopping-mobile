package com.example.shoppingmobile.domain.offer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppingmobile.R

class OfferAdapter(): RecyclerView.Adapter<OfferAdapter.ViewHolder>() {
    private var dataList: MutableList<Offer> = mutableListOf()

    fun setData(data: MutableList<Offer>) {
        dataList.clear()
        dataList.addAll(data)
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var id: TextView = itemView.findViewById(R.id.id)
        var title: TextView = itemView.findViewById(R.id.title)
        var description: TextView = itemView.findViewById(R.id.description)
        var coupon: TextView = itemView.findViewById(R.id.coupon)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.offer_card_user, parent, false)
        return OfferAdapter.ViewHolder(view)
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val offer: Offer = dataList[position]
        holder.id.text = offer.id.toString()
        holder.title.text = offer.title
        holder.description.text = offer.description
        if(offer.coupon != null) {
            holder.coupon.text = offer.coupon.code
            holder.coupon.visibility = View.VISIBLE
        }
        else holder.coupon.visibility = View.GONE
    }
}