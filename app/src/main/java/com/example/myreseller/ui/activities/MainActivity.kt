package com.example.myreseller.ui.activities

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.myreseller.databinding.ActivityMainBinding
import com.example.myreseller.utils.Constants


class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sharedPreferences = getSharedPreferences(Constants.MYRESELLER_PREFERENCES, Context.MODE_PRIVATE)
        val username = sharedPreferences.getString(Constants.LOGGED_IN_USERNAME,"")!!

        binding.tvMain.text = "Hello $username."
    }
}