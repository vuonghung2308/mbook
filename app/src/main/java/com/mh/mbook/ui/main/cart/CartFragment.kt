package com.mh.mbook.ui.main.cart

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.mh.mbook.R
import com.mh.mbook.databinding.FragmentCartDetailBinding
import com.mh.mbook.di.Injectable
import com.mh.mbook.ui.common.callback
import com.mh.mbook.ui.main.MainActivity
import com.mh.mbook.ui.main.MainViewModel
import com.mh.mbook.util.AppExecutors
import com.mh.mbook.vo.Status
import javax.inject.Inject

class CartFragment : Fragment(), Injectable {
    @Inject
    lateinit var executors: AppExecutors
    private lateinit var binding: FragmentCartDetailBinding
    private val viewModel: MainViewModel
            by viewModels({ activity })

    private val adapter by lazy {
        CartItemAdapter(executors) {
            viewModel.removeItem(it.id)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_cart_detail,
            container, false
        )
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setUpSupportActionBar(binding.toolbar)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.cartItems.adapter = adapter
        viewModel.cart.observe(viewLifecycleOwner) {
            it.data ?: return@observe
            if (it.status == Status.SUCCESS) {
                if (it.data.items.isNullOrEmpty()) {
                    binding.linearLayout.visibility = View.INVISIBLE
                } else {
                    binding.cart = it.data
                    adapter.submitList(it.data.items)
                    binding.linearLayout.visibility = View.VISIBLE
                }
            }
        }
        viewModel.remove.observe(viewLifecycleOwner) {
            it.data ?: return@observe
            if (it.status == Status.SUCCESS) {
                viewModel.getCart()
            }
        }
        viewModel.clear.observe(viewLifecycleOwner) {
            it.data ?: return@observe
            if (it.status == Status.SUCCESS) {
                viewModel.getCart()
            }
        }
        viewModel.make.observe(viewLifecycleOwner) {
            it.data ?: return@observe
            if (it.status == Status.SUCCESS) {
                Toast.makeText(
                    activity, "Đặt hàng thành công",
                    Toast.LENGTH_SHORT
                ).show()
                viewModel.getCart()
            }
        }
        binding.orderCb = callback {
            val name = binding.edtName.text?.toString()
            val address = binding.edtAddress.text?.toString()
            val phone = binding.edtPhone.text?.toString()
            if (name.isNullOrEmpty()) {
                Toast.makeText(
                    activity, "Họ và tên không được để trống",
                    Toast.LENGTH_SHORT
                ).show()
                return@callback
            }
            if (phone.isNullOrEmpty()) {
                Toast.makeText(
                    activity, "Số điện thoại không được bỏ trống",
                    Toast.LENGTH_SHORT
                ).show()
                return@callback
            }
            if (address.isNullOrEmpty()) {
                Toast.makeText(
                    activity, "Địa chỉ không được bỏ trống",
                    Toast.LENGTH_SHORT
                ).show()
                return@callback
            }
            viewModel.makeOrder(name, phone, address)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_cart, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.item_delete -> {
                viewModel.clearCart()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    private fun setUpSupportActionBar(toolbar: Toolbar) {
        setHasOptionsMenu(true)
        activity.apply {
            setSupportActionBar(toolbar)
            supportActionBar?.apply {
                setDisplayShowTitleEnabled(true)
                title = "Giỏ hàng"
            }
        }

        var isWhite = true
        var old = 0
//        binding.scrollView.setOnScrollChangeListener(
//            NestedScrollView.OnScrollChangeListener { _, _, y, _, _ ->
//                if (y == 0 && !isWhite) {
//                    binding.divider.setBackgroundResource(R.color.white)
//                    isWhite = true
//                }
//            }
//        )
//        binding.appbarLayout.addOnOffsetChangedListener(
//            AppBarLayout.OnOffsetChangedListener { layout, offset ->
//                if (offset - old > 0 && isWhite) {
//                    binding.divider.setBackgroundResource(R.color.stroke)
//                    isWhite = false
//                }
//                old = offset
//                if (layout.totalScrollRange + offset == 0) {
//                    if (!isWhite) {
//                        binding.divider.setBackgroundResource(R.color.white)
//                        isWhite = true
//                    }
//                }
//            }
//        )
    }

    override fun onResume() {
        activity.viewModel.getCart()
        super.onResume()
    }

    private val activity: MainActivity
        get() = requireActivity() as MainActivity
}