package com.mh.mbook.ui.main.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.mh.mbook.R
import com.mh.mbook.databinding.FragmentDashboardBinding
import com.mh.mbook.ui.main.MainActivity

class DashboardFragment : Fragment() {
    private lateinit var binding: FragmentDashboardBinding
    private val dashboardAdapter by lazy {
        DashboardAdapter(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_dashboard,
            container, false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.viewPaper.adapter = dashboardAdapter
        binding.viewPaper.isUserInputEnabled = false
        setHasOptionsMenu(true)
        binding.navigation.setOnItemSelectedListener { item ->
            return@setOnItemSelectedListener when (item.itemId) {
                R.id.item_book -> {
                    binding.viewPaper.currentItem = 0
                    true
                }
                R.id.item_cart -> {
                    binding.viewPaper.currentItem = 1
                    true
                }
                R.id.item_profile -> {
                    binding.viewPaper.currentItem = 2
                    true
                }
                else -> false
            }
        }
    }

    private val activity: MainActivity
        get() = requireActivity() as MainActivity
}