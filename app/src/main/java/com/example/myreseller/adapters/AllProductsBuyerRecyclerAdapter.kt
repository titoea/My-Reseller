package com.example.myreseller.adapters

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myreseller.R
import com.example.myreseller.firestore.FirestoreClass
import com.example.myreseller.models.Item
import com.example.myreseller.models.Likes
import com.example.myreseller.ui.activities.BaseActivity
import com.example.myreseller.ui.activities.DashboardBuyerActivity
import com.example.myreseller.ui.activities.LikesActivity
import com.example.myreseller.ui.activities.ProductPageActivity
import com.example.myreseller.utils.Constants
import com.google.android.material.internal.ContextUtils.getActivity

class AllProductsBuyerRecyclerAdapter(val context: Context, val myList :List<Item>): RecyclerView.Adapter<AllProductsBuyerRecyclerAdapter.ViewHolder>(
) {
    private var count : Int = 0
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): AllProductsBuyerRecyclerAdapter.ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.allproducts_card_layout_buyer,parent,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: AllProductsBuyerRecyclerAdapter.ViewHolder, position: Int) {

        Glide.with(context).load(myList[position].image).into(holder.productImage)

        holder.productName.text = myList[position].name
        holder.productPrice.text = "NGN "+ myList[position].price
        holder.productSize.text = "UK " + myList[position].size

        holder.productImage.setOnClickListener {
            val intent = Intent(context,ProductPageActivity::class.java)
            intent.putExtra(Constants.ITEM_DETAILS, myList[position])
            context.startActivity(intent)

        }

        holder.productLikeButton.setOnClickListener {
            Log.i("message", "clicked")
            val dashboardBuyerActivity : DashboardBuyerActivity = context as DashboardBuyerActivity
            addToLikes(dashboardBuyerActivity,myList[position])
        }
        holder.productAddButton.setOnClickListener {
            Log.i("message", "clicked")
        }


    }

    override fun getItemCount(): Int {
        return myList.size
    }
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var productImage : ImageView
        var productName: TextView
        var productPrice : TextView
        var productSize : TextView
        var productLikeButton: ImageButton
        var productAddButton : ImageButton

        init{
            productImage = itemView.findViewById(R.id.product_image_buyer)
            productName = itemView.findViewById(R.id.product_name_buyer)
            productPrice = itemView.findViewById(R.id.product_price_buyer)
            productSize = itemView.findViewById(R.id.product_size_buyer)
            productLikeButton = itemView.findViewById(R.id.products_like_button)
            productAddButton = itemView.findViewById(R.id.products_add_button)

        }
    }
    private fun addToLikes(activity: DashboardBuyerActivity, item : Item){
        activity.showProgress("Please wait")
        val likes = Likes(item.name,FirestoreClass().getCurrentUserID(), item,++count)
        FirestoreClass().addToLikes(activity, likes)
    }

}