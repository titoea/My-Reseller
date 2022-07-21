package com.example.myreseller.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myreseller.R
import com.example.myreseller.models.Item
import android.content.Context

class ProductsRecyclerAdapter (val context: Context, val myList :List<Item>): RecyclerView.Adapter<ProductsRecyclerAdapter.ViewHolder>(
) {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): ProductsRecyclerAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.products_card_layout_seller,parent,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ProductsRecyclerAdapter.ViewHolder, position: Int) {
        Glide.with(context).load(myList[position].image).into(holder.productImage)

        holder.productName.text = myList[position].name
        holder.productPrice.text = "NGN "+ myList[position].price

    }

    override fun getItemCount(): Int {
        return myList.size
    }
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var productImage : ImageView
        var productName: TextView
        var productPrice : TextView
        //var productLikeButton: Button
       // var productAddButton : Button

        init{
            productImage = itemView.findViewById(R.id.product_image)
            productName = itemView.findViewById(R.id.product_name)
            productPrice = itemView.findViewById(R.id.product_price)
            //productLikeButton = itemView.findViewById(R.id.products_like_button)
           // productAddButton = itemView.findViewById(R.id.products_add_button)

        }
    }
}