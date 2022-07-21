package com.example.myreseller.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView

import androidx.recyclerview.widget.RecyclerView
import com.example.myreseller.R

class OrdersRecyclerAdapter : RecyclerView.Adapter<OrdersRecyclerAdapter.ViewHolder> () {
    override fun onCreateViewHolder(
        //create cardview and inflate it
        parent: ViewGroup,
        viewType: Int,
    ): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.orders_card_layout_seller,parent,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        TODO("Not yet implemented")
        //populate item for cardview
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var orderImage : ImageView
        var orderBuyerName: TextView
        var orderItemName : TextView
        var orderItemPrice: TextView
        var orderItemQuantity : TextView

        init{
            orderImage = itemView.findViewById(R.id.order_image)
            orderBuyerName = itemView.findViewById(R.id.order_buyer_name)
            orderItemName = itemView.findViewById(R.id.order_item_name)
            orderItemPrice = itemView.findViewById(R.id.order_item_price)
            orderItemQuantity = itemView.findViewById(R.id.order_item_quantity)

        }
    }
}