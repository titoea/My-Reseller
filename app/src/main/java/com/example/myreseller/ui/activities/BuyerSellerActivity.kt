package com.example.myreseller.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.myreseller.databinding.ActivityBuyerSellerBinding

class BuyerSellerActivity : AppCompatActivity() {
    private lateinit var binding: ActivityBuyerSellerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBuyerSellerBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        //send seller to seller page
        binding.rbSeller.setOnClickListener{
            startActivity(Intent(this@BuyerSellerActivity, DashboardSellerActivity::class.java))
            finish()
        }

        binding.rbBuyer.setOnClickListener {
            startActivity(Intent(this@BuyerSellerActivity, DashboardBuyerActivity::class.java))
            //Toast.makeText(this,"Please pick a category", Toast.LENGTH_SHORT).show()
            finish()
        }
    }


}