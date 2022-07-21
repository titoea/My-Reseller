package com.example.myreseller.ui.activities

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Handler
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.myreseller.R
import com.google.android.material.snackbar.Snackbar

open class BaseActivity : AppCompatActivity() {
    private var doubleBackToExitPressedOnce = false

    private lateinit var mProgressDialog : Dialog

    //function to display error
    fun showErrorSnackBar(message: String, errorMessage : Boolean){
        //get snackbar
        val snackBar =
            Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)
        //get snack bar view
        val snackBarView = snackBar.view

        // display error color
        if(errorMessage){
            snackBarView.setBackgroundColor(
                ContextCompat.getColor(
                    this@BaseActivity, R.color.colorSnackBarError
                )
            )
        }
        // display success color
        else{
            snackBarView.setBackgroundColor(
                ContextCompat.getColor(
                    this@BaseActivity, R.color.colorSnackBarSuccess
                )
            )
        }
        //show snackbar
        snackBar.show()
    }
    fun showProgress(text : String){
        mProgressDialog = Dialog(this)

        /* Set the screen content from a layout resource
        * The resource will be inflated. addding all top level views to the screen */
        mProgressDialog.setContentView(R.layout.dialog_progress)

        mProgressDialog.findViewById<TextView>(R.id.tv_progress_text).text = text

        mProgressDialog.setCancelable(false)
        mProgressDialog.setCanceledOnTouchOutside(false)

        // Start the dialog and display it on screen
        mProgressDialog.show()

    }
    fun hideProgressDialog(){
        mProgressDialog.dismiss()
    }

    fun doubleBackToExit(){
        if(doubleBackToExitPressedOnce){
            super.onBackPressed()
            return
        }
        this.doubleBackToExitPressedOnce = true
        Toast.makeText(this,resources.getString(R.string.please_click_back_again_to_exit), Toast.LENGTH_SHORT).show()

        @Suppress("DEPRECATION")
        Handler().postDelayed({doubleBackToExitPressedOnce = false}, 2000)
    }
}