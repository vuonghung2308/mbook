package com.mh.mbook.ui.main.order.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.mh.mbook.R
import com.mh.mbook.databinding.FragmentListOrderBinding
import com.mh.mbook.di.Injectable
import com.mh.mbook.ui.common.callback
import com.mh.mbook.ui.main.MainActivity
import com.mh.mbook.ui.main.MainViewModel
import com.mh.mbook.ui.main.order.adapter.OrderAdapter
import com.mh.mbook.ui.signin.SignInActivity
import com.mh.mbook.util.AppExecutors
import com.mh.mbook.vo.Status
import javax.inject.Inject

class ListOrderFragment : Fragment(), Injectable {
    @Inject
    lateinit var executors: AppExecutors

    private lateinit var binding: FragmentListOrderBinding
    private val viewModel: MainViewModel
            by viewModels({ activity })

    private val adapter by lazy {
        OrderAdapter(executors) {
            activity.addFragmentOrderDetail(it.id)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_list_order,
            container, false
        )
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setUpSupportActionBar(binding.toolbar)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.orders.adapter = adapter
        binding.signOutCb = callback {
            viewModel.signOut()
            Intent(activity, SignInActivity::class.java)
                .apply { startActivity(this) }
            activity.finish()
        }
        binding.listOrderCb = callback {
            activity.addFragmentListOrder()
        }
        viewModel.orders.observe(viewLifecycleOwner) {
            it.data ?: return@observe
            if(it.status == Status.SUCCESS) {
                adapter.submitList(it.data)
            }
        }
        viewModel.getOrders()
    }

    private fun setUpSupportActionBar(toolbar: Toolbar) {
        activity.apply {
            setSupportActionBar(toolbar)
            toolbar.setNavigationOnClickListener {
                onBackPressed()
            }
            supportActionBar?.apply {
                setDisplayShowTitleEnabled(true)
                setDisplayHomeAsUpEnabled(true)
                title = "Danh sách đơn hàng"
            }
        }
    }

    private val activity: MainActivity
        get() = requireActivity() as MainActivity
}