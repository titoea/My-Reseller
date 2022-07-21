package com.example.myreseller.ui.fragments.seller

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myreseller.R
import com.example.myreseller.adapters.ProductsRecyclerAdapter
//import com.example.myreseller.R
import com.example.myreseller.databinding.FragmentProductsBinding
import com.example.myreseller.firestore.FirestoreClass
import com.example.myreseller.models.Item
import com.example.myreseller.ui.activities.UploadActivity
import com.example.myreseller.utils.Constants
import com.google.firebase.firestore.FirebaseFirestore


class ProductsFragment : Fragment() {

    //private lateinit var homeViewModel: HomeViewModel
    private var _binding: FragmentProductsBinding? = null
    private lateinit var myList : List<Item>
    private lateinit var productsRecyclerAdapter : ProductsRecyclerAdapter
    private lateinit var mRecyclerView : RecyclerView

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        //homeViewModel =
           // ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentProductsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        //val textView: TextView = binding.textHome

        //textView.text = "This is the Products fragment"

        binding.rvProducts.apply {
            layoutManager = GridLayoutManager(requireContext(), 2)
        }
        fetchData()

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.products_seller_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        when(id){
            R.id.action_upload-> {
                // TODO STEP 9: Launch the UploadActivity on click of the action item.
                //START
                startActivity(Intent(activity, UploadActivity::class.java))
                //END
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
    private fun fetchData(){

        val currentID = FirestoreClass().getCurrentUserID()

        FirebaseFirestore.getInstance().collection(Constants.ITEMS).whereEqualTo("userid",currentID).get()
            .addOnSuccessListener { documents ->
                for (document in documents){
                    //model and adapter
                    val item = documents.toObjects(Item::class.java)
                    binding.rvProducts.adapter = ProductsRecyclerAdapter(requireContext(),item)
                }

            }
            .addOnFailureListener {
                //toast for error
                Toast.makeText(requireContext(),"An error occurred: ${it.localizedMessage}",Toast.LENGTH_SHORT).show()
            }
    }
}