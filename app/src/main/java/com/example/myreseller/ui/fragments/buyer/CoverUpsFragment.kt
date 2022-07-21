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
import com.example.myreseller.databinding.FragmentCoverUpsBinding
import com.example.myreseller.databinding.FragmentTopsBinding
import com.example.myreseller.firestore.FirestoreClass
import com.example.myreseller.models.Item
import com.example.myreseller.utils.Constants
import com.google.firebase.firestore.FirebaseFirestore

/*
 * A simple [Fragment] subclass.
 * Use the [CoverUpsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class CoverUpsFragment : Fragment() {
    private var _binding: FragmentCoverUpsBinding? = null
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
        _binding = FragmentCoverUpsBinding.inflate(inflater, container, false)

        val root: View = binding.root

        binding.rvCoverupsBuyer.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
        }
        fetchData()


        return root
    }

    private fun fetchData(){

        val currentID = FirestoreClass().getCurrentUserID()

        FirebaseFirestore.getInstance().collection(Constants.ITEMS).whereEqualTo("category","Cover-ups").get()
            .addOnSuccessListener { documents ->
                for (document in documents){
                    //model and adapter
                    val item = documents.toObjects(Item::class.java)
                    binding.rvCoverupsBuyer.adapter = AllProductsBuyerRecyclerAdapter(requireContext(),item)
                }

            }
            .addOnFailureListener {
                //toast for error
                Toast.makeText(requireContext(),"An error occurred: ${it.localizedMessage}", Toast.LENGTH_SHORT).show()
            }
    }

}