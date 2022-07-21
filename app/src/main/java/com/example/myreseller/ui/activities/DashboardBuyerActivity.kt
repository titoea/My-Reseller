package com.example.myreseller.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.myreseller.R
import com.example.myreseller.databinding.ActivityDashboardBuyerBinding
import com.example.myreseller.ui.fragments.buyer.HomeBuyersFragment
import com.example.myreseller.utils.MRButton

class DashboardBuyerActivity : BaseActivity() {
    private lateinit var binding: ActivityDashboardBuyerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDashboardBuyerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navViewBuyer

        val navController = findNavController(R.id.nav_host_fragment_activity_dashboard_buyer)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(setOf(
            R.id.navigation_home, R.id.navigation_buyer_orders, R.id.navigation_notifications))
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        onBackPressed()
        Toast.makeText(this,"Please pick a category", Toast.LENGTH_LONG).show()

    }

    override fun onBackPressed() {
        doubleBackToExit()
    }

    //for adding to likes
    fun addToLikesSuccess(){
        hideProgressDialog()

        Toast.makeText(this@DashboardBuyerActivity, resources.getString(R.string.addToLikes_success), Toast.LENGTH_SHORT).show()
    }

}