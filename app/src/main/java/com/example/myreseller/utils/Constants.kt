package com.example.myreseller.utils

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import android.webkit.MimeTypeMap

object Constants {
    const val USERS : String = "users"
    const val ITEMS : String = "items"
    const val LIKES : String = "likes"
    const val CART  : String = "cart"
    const val MYRESELLER_PREFERENCES : String = "MyResellerPrefs"
    const val LOGGED_IN_USERNAME: String = "logged_in_username"
    const val EXTRA_USER_DETAILS: String = "extra_user_details"
    const val ITEM_DETAILS : String = "item_details"
    const val READ_STORAGE_PERMISSION_CODE = 2
    const val PICK_IMAGE_REQUEST_CODE = 1

    const val MALE: String = "male"
    const val FEMALE : String = "female"

    const val MOBILE : String="mobile"
    const val GENDER: String ="gender"
    const val IMAGE: String ="image"
    const val COMPLETE_PROFILE: String = "profileCompleted"

    const val USER_PROFILE_IMAGE: String = "User_profile_image"
    const val ITEM_IMAGE : String = "Item_image"


    fun showImageChooser(activity: Activity){
        // An intent for launching the image selection of phone storage
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        // LAUNCHES THE IMAGE SELECTION OF PHONE STORAGE USING THE CONSTANT CODE
        activity.startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST_CODE)
    }
    fun getFileExtension(activity: Activity, uri: Uri?) : String?{
        return MimeTypeMap.getSingleton().getExtensionFromMimeType(activity.contentResolver.getType(uri!!))
    }



}