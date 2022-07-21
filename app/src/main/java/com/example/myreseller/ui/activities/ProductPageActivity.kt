package com.example.myreseller.ui.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.myreseller.R
import com.example.myreseller.databinding.ActivityProductPageBinding
import com.example.myreseller.firestore.FirestoreClass
import com.example.myreseller.models.Cart
import com.example.myreseller.models.Item
import com.example.myreseller.models.Likes
import com.example.myreseller.utils.Constants
import com.google.firebase.firestore.FirebaseFirestore

class ProductPageActivity : BaseActivity() {
    private var count : Int = 0
    private lateinit var binding : ActivityProductPageBinding
    private lateinit var mProduct : Item
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductPageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupActionBar()
        mProduct = Item()

        //get intent extra
        if(intent.hasExtra(Constants.ITEM_DETAILS)){
            mProduct = intent.getParcelableExtra(Constants.ITEM_DETAILS)!!
        }

        //set text for toolbar
        binding.tvProductName.setText(mProduct.name)

        //load image using into image view using glide
        val imagview : ImageView = findViewById<ImageView>(R.id.pp_product_image_buyer)
        Glide.with(applicationContext)
            .load(mProduct.image)
            .into(imagview)

        //set text for description
        binding.ppTvProductDescriptionBuyer.setText(mProduct.description)

        //set text for size
        binding.ppTvProductSizeBuyer.setText("Size: UK "+ mProduct.size)

        //set text for price
        binding.ppTvProductPriceBuyer.setText("Price: NGN "+ mProduct.price)

        //set text for quantity
        binding.ppTvProductQuantityBuyer.setText("Quantity: "+ mProduct.quantity)

        //set text for color
        binding.ppTvProductColorBuyer.setText("Color: "+ mProduct.color)

        binding.btnAddToCart.setOnClickListener{
            showProgress("Please wait")
            FirebaseFirestore.getInstance().collection(Constants.CART).get()
                .addOnSuccessListener { documents ->
                    for (document in documents){
                       if (document.id == mProduct.name){
                           Toast.makeText(this@ProductPageActivity, resources.getString(R.string.already_added_to_cart), Toast.LENGTH_SHORT).show()
                       }
                        else{
                            // item not in cart
                           addToCart(mProduct)
                       }
                    }
                }
                .addOnFailureListener {
                    Toast.makeText(this,"An error occurred: ${it.localizedMessage}", Toast.LENGTH_SHORT).show()
                }

        }


    }

    private fun setupActionBar(){
        setSupportActionBar(binding.toolbarProductPageActivity)

        val actionBar = supportActionBar
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_black_color_back_24dp)
        }
        // goes back to login screen
        binding.toolbarProductPageActivity.setNavigationOnClickListener{
            onBackPressed()
        }
    }
    private fun addToCart(item: Item){
        val cart = Cart(item.name, FirestoreClass().getCurrentUserID(), item,++count)
        FirestoreClass().addToCart(this,cart)
    }
    fun addToCartSuccess(){
        hideProgressDialog()

        Toast.makeText(this@ProductPageActivity, resources.getString(R.string.addToCart_success), Toast.LENGTH_SHORT).show()
    }
}