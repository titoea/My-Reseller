package com.example.myreseller.firestore

import android.app.Activity
import android.content.Context
import android.content.SharedPreferences
import android.net.Uri
import android.util.Log
import com.example.myreseller.models.Cart
import com.example.myreseller.models.Item
import com.example.myreseller.models.Likes
import com.example.myreseller.models.User
import com.example.myreseller.ui.activities.*
import com.example.myreseller.utils.Constants
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference

class FirestoreClass {

    private val mFirestore = FirebaseFirestore.getInstance()

    fun registerUser(activity: RegisterActivity, userInfo: User) {
        // The "users" is the collection name. If the collection is already created
        //then it will not create the same one
        mFirestore.collection(Constants.USERS)
            //Document ID for users fields. Here the document it is the User id.
            .document(userInfo.id)
            //Here the userInfo are field and the SetOption is set to merge. It is for if we want to merge later on instead of replacing the fields
            .set(userInfo, SetOptions.merge())
            .addOnSuccessListener {
                //Here call a function of base activity for transferring the result to it
                activity.userRegistrationSuccess()
            }
            .addOnFailureListener { e ->
                activity.hideProgressDialog()
                Log.e(
                    activity.javaClass.simpleName,
                    "Error while registering user.", e
                )
            }
    }
    fun uploadItem(activity: UploadActivity, itemInfo: Item) {
        // The "users" is the collection name. If the collection is already created
        //then it will not create the same one
        mFirestore.collection(Constants.ITEMS)
            //Document ID for users fields. Here the document it is the User id.
            .document(itemInfo.id)
            //Here the userInfo are field and the SetOption is set to merge. It is for if we want to merge later on instead of replacing the fields
            .set(itemInfo, SetOptions.merge())
            .addOnSuccessListener {
                //Here call a function of base activity for transferring the result to it
                activity.uploadItemSuccess()
            }
            .addOnFailureListener { e ->
                activity.hideProgressDialog()
                Log.e(
                    activity.javaClass.simpleName,
                    "Error while uploading item.", e
                )
            }
    }
    fun addToLikes(activity: DashboardBuyerActivity, likesInfo : Likes){
        // The "users" is the collection name. If the collection is already created
        //then it will not create the same one
        mFirestore.collection(Constants.LIKES)
            //Document ID for users fields. Here the document it is the User id.
            .document(likesInfo.id)
            //Here the userInfo are field and the SetOption is set to merge. It is for if we want to merge later on instead of replacing the fields
            .set(likesInfo, SetOptions.merge())
            .addOnSuccessListener {
                //Here call a function of base activity for transferring the result to it
                activity.addToLikesSuccess()
            }
            .addOnFailureListener { e ->
                activity.hideProgressDialog()
                Log.e(
                    activity.javaClass.simpleName,
                    "Error while adding to likes.", e
                )
            }
    }

        fun addToCart(activity: ProductPageActivity, cartInfo: Cart){
        mFirestore.collection(Constants.CART).
        document(cartInfo.id)
            .set(cartInfo, SetOptions.merge())
            .addOnSuccessListener {
                activity.addToCartSuccess()
            }
            .addOnFailureListener { e ->
                activity.hideProgressDialog()
                Log.e(
                    activity.javaClass.simpleName,
                    "Error while adding to cart.", e
                )
            }
    }



    fun getCurrentUserID(): String {
        //An instance of currentUser using FirebaseAuth
        val currentUser = FirebaseAuth.getInstance().currentUser

        //A variable to assign the currentUserId if it is not null or else it will be blank.

        var currentUserId = ""
        if (currentUser != null) {
            currentUserId = currentUser.uid
        }
        return currentUserId
    }

