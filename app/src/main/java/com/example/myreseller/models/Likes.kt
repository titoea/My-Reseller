package com.example.myreseller.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class Likes (
    val id: String="",
    val userid: String= "",
    val item : Item,
    val count : Int = 0 ):Parcelable

