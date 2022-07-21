package com.example.myreseller.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myreseller.R
import com.example.myreseller.adapters.CartRecyclerAdapter
import com.example.myreseller.adapters.ProductsRecyclerAdapter
import com.example.myreseller.databinding.ActivityCartBinding
import com.example.myreseller.firestore.FirestoreClass
import com.example.myreseller.models.Cart
import com.example.myreseller.models.Item
import com.example.myreseller.utils.Constants
import com.google.firebase.firestore.FirebaseFirestore

class CartActivity : AppCompatActivity() {
    private lateinit var binding : ActivityCartBinding
    private var cumAmount =0.00

    private lateinit var cartItemsRecyclerAdapter : CartRecyclerAdapter
    //private lateinit var mRecyclerView : RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupActionBar()
        fetchData()

        //calculate cumAmount
        //binding.tvTotalAmount.text


    }
    private fun setupActionBar(){
        setSupportActionBar(binding.toolbarCartActivity)

        val actionBar = supportActionBar
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_black_color_back_24dp)
        }
        // goes back to login screen
        binding.toolbarCartActivity.setNavigationOnClickListener{
            onBackPressed()
        }
    }

    private  fun  fetchData() {
        var itemList : MutableList<Item> = mutableListOf<Item>()
        val currentID = FirestoreClass().getCurrentUserID()

        FirebaseFirestore.getInstance().collection(Constants.CART).whereEqualTo("userid",currentID).get()
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
                            map?.get("category").toString(),
                            map?.get("quantity") as Long,
                            map?.get("color").toString()
                        )
                        itemList.add(item)
                        Log.i("message: ","added item")


                    //val cart = documents.toObjects(Cart::class.java)

                }

                //Log.i("size here : ", itemList.size.toString())

                cartItemsRecyclerAdapter = CartRecyclerAdapter(this,itemList)

                binding.rvCartItems.adapter = cartItemsRecyclerAdapter

            }
            .addOnFailureListener {
                //toast for error
                Toast.makeText(this,"An error occurred: ${it.localizedMessage}", Toast.LENGTH_SHORT).show()
            }

    }
}