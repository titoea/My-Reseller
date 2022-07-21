package com.example.myreseller.adapters

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myreseller.R
import com.example.myreseller.models.Cart
import com.example.myreseller.models.Item
import com.example.myreseller.utils.MRButton
import com.example.myreseller.utils.MRTextViewBold
import com.example.myreseller.utils.MRTextViewRegular

class CartRecyclerAdapter (val context: Context, val myList :List<Item>) : RecyclerView.Adapter<CartRecyclerAdapter.ViewHolder>() {
    private var amount = 0
    private var cumamount =0.00
    override fun onCreateViewHolder(
        //create cardview and inflate it
        parent: ViewGroup,
        viewType: Int,
    ): CartRecyclerAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.cart_items_card_layout,parent,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: CartRecyclerAdapter.ViewHolder, position: Int) {

        //populate item for cardview
        Glide.with(context).load(myList[position].image).into(holder.cartItemImage)

        holder.cartItemName.text = myList[position].name
        holder.cartItemPrice.text = "NGN "+ myList[position].price
        holder.cartItemSize.text = "Size (UK): " + myList[position].size


        holder.cartImageButtonIncrease.setOnClickListener {
            if(amount < myList[position].quantity ){
                amount++
                holder.cartItemQuantity.text = amount.toString()
            }
            else{
                holder.cartItemQuantity.text = amount.toString()
            }

        }

        holder.cartImageButtonDecrease.setOnClickListener {
            if(amount > 1 && (amount <= myList[position].quantity)){
                amount--
                holder.cartItemQuantity.text = amount.toString()
            }
            else{
                holder.cartItemQuantity.text = amount.toString()
            }
        }


       // cumamount += (myList[position].price * holder.cartItemQuantity.text.toString().toDouble())
        //Log.i("cumamount: ", cumamount.toString())
    }

    override fun getItemCount(): Int {
        Log.i("size: ", myList.size.toString())
        return myList.size

    }
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var cartItemImage : ImageView
        var cartItemName: MRTextViewRegular
        var cartItemPrice : MRTextViewRegular
        var cartItemSize: MRTextViewRegular
        var cartImageButtonIncrease : ImageButton
        var cartImageButtonDecrease : ImageButton
        var cartItemQuantity : MRTextViewRegular
        var cartImageButtonRemove : ImageButton

        //var cartItemButton : MRButton

        init{
            cartItemImage = itemView.findViewById(R.id.cart_item_image)
            cartItemName = itemView.findViewById(R.id.cart_item_name)
            cartItemPrice = itemView.findViewById(R.id.cart_item_price)
            cartItemSize = itemView.findViewById(R.id.cart_item_size)
            cartImageButtonIncrease = itemView.findViewById(R.id.ib_increase_quantity)
            cartImageButtonDecrease = itemView.findViewById(R.id.ib_decrease_quantity)
            cartItemQuantity = itemView.findViewById(R.id.tv_cart_item_quantity)
            cartImageButtonRemove = itemView.findViewById(R.id.ib_remove_from_cart)

           // cartItemButton = itemView.findViewById(R.id.btn_remove_from_cart)

        }
    }
}