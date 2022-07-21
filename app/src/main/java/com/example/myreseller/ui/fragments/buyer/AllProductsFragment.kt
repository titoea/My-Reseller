package com.example.myreseller.ui.fragments.buyer

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myreseller.R
import com.example.myreseller.adapters.AllProductsBuyerRecyclerAdapter
import com.example.myreseller.adapters.ProductsRecyclerAdapter
import com.example.myreseller.databinding.FragmentAllProductsBinding
import com.example.myreseller.databinding.FragmentProductsBinding
import com.example.myreseller.firestore.FirestoreClass
import com.example.myreseller.models.Item
import com.example.myreseller.utils.Constants
import com.example.myreseller.utils.MRButton
import com.google.firebase.firestore.FirebaseFirestore


class AllProductsFragment : Fragment() {
    private var _binding: FragmentAllProductsBinding? = null
    private lateinit var myList : List<Item>
    private lateinit var productsRecyclerAdapter : AllProductsBuyerRecyclerAdapter
    private lateinit var mRecyclerView : RecyclerView

    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAllProductsBinding.inflate(inflater, container, false)

        val root: View = binding.root

        binding.rvProductsBuyer.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
        }
        fetchData()


        return root

    }

    private fun fetchData(){

        val currentID = FirestoreClass().getCurrentUserID()

        FirebaseFirestore.getInstance().collection(Constants.ITEMS).get()
            .addOnSuccessListener { documents ->
                for (document in documents){
                    //model and adapter
                    val item = documents.toObjects(Item::class.java)
                    val allProductsRecyclerAdapter = AllProductsBuyerRecyclerAdapter(requireContext(),item)
                    binding.rvProductsBuyer.adapter =  allProductsRecyclerAdapter

                }

            }
            .addOnFailureListener {
                //toast for error
                Toast.makeText(requireContext(),"An error occurred: ${it.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
    }

}