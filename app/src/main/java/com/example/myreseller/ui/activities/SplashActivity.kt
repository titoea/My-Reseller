package com.example.myreseller.ui.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.myreseller.databinding.ActivitySplashBinding


class SplashActivity : AppCompatActivity() {
    private lateinit var binding : ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)


        //Research an alternative
        @Suppress("DEPRECATION")
        Handler().postDelayed({
            startActivity(Intent(this@SplashActivity, LoginActivity::class.java))
            finish()
        },2500)
    }
    /* Use in fonts class created
    val typeface: Typeface = Typeface.createFromAsset(assets, "Montserrat-Bold.ttf")
    tvappname.typeface = typeface

     */

}