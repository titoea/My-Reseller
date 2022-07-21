package com.example.myreseller.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.myreseller.R
import com.example.myreseller.adapters.LikesRecyclerAdapter
import com.example.myreseller.databinding.ActivityLikesBinding
import com.example.myreseller.firestore.FirestoreClass
import com.example.myreseller.models.Item
import com.example.myreseller.utils.Constants
import com.google.firebase.firestore.FirebaseFirestore

class LikesActivity : BaseActivity() {
    private lateinit var binding : ActivityLikesBinding
    private lateinit var likesItemsRecyclerAdapter : LikesRecyclerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLikesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupActionBar()
        fetchData()
    }

    private fun setupActionBar(){
        setSupportActionBar(binding.toolbarLikesActivity)

        val actionBar = supportActionBar
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_black_color_back_24dp)
        }
        // goes back to login screen
        binding.toolbarLikesActivity.setNavigationOnClickListener{
            onBackPressed()
        }
    }

    private  fun  fetchData() {
        var itemList : MutableList<Item> = mutableListOf<Item>()
        val currentID = FirestoreClass().getCurrentUserID()

        FirebaseFirestore.getInstance().collection(Constants.LIKES).whereEqualTo("userid",currentID).get()
            .addOnSuccessListener { documents ->
                for (document in documents){
                    //model and adapter

                    val map : HashMap<String, Any> ? = document.get("item") as HashMap<String, Any>?
                    //Log.i("map id: ", map?.get("id").toString())
                    var item = Item (
                        map?.get("id").toString(),
                        map?.get("userid").toString(),
                        map?.get("name").toString(),
                        map?.get("price") as Double,
                        map?.get("description").toString(),
                        map?.get("size").toString(),
                        map?.get("image").toString(),
                        map?.get("category").toString() ,
                        map?.get("quantity").toString().toLong(),
                        map?.get("color").toString()
                    )
                    itemList.add(item)
                    Log.i("message: ","added item")

                }

                //Log.i("size here : ", itemList.size.toString())

                likesItemsRecyclerAdapter = LikesRecyclerAdapter(this,itemList)

                binding.rvLikedItems.adapter = likesItemsRecyclerAdapter

            }
            .addOnFailureListener {
                //toast for error
                Toast.makeText(this,"An error occurred: ${it.localizedMessage}", Toast.LENGTH_SHORT).show()
            }

    }

}