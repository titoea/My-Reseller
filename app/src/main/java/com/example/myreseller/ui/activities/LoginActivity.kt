package com.example.myreseller.ui.activities

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import com.example.myreseller.R
import com.example.myreseller.databinding.ActivityLoginBinding
import com.example.myreseller.firestore.FirestoreClass
import com.example.myreseller.models.User
import com.example.myreseller.utils.Constants
import com.google.firebase.auth.FirebaseAuth


class LoginActivity : BaseActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        //Click event assigned to Forgot Password text
        binding.tvForgotPassword.setOnClickListener{
            val intent = Intent(this@LoginActivity, ForgotPasswordActivity::class.java)
            startActivity(intent)
        }
        binding.btnLogin.setOnClickListener{
            loginRegisteredUser()
        }
        binding.tvRegister.setOnClickListener{
            val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
            startActivity(intent)
        }

    }
    fun userLoggedInSuccess(user: User){
        //Hide the progress dialog
        hideProgressDialog()

        //Print the user details in the log as of now
        Log.i("First Name: ", user.firstName)
        Log.i("Last Name: ", user.lastName)
        Log.i("Email: ", user.email)

        if (user.profileCompleted == 0){

            val intent =  Intent(this@LoginActivity, UserProfileActivity::class.java)
            intent.putExtra(Constants.EXTRA_USER_DETAILS, user)
            startActivity(intent)
        }
        else{
            // Redirect the user to Main Screen after log in and profile is complete
            startActivity(Intent(this@LoginActivity, BuyerSellerActivity::class.java))
        }
        finish()

    }
    //NOT WORKING
    /*
    override fun onClick(view: View?){
       if(view != null){
           when(view.id){

               R.id.tv_forgot_password -> {

               }
               R.id.btn_login ->{
                   // call the validate function
                   //START
                   loginRegisteredUser()
                   //END
               }
               R.id.tv_register ->{
                   //launch the register screen when the user clicks on the register text
                   val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
                   startActivity(intent)
               }
           }
       }
    } */

    private fun validateLoginDetails():Boolean{
        return when{
            TextUtils.isEmpty(binding.etEmail.text.toString().trim{ it <= ' '}) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_email), true)
                false
            }
            TextUtils.isEmpty(binding.etPassword.text.toString().trim{ it <= ' '}) -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_password), true)
                false
            }else -> {
                true
            }

        }
    }

    private fun loginRegisteredUser(){
        if(validateLoginDetails()){
            //show the progress dialog
            showProgress(resources.getString(R.string.please_wait))

            //Get the text from the editText and trim the space
            val email = binding.etEmail.text.toString().trim{it <= ' '}
            val password = binding.etPassword.text.toString().trim{it <= ' '}

            // Login using firebase auth

            FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password).addOnCompleteListener{
                task ->

                if(task.isSuccessful){
                    //TODO -Send user to Home page
                    FirestoreClass().getUserDetails(this@LoginActivity)
                }else{
                    hideProgressDialog()
                    showErrorSnackBar(task.exception!!.message.toString(), true)
                }
            }

        }
    }

}