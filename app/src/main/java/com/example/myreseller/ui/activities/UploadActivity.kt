package com.example.myreseller.ui.activities

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.myreseller.R
import com.example.myreseller.databinding.ActivityUploadBinding
import com.example.myreseller.firestore.FirestoreClass
import com.example.myreseller.models.Item
import com.example.myreseller.utils.Constants
import com.example.myreseller.utils.GlideLoader
import com.google.firebase.auth.FirebaseAuth
import java.io.IOException

class UploadActivity : BaseActivity(), AdapterView.OnItemSelectedListener, View.OnClickListener {
    private lateinit var binding : ActivityUploadBinding
    private var mCategory : String = ""
    private var mSelectedImageFileUri : Uri? = null
    private var mItemImageURL : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUploadBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        //set up action bar
        setupActionBar()

        //populate the spinner

        val spinner : Spinner = findViewById(R.id.spinner_upload)
        spinner.onItemSelectedListener = this

        //create an Array Adapter
        ArrayAdapter.createFromResource(this,
        R.array.category_array,
        android.R.layout.simple_spinner_item)
            .also{ adapter ->
                //specify layout to use when the list of choices appears
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                spinner.adapter = adapter
            }
        binding.ivItemPhoto.setOnClickListener(this@UploadActivity)
        binding.btnUpload.setOnClickListener(this@UploadActivity)
    }
    private fun setupActionBar(){
        setSupportActionBar(binding.toolbarUploadActivity)

        val actionBar = supportActionBar
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_black_color_back_24dp)
        }
        // goes back to login screen
        binding.toolbarUploadActivity.setNavigationOnClickListener{
            onBackPressed()
        }
    }

    override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
       // TODO("Not yet implemented")
        //p0?.getItemAtPosition(p2)
        mCategory = p0!!.getItemAtPosition(p2).toString()
    }

    override fun onNothingSelected(p0: AdapterView<*>?) {
       // TODO("Not yet implemented")
    }

    //
    private fun uploadItemDetails(){
        val rands = (0..100).random() //random number from 1 to 100
        if(validateItemDetails()){
            showProgress("Please wait")
            val item = Item(
                binding.etItemName.text.toString().trim{it <= ' '}+rands,
                FirestoreClass().getCurrentUserID(),
                binding.etItemName.text.toString().trim{it <= ' '},
                binding.etItemPrice.text.toString().toDouble(),
                binding.etItemDescription.text.toString().trim {it<=' '},
                binding.etItemSize.text.toString().trim{it <= ' '},
                mItemImageURL,
                mCategory,
            binding.etItemQuantity.text.toString().toLong(),
            binding.etItemColor.text.toString().trim{it<=' '})
            FirestoreClass().uploadItem(this@UploadActivity, item)

        }
    }

    override fun onClick(p0: View?) {
        if(p0 != null){
            when(p0.id){
                R.id.iv_item_photo -> {
                    //Here we will check if the permission is already allowed or we need to request for it
                    //First of all we will check the READ_EXTERNAL_STORAGE permission and if it is not allowed we

                    if(ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                        == PackageManager.PERMISSION_GRANTED){
                        //showErrorSnackBar("You already have storage permission", false)
                        Constants.showImageChooser(this@UploadActivity)
                    } else{

                        /* Requests permissions to be granted to this application. These permissions
                        must be requested in your manifest, they should not be granted to your app
                        and they should have protection level
                         */
                        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                            Constants.READ_STORAGE_PERMISSION_CODE)
                    }

                }

                R.id.btn_upload -> {
                    if(validateItemDetails()){
                        showProgress(resources.getString((R.string.please_wait)))

                        if (mSelectedImageFileUri != null){
                            FirestoreClass().uploadItemImageToCloudStorage(this, mSelectedImageFileUri)

                        }
                    }
                    hideProgressDialog()
                }
            }
        }
    }

    private fun validateItemDetails(): Boolean{
        return when{
            TextUtils.isEmpty(binding.etItemName.text.toString().trim{it <= ' '}) ->{
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_item_name), true)
                false
            }
            TextUtils.isEmpty(binding.etItemDescription.text.toString().trim{it <= ' '} ) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_item_description), true)
                false
            }
            TextUtils .isEmpty(binding.etItemPrice.text.toString().trim{it <= ' '}) ->{
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_item_price), true)
                false
            }
            TextUtils .isEmpty(binding.etItemSize.text.toString().trim{it <= ' '}) ->{
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_item_size), true)
                false
            }
            TextUtils .isEmpty(binding.etItemQuantity.text.toString().trim{it <= ' '}) ->{
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_item_quantity), true)
                false
            }
            TextUtils .isEmpty(binding.etItemColor.text.toString().trim{it <= ' '}) ->{
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_item_color), true)
                false
            }

            else->{
                showErrorSnackBar(resources.getString(R.string.upload_successful), false)
                true
            }

        }
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
                Constants.showImageChooser(this@UploadActivity)
            } else{
                //Display another toast if permission is not granted

                Toast.makeText(this, resources.getString(R.string.read_storage_permission_denied),
                    Toast.LENGTH_LONG).show()
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
                        GlideLoader(this).loadUserPicture(mSelectedImageFileUri!!,binding.ivItemPhoto)

                    }catch(e : IOException){
                        e.printStackTrace()
                        Toast.makeText(this@UploadActivity, resources.getString(R.string.image_selection_failed), Toast.LENGTH_SHORT).show()

                    }
                }
            }
        }else if(resultCode == Activity.RESULT_CANCELED){
            //TODO DO SOMETHING
            Log.e("Request Cancelled", "Image selection cancelled")
        }
    }

    fun imageUploadSuccess(imageURL : String){
        //hideProgressDialog()
        mItemImageURL = imageURL

        //update item details??
        uploadItemDetails()
    }

    fun uploadItemSuccess(){
        //Hide the progress dialog
        hideProgressDialog()

        Toast.makeText(this@UploadActivity, resources.getString(R.string.upload_success), Toast.LENGTH_SHORT).show()
    }


}