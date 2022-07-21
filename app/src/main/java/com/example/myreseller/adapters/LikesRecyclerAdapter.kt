package com.example.myreseller.adapters

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myreseller.R
import com.example.myreseller.models.Item
import com.example.myreseller.ui.activities.ProductPageActivity
import com.example.myreseller.utils.Constants
import com.example.myreseller.utils.MRButton
import com.example.myreseller.utils.MRTextViewRegular

class LikesRecyclerAdapter(val context: Context, val myList :List<Item>) : RecyclerView.Adapter<LikesRecyclerAdapter.ViewHolder>() {
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): LikesRecyclerAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.liked_items_card_layout, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: LikesRecyclerAdapter.ViewHolder, position: Int) {
        //populate item for cardview
        Glide.with(context).load(myList[position].image).into(holder.likesItemImage)

        holder.likesItemName.text = myList[position].name
        holder.likesItemPrice.text = "NGN " + myList[position].price
        holder.likesItemSize.text = "UK " + myList[position].size

        holder.likesItemImage.setOnClickListener {
            val intent = Intent(context, ProductPageActivity::class.java)
            intent.putExtra(Constants.ITEM_DETAILS, myList[position])
            context.startActivity(intent)

        }
    }

    override fun getItemCount(): Int {
        Log.i("size: ", myList.size.toString())
        return myList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var likesItemImage: ImageView
        var likesItemName: MRTextViewRegular
        var likesItemPrice: MRTextViewRegular
        var likesItemSize: MRTextViewRegular
        var likesItemButton: MRButton

        init {
            likesItemImage = itemView.findViewById(R.id.liked_item_image)
            likesItemName = itemView.findViewById(R.id.liked_item_name)
            likesItemPrice = itemView.findViewById(R.id.liked_item_price)
            likesItemSize = itemView.findViewById(R.id.liked_item_size)
            likesItemButton = itemView.findViewById(R.id.btn_remove_from_likes)

        }
    }

}
