package com.example.myreseller.ui.fragments.seller

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.myreseller.R
//import com.example.myreseller.R
import com.example.myreseller.databinding.FragmentDashboardSellerBinding
import com.example.myreseller.ui.activities.SettingsActivity

class DashboardSellerFragment : Fragment() {

    //private lateinit var dashboardViewModel: DashboardViewModel
    private var _binding: FragmentDashboardSellerBinding? = null

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
        //dashboardViewModel =
           // ViewModelProvider(this).get(DashboardViewModel::class.java)

        _binding = FragmentDashboardSellerBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textDashboard
        textView.text = "This is the dashboard Fragment"
        return root
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.dashboard_seller_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        when(id){
            R.id.action_settings-> {
                // TODO STEP 9: Launch the SettingActivity on click of the action item.
                //START
                startActivity(Intent(activity, SettingsActivity::class.java))
                //END
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}