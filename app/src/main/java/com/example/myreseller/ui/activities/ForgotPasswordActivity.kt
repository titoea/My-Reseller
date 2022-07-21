package com.example.myreseller.ui.activities

import android.os.Bundle
import android.widget.Toast
import com.example.myreseller.R
import com.example.myreseller.databinding.ActivityForgotPasswordBinding
import com.google.firebase.auth.FirebaseAuth

class ForgotPasswordActivity : BaseActivity() {
    private lateinit var binding : ActivityForgotPasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //set up back button
        setupActionBar()
    }

    private fun setupActionBar(){
        setSupportActionBar(binding.toolbarForgotPasswordActivity)

        val actionBar = supportActionBar
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.title = ""
            actionBar.setHomeAsUpIndicator(R.drawable.ic_black_color_back_24dp)
        }
        // goes back to login screen
        binding.toolbarForgotPasswordActivity.setNavigationOnClickListener{
            onBackPressed()
        }
        binding.btnSubmit.setOnClickListener {
            val email: String = binding.etEmailForgotPwd.text.toString().trim{it <= ' '}
            if(email.isEmpty()){
                showErrorSnackBar(resources.getString(R.string.err_msg_enter_email), true)
            }
            else{
                showProgress(resources.getString(R.string.please_wait))
                FirebaseAuth.getInstance().sendPasswordResetEmail(email).addOnCompleteListener{
                    task->
                    hideProgressDialog()
                    if(task.isSuccessful){
                        // show the toast message and finish the forgot password
                        Toast.makeText(
                            this@ForgotPasswordActivity, resources.getString(R.string.email_sent_success),
                            Toast.LENGTH_LONG
                        ).show()

                        finish()
                    }else{
                        showErrorSnackBar(task.exception!!.message.toString(),true)
                    }
                }
            }
        }
    }

}