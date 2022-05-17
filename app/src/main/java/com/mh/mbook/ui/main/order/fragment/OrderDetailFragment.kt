package com.mh.mbook.ui.main.order.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.mh.mbook.R
import com.mh.mbook.databinding.FragmentOrderDetailBinding
import com.mh.mbook.di.Injectable
import com.mh.mbook.ui.main.MainActivity
import com.mh.mbook.ui.main.MainViewModel
import com.mh.mbook.ui.main.order.adapter.OrderItemAdapter
import com.mh.mbook.util.AppExecutors
import com.mh.mbook.vo.Status
import javax.inject.Inject

class OrderDetailFragment(
    private val id: Long
) : Fragment(), Injectable {
    @Inject
    lateinit var executors: AppExecutors

    private lateinit var binding: FragmentOrderDetailBinding
    private val viewModel: MainViewModel
            by viewModels({ activity })

    private val adapter by lazy {
        OrderItemAdapter(executors) {
            viewModel.removeItem(it.id)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_order_detail,
            container, false
        )
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setUpSupportActionBar(binding.toolbar)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.orderItems.adapter = adapter
        viewModel.order.observe(viewLifecycleOwner) {
            it.data ?: return@observe
            if (it.status == Status.SUCCESS) {
                binding.order = it.data
                adapter.submitList(it.data.items)
                binding.linearLayout.visibility = View.VISIBLE
            }
        }
        viewModel.getOrder(id)
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
                title = "Chi tiết đơn hàng"
            }
        }
    }

    private val activity: MainActivity
        get() = requireActivity() as MainActivity
}