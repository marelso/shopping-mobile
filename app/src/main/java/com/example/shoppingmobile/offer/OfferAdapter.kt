package com.example.shoppingmobile.offer

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.shoppingmobile.R
import com.example.shoppingmobile.domain.offer.Offer

class OfferAdapter(): RecyclerView.Adapter<OfferAdapter.ViewHolder>() {
    private var dataList: MutableList<Offer> = mutableListOf()
    private var onClickListener: View.OnClickListener? = null

    fun setData(data: MutableList<Offer>) {
        dataList.clear()
        dataList.addAll(data)
        notifyDataSetChanged()
    }

    fun setClickListener(event: View.OnClickListener) {
        onClickListener = event
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var id: TextView = itemView.findViewById(R.id.textOfferId)
        var title: TextView = itemView.findViewById(R.id.textOfferTitle)

        var couponLayout: LinearLayout = itemView.findViewById(R.id.layoutCoupon)
        var couponImage: ImageButton = itemView.findViewById(R.id.icCoupon)
        var couponCode: TextView = itemView.findViewById(R.id.textOfferCouponCode)

        var activeLayout: LinearLayout = itemView.findViewById(R.id.layoutStatus)
        var activeIcon:ImageButton = itemView.findViewById(R.id.icStatus)
        var activeStatus: TextView = itemView.findViewById(R.id.textOfferStatus)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.offer_card, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = dataList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val offer: Offer = dataList[position]
        holder.id.text = offer.id.toString()
        holder.title.text = offer.title

        when(offer.active) {
            true -> {
                holder.activeStatus.text = "Enabled"
                holder.activeIcon.setImageResource(R.drawable.ic_enabled)
            }
            false -> {
                holder.activeStatus.text = "Disabled"
                holder.activeIcon.setImageResource(R.drawable.ic_disabled)
            }
        }

        if(offer.coupon != null) {
            holder.couponCode.text = offer.coupon.code
        } else {
            holder.couponLayout.visibility = View.GONE
        }

        holder.itemView.setOnClickListener {
            onClickListener?.onClick(it)
        }
    }
}