    fun getUserDetails(activity: Activity) {
        //Here we pass the collection name from which we want the data
        mFirestore.collection(Constants.USERS)
            //The document id to get the fielss of user
            .document(getCurrentUserID())
            .get()
            .addOnSuccessListener { document ->
                Log.i(activity.javaClass.simpleName, document.toString())

                //Here we have received the document snapshot which is converted into the User Data model object

                val user = document.toObject(User::class.java)!!

                //create shared preferences object
                val sharedPreferences =
                    activity.getSharedPreferences(Constants.MYRESELLER_PREFERENCES,
                        Context.MODE_PRIVATE)

                val editor: SharedPreferences.Editor = sharedPreferences.edit()
                //Key: logged_in_username
                //Value : firstname lastname
                editor.putString(
                    Constants.LOGGED_IN_USERNAME,
                    "${user.firstName} ${user.lastName}"
                )
                editor.apply()


                //START
                when (activity) {
                    is LoginActivity -> {
                        activity.userLoggedInSuccess(user)
                    }
                }
                //END
            }
            .addOnFailureListener { e ->

            }

    }

    fun updateUserProfileData(activity: Activity, userHashMap: HashMap<String, Any>) {
        mFirestore.collection(Constants.USERS)
            .document(getCurrentUserID())
            .update(userHashMap)
            .addOnSuccessListener {
                when (activity) {
                    is UserProfileActivity -> {
                        //Hide progress dialog and send user to main activity
                        activity.userProfileUpdateSuccess()
                    }
                }

            }
            .addOnFailureListener { e ->
                when (activity) {
                    is UserProfileActivity -> {
                        // Hide the progress dialog if there is any error. nd print the error in log
                        activity.hideProgressDialog()
                    }
                }
                Log.e(activity.javaClass.simpleName, "Error while updating the user details.", e)
            }
    }

    fun uploadImageToCloudStorage(activity: Activity, imageFileURI: Uri?) {
        val sRef: StorageReference = FirebaseStorage.getInstance().reference.child(
            Constants.USER_PROFILE_IMAGE + System.currentTimeMillis() + "." + Constants.getFileExtension(
                activity,
                imageFileURI))

        sRef.putFile(imageFileURI!!)
            .addOnSuccessListener { taskSnapshot ->
                // The image upload is successful
                Log.e("Firebase Image Url",
                    taskSnapshot.metadata!!.reference!!.downloadUrl.toString()
                )
                //get the downloadable url from the task snapshot
                taskSnapshot.metadata!!.reference!!.downloadUrl.addOnSuccessListener { uri ->
                    Log.e("Download Image URL", uri.toString())
                    when (activity){
                        is UserProfileActivity -> {
                            activity.imageUploadSuccess(uri.toString())
                        }
                    }
                }

            }
            .addOnFailureListener { exception ->
                //Hide progress dialog if there is any error, print the error in the log
                when (activity) {
                    is UserProfileActivity -> {
                        activity.hideProgressDialog()

                        Log.e(
                            activity.javaClass.simpleName, exception.message, exception
                        )

                    }
                }
            }
    }

    fun uploadItemImageToCloudStorage(activity: Activity, imageFileURI: Uri?) {
        val sRef: StorageReference = FirebaseStorage.getInstance().reference.child(
            Constants.ITEM_IMAGE + System.currentTimeMillis() + "." + Constants.getFileExtension(
                activity,
                imageFileURI))

        sRef.putFile(imageFileURI!!)
            .addOnSuccessListener { taskSnapshot ->
                // The image upload is successful
                Log.e("Firebase Image Url",
                    taskSnapshot.metadata!!.reference!!.downloadUrl.toString()
                )
                //get the downloadable url from the task snapshot
                taskSnapshot.metadata!!.reference!!.downloadUrl.addOnSuccessListener { uri ->
                    Log.e("Download Image URL", uri.toString())
                    when (activity){
                        is UploadActivity -> {
                            activity.imageUploadSuccess(uri.toString())
                        }
                    }
                }

            }
            .addOnFailureListener { exception ->
                //Hide progress dialog if there is any error, print the error in the log
                when (activity) {
                    is UserProfileActivity -> {
                        activity.hideProgressDialog()

                        Log.e(
                            activity.javaClass.simpleName, exception.message, exception
                        )

                    }
                }
            }
    }
}
