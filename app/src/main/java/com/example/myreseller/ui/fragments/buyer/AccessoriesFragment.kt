package com.example.myreseller.ui.fragments.buyer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myreseller.R
import com.example.myreseller.adapters.AllProductsBuyerRecyclerAdapter
import com.example.myreseller.databinding.FragmentAccessoriesBinding
import com.example.myreseller.databinding.FragmentTopsBinding
import com.example.myreseller.firestore.FirestoreClass
import com.example.myreseller.models.Item
import com.example.myreseller.utils.Constants
import com.google.firebase.firestore.FirebaseFirestore


class AccessoriesFragment : Fragment() {
    private var _binding: FragmentAccessoriesBinding? = null
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
        _binding = FragmentAccessoriesBinding.inflate(inflater, container, false)

        val root: View = binding.root

        binding.rvAccessoriesBuyer.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
        }
        fetchData()


        return root
    }

    private fun fetchData(){

        val currentID = FirestoreClass().getCurrentUserID()

        FirebaseFirestore.getInstance().collection(Constants.ITEMS).whereEqualTo("category","Accessories").get()
            .addOnSuccessListener { documents ->
                for (document in documents){
                    //model and adapter
                    val item = documents.toObjects(Item::class.java)
                    binding.rvAccessoriesBuyer.adapter = AllProductsBuyerRecyclerAdapter(requireContext(),item)
                }

            }
            .addOnFailureListener {
                //toast for error
                Toast.makeText(requireContext(),"An error occurred: ${it.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
    }
}