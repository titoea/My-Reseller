package com.example.myreseller.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class Item (
    val id: String = "",
    val userid: String ="",
    val name : String = "",
    val price : Double = 0.00,
    val description : String = "",
    val size : String = "",
    val image : String = "",
    var category : String = "",
    var quantity : Long = 0,
    var color : String = ""
        ): Parcelable


