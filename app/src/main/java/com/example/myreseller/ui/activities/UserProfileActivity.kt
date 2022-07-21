package com.example.myreseller.ui.activities

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.myreseller.R
import com.example.myreseller.databinding.ActivityUserProfileBinding
import com.example.myreseller.firestore.FirestoreClass
import com.example.myreseller.models.User
import com.example.myreseller.utils.Constants
import com.example.myreseller.utils.GlideLoader
import java.io.IOException

class UserProfileActivity : BaseActivity(), View.OnClickListener {
    private lateinit var mUserDetails: User
    private var mSelectedImageFileUri : Uri? = null
    private var mUserProfileImageURL: String = ""
    private lateinit var binding : ActivityUserProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserProfileBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        mUserDetails = User()

        if(intent.hasExtra(Constants.EXTRA_USER_DETAILS)){
            mUserDetails = intent.getParcelableExtra(Constants.EXTRA_USER_DETAILS)!!
        }
        // set first name, last name and email to regisered name
        binding.etFirstName.isEnabled = false
        binding.etFirstName.setText(mUserDetails.firstName)

        binding.etLastName.isEnabled = false
        binding.etLastName.setText(mUserDetails.lastName)

        binding.etEmail.isEnabled = false
        binding.etEmail.setText(mUserDetails.email)

        binding.ivUserPhoto.setOnClickListener(this@UserProfileActivity)
        binding.btnSubmit.setOnClickListener(this@UserProfileActivity)


    }

    override fun onClick(p0: View?) {
        if(p0 != null){
            when(p0.id){
                R.id.iv_user_photo -> {
                    //Here we will check if the permission is already allowed or we need to request for it
                    //First of all we will check the READ_EXTERNAL_STORAGE permission and if it is not allowed we

                    if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_GRANTED){
                        //showErrorSnackBar("You already have storage permission", false)
                        Constants.showImageChooser(this@UserProfileActivity)
                    } else{

                        /* Requests permissions to be granted to this application. These permissions
                        must be requested in your manifest, they should not be granted to your app
                        and they should have protection level
                         */
                        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                            Constants.READ_STORAGE_PERMISSION_CODE)
                    }

                }

                R.id.btn_submit -> {
                    if(validateUserProfileDetails()){
                        showProgress(resources.getString((R.string.please_wait)))

                        if (mSelectedImageFileUri != null){
                            FirestoreClass().uploadImageToCloudStorage(this, mSelectedImageFileUri)
                        }else{
                            updateUserProfileDetails()
                        }
                    }
                }
            }
        }
    }

    private fun updateUserProfileDetails(){
        val userHashMap = HashMap<String,Any>()

        val mobileNumber = binding.etMobileNumber.text.toString().trim{it <= ' '}

        val gender = if (binding.rbMale.isChecked){
            Constants.MALE
        } else{
            Constants.FEMALE
        }
        if(mUserProfileImageURL.isNotEmpty()){
            userHashMap[Constants.IMAGE] = mUserProfileImageURL
        }

        if(mobileNumber.isNotEmpty()){
            userHashMap[Constants.MOBILE] = mobileNumber.toLong()
        }
        userHashMap[Constants.GENDER] = gender

        userHashMap[Constants.COMPLETE_PROFILE] =  1

        //showProgress(resources.getString(R.string.please_wait))

        // update user profile data
        FirestoreClass().updateUserProfileData(this,userHashMap)

        //showErrorSnackBar("Your details are valid. You can update them", false)

    }

    fun userProfileUpdateSuccess(){
        hideProgressDialog()

        Toast.makeText(this@UserProfileActivity, resources.getString(R.string.msg_profile_update_success), Toast.LENGTH_SHORT).show()

        startActivity(Intent(this@UserProfileActivity, BuyerSellerActivity::class.java))
        finish()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == Constants.READ_STORAGE_PERMISSION_CODE){
            if(grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                //showErrorSnackBar("The storage permission is granted", false)
                Constants.showImageChooser(this@UserProfileActivity)
            } else{
                //Display another toast if permission is not granted

                Toast.makeText(this, resources.getString(R.string.read_storage_permission_denied),Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(resultCode == Activity.RESULT_OK){
            if(requestCode == Constants.PICK_IMAGE_REQUEST_CODE){
                if (data != null){
                    try {
                        // the uri of selected image from the phone storage
                         mSelectedImageFileUri = data.data!!

                        //binding.ivUserPhoto.setImageURI(selectedImageFileUri)
                        GlideLoader(this).loadUserPicture(mSelectedImageFileUri!!,binding.ivUserPhoto)

                    }catch(e : IOException){
                        e.printStackTrace()
                        Toast.makeText(this@UserProfileActivity, resources.getString(R.string.image_selection_failed), Toast.LENGTH_SHORT).show()

                    }
                }
            }
        }else if(resultCode == Activity.RESULT_CANCELED){
            //TODO DO SOMETHING
            Log.e("Request Cancelled", "Image selection cancelled")
        }
    }

    private fun validateUserProfileDetails(): Boolean {
        return when{
            // must enter mobile number
            TextUtils.isEmpty(binding.etMobileNumber.text.toString().trim {it <= ' '}) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_mobile_number), true)
                false
            }
            else ->{
                true
            }
        }
    }

    fun imageUploadSuccess(imageURL : String){
        //hideProgressDialog()
        mUserProfileImageURL = imageURL

        //update user details
        updateUserProfileDetails()


    }
}