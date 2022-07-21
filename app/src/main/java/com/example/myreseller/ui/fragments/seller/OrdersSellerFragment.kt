package com.example.myreseller.ui.fragments.seller

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
//import com.example.myreseller.R
import com.example.myreseller.databinding.FragmentOrdersSellerBinding

class OrdersSellerFragment : Fragment() {

    //private lateinit var notificationsViewModel: NotificationsViewModel
    private var _binding: FragmentOrdersSellerBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        //notificationsViewModel =
           // ViewModelProvider(this).get(NotificationsViewModel::class.java)

        _binding = FragmentOrdersSellerBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textOrders

        textView.text = "This is the orders fragment"

        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}