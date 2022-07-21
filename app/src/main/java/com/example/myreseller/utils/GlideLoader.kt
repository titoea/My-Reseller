package com.example.myreseller.utils

import android.content.Context
import android.net.Uri
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.myreseller.R
import java.io.IOException

class GlideLoader(val context : Context){
    fun loadUserPicture(imageURI : Uri, imageView : ImageView){
        try{
            //Load the user image in the imageview
            Glide
                .with(context)
                .load(Uri.parse(imageURI.toString())) //URI of the image
                .centerCrop()
                .placeholder(R.drawable.ic_user_placeholder) // a default placeholder if image failed to load
                .into(imageView) // the view in which the image will be loaded

        } catch(e : IOException){
            e.printStackTrace()
        }
    }

}