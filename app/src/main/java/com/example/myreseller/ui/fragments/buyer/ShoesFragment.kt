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
import com.example.myreseller.databinding.FragmentShoesBinding
import com.example.myreseller.databinding.FragmentTopsBinding
import com.example.myreseller.firestore.FirestoreClass
import com.example.myreseller.models.Item
import com.example.myreseller.utils.Constants
import com.google.firebase.firestore.FirebaseFirestore

/**
 * A simple [Fragment] subclass.
 * Use the [ShoesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ShoesFragment : Fragment() {
    private var _binding: FragmentShoesBinding? = null
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
        _binding = FragmentShoesBinding.inflate(inflater, container, false)

        val root: View = binding.root

        binding.rvShoesBuyer.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
        }
        fetchData()


        return root
    }

    private fun fetchData(){

        val currentID = FirestoreClass().getCurrentUserID()

        FirebaseFirestore.getInstance().collection(Constants.ITEMS).whereEqualTo("category","Shoes").get()
            .addOnSuccessListener { documents ->
                for (document in documents){
                    //model and adapter
                    val item = documents.toObjects(Item::class.java)
                    binding.rvShoesBuyer.adapter = AllProductsBuyerRecyclerAdapter(requireContext(),item)
                }

            }
            .addOnFailureListener {
                //toast for error
                Toast.makeText(requireContext(),"An error occurred: ${it.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
    }

}