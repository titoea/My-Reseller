package com.example.myreseller.ui.fragments.buyer

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.myreseller.R
import com.example.myreseller.databinding.FragmentHomeBuyerBinding
import com.example.myreseller.ui.activities.CartActivity
import com.example.myreseller.ui.activities.LikesActivity

class HomeBuyersFragment : Fragment() {

  //private lateinit var dashboardViewModel: DashboardViewModel
private var _binding: FragmentHomeBuyerBinding? = null
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
        savedInstanceState: Bundle?
      ): View? {
        //dashboardViewModel = ViewModelProvider(this).get(DashboardViewModel::class.java)

        _binding = FragmentHomeBuyerBinding.inflate(inflater, container, false)
        val root: View = binding.root
        /*
        val textView: TextView = root.findViewById(R.id.text_dashboard)
        textView.text = "Home"
         TextView = binding.textDashboard
        dashboardViewModel.text.observe(viewLifecycleOwner, Observer {
          textView.text = it
        })
         */

        val tv_all = root.findViewById<TextView>(R.id.tv_products_all_buyer)
        tv_all.setOnClickListener{
            val allproductsfragment = AllProductsFragment()
            val transaction : FragmentTransaction = activity!!.supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragmentview_buyer_home, allproductsfragment)
            transaction.commit()
        }
        val tv_tops = root.findViewById<TextView>(R.id.tv_products_tops_buyer)
        tv_tops.setOnClickListener {
            val topsproductsfragment = TopsFragment()
            val transaction : FragmentTransaction = activity!!.supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragmentview_buyer_home,topsproductsfragment)
            transaction.commit()
        }
        val tv_bottoms = root.findViewById<TextView>(R.id.tv_products_bottoms_buyer)
        tv_bottoms.setOnClickListener {
            val bottomsproductfragment = BottomsFragment()
            val transaction : FragmentTransaction = activity!!.supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragmentview_buyer_home,bottomsproductfragment)
            transaction.commit()
        }
          val tv_dresses = root.findViewById<TextView>(R.id.tv_products_dresses_buyer)
          tv_dresses.setOnClickListener {
              val dressesproductfragment = DressesFragment()
              val transaction : FragmentTransaction = activity!!.supportFragmentManager.beginTransaction()
              transaction.replace(R.id.fragmentview_buyer_home,dressesproductfragment)
              transaction.commit()
          }
          val tv_shoes = root.findViewById<TextView>(R.id.tv_products_shoes_buyer)
          tv_shoes.setOnClickListener {
              val shoesproductfragment = ShoesFragment()
              val transaction : FragmentTransaction = activity!!.supportFragmentManager.beginTransaction()
              transaction.replace(R.id.fragmentview_buyer_home,shoesproductfragment)
              transaction.commit()
          }
          val tv_accessories = root.findViewById<TextView>(R.id.tv_products_accessories_buyer)
          tv_accessories.setOnClickListener {
              val accessoriesproductfragment = AccessoriesFragment()
              val transaction : FragmentTransaction = activity!!.supportFragmentManager.beginTransaction()
              transaction.replace(R.id.fragmentview_buyer_home,accessoriesproductfragment)
              transaction.commit()
          }
          val tv_coverups = root.findViewById<TextView>(R.id.tv_products_coverups_buyer)
          tv_coverups.setOnClickListener {
              val coverupsproductfragment = CoverUpsFragment()
              val transaction : FragmentTransaction = activity!!.supportFragmentManager.beginTransaction()
              transaction.replace(R.id.fragmentview_buyer_home,coverupsproductfragment)
              transaction.commit()
          }

        return root
      }

    override fun onDestroyView() {
            super.onDestroyView()
            _binding = null
        }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.buyer_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        when(id){
            R.id.action_view_likes-> {
                // TODO STEP 9: Launch the UploadActivity on click of the action item.
                //START
                startActivity(Intent(activity, LikesActivity::class.java))
                //END
                return true
            }
            R.id.action_view_cart->{
                startActivity(Intent(activity, CartActivity::class.java))
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}