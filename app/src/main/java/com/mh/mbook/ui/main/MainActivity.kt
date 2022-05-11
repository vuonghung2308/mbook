package com.mh.mbook.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.mh.mbook.R
import com.mh.mbook.databinding.ActivityMainBinding
import com.mh.mbook.ui.main.book.DetailFragment
import com.mh.mbook.ui.main.dashboard.DashboardFragment
import com.mh.mbook.ui.main.order.fragment.ListOrderFragment
import com.mh.mbook.ui.main.order.fragment.OrderDetailFragment
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class MainActivity : AppCompatActivity(), HasAndroidInjector {
    @Inject
    lateinit var injector: DispatchingAndroidInjector<Any>

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private lateinit var binding: ActivityMainBinding

    val viewModel: MainViewModel
            by viewModels { factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this, R.layout.activity_main
        )
        replaceDashboardFragment()
        viewModel
    }

    private fun replaceDashboardFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, DashboardFragment())
            .commit()
    }

    fun addFragmentBookDetail(id: Long) {
        supportFragmentManager.beginTransaction()
            .add(R.id.container, DetailFragment(id))
            .addToBackStack(null)
            .commit()
    }

    fun addFragmentOrderDetail(id: Long) {
        supportFragmentManager.beginTransaction()
            .add(R.id.container, OrderDetailFragment(id))
            .addToBackStack(null)
            .commit()
    }

    fun addFragmentListOrder() {
        supportFragmentManager.beginTransaction()
            .add(R.id.container, ListOrderFragment())
            .addToBackStack(null)
            .commit()
    }

    override fun androidInjector() = injector
}
