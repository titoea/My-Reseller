package com.example.myreseller.ui.activities

import android.os.Bundle
import android.text.TextUtils
import android.widget.Toast
import com.example.myreseller.R
import com.example.myreseller.databinding.ActivityRegisterBinding
import com.example.myreseller.firestore.FirestoreClass
import com.example.myreseller.models.User
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

class RegisterActivity : BaseActivity() {
    private lateinit var binding : ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupActionBar()

        //set on click listener for Login text view
        binding.tvLogin.setOnClickListener {
            onBackPressed()
        }
        binding.btnRegister.setOnClickListener{
            registerUser()
        }
    }
    //set the action bar to have a back icon
   private fun setupActionBar(){
       setSupportActionBar(binding.toolbarRegisterActivity)

       val actionBar = supportActionBar
       if(actionBar != null){
           actionBar.setDisplayHomeAsUpEnabled(true)
           actionBar.setHomeAsUpIndicator(R.drawable.ic_black_color_back_24dp)
       }
        // goes back to login screen
       binding.toolbarRegisterActivity.setNavigationOnClickListener{
           onBackPressed()
       }
   }
    // a function to validate new user entries
    private fun validateRegisterDetails(): Boolean{
        return when{
           TextUtils.isEmpty(binding.etFirstName.text.toString().trim{it <= ' '}) ->{
               showErrorSnackBar(resources.getString(R.string.err_msg_enter_first_name), true)
                false
           }
            TextUtils.isEmpty(binding.etLastName.text.toString().trim{it <= ' '}) || binding.etLastName.length() <= 3->{
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_last_name), true)
                false
            }
            TextUtils .isEmpty(binding.etEmail.text.toString().trim{it <= ' '}) ->{
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_email), true)
                false
            }
            TextUtils .isEmpty(binding.etPassword.text.toString().trim{it <= ' '}) ->{
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_password), true)
                false
            }
            TextUtils .isEmpty(binding.etConfirmPassword.text.toString().trim{it <= ' '}) ->{
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_confirm_password), true)
                false
            }
            binding.etPassword.text.toString().trim{ it <= ' '} != binding.etConfirmPassword.text.toString().trim{ it <= ' '} -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_password_and_confirm_password_mismatch), true)
                false
            }
            !binding.cbTermsAndCondition.isChecked -> {
                showErrorSnackBar(resources.getString(R.string.err_msg_agree_terms_and_condition), true)
                false
            }
            else -> {
                //showErrorSnackBar(resources.getString(R.string.registry_successful), false)
                true
            }
        }
    }
    // function to register user
    private fun registerUser(){

        if(validateRegisterDetails()) {

            showProgress(resources.getString(R.string.please_wait))

            val email: String = binding.etEmail.text.toString().trim { it <= ' ' }
            val password: String = binding.etPassword.text.toString().trim { it <= ' ' }

            //Create an instance and create and register a user with email and password

            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(
                    OnCompleteListener<AuthResult> { task ->

                        //if the registration is succesfully done
                        if (task.isSuccessful) {
                            // Firebase registered user
                            val firebaseUser: FirebaseUser = task.result!!.user!!

                            //create new user for firestore

                            val user = User(firebaseUser.uid,
                                binding.etFirstName.text.toString().trim{it <= ' '},
                                binding.etLastName.text.toString().trim{it <= ' '},
                                binding.etEmail.text.toString().trim{it <= ' '},
                            )
                            FirestoreClass().registerUser(this@RegisterActivity ,user)

                            //if registered, sign out i.e close register page
                            //FirebaseAuth.getInstance().signOut()
                            //finish()
                        } else {
                            hideProgressDialog()
                            // if the registering is not successful the show error message
                            showErrorSnackBar(task.exception!!.message.toString(), true)
                        }

                    })
        }

    }
    fun userRegistrationSuccess(){
        //Hide the progress dialog
        hideProgressDialog()

        Toast.makeText(this@RegisterActivity, resources.getString(R.string.register_success), Toast.LENGTH_SHORT).show()
    }
}