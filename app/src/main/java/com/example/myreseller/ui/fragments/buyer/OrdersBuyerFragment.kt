package com.example.myreseller.ui.fragments.buyer

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.myreseller.databinding.FragmentOrdersBuyerBinding


class OrdersBuyerFragment : Fragment() {

  //private lateinit var homeViewModel: HomeViewModel
private var _binding: FragmentOrdersBuyerBinding? = null
  // This property is only valid between onCreateView and
  // onDestroyView.
  private val binding get() = _binding!!

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {
    //homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

    _binding = FragmentOrdersBuyerBinding.inflate(inflater, container, false)
    val root: View = binding.root

    val textView: TextView = binding.textHome
    textView.text = "Orders"
    /*homeViewModel.text.observe(viewLifecycleOwner, Observer {

    })

     */
    return root
  }

override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}