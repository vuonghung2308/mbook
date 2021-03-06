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
import com.mh.mbook.api.response.ItemResponse
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
        CartItemAdapter(
            executors,
            { i -> viewModel.removeItem(i.id) },
            { i, q -> viewModel.updateItem(i, q) }
        )
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
                binding.cart = it.data
                adapter.submitList(it.data.items)
                if (it.data.items.isNullOrEmpty()) {
                    binding.tvEmpty.visibility = View.VISIBLE
                } else {
                    binding.tvEmpty.visibility = View.INVISIBLE
                }
            }
        }
        viewModel.removeItem.observe(viewLifecycleOwner) {
            it.data ?: return@observe
            if (it.status == Status.SUCCESS) {
                viewModel.getCart()
            }
        }
        viewModel.updateItem.observe(viewLifecycleOwner) {
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
                    activity, "?????t h??ng th??nh c??ng",
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
                    activity, "H??? v?? t??n kh??ng ???????c ????? tr???ng",
                    Toast.LENGTH_SHORT
                ).show()
                return@callback
            }
            if (phone.isNullOrEmpty()) {
                Toast.makeText(
                    activity, "S??? ??i???n tho???i kh??ng ???????c b??? tr???ng",
                    Toast.LENGTH_SHORT
                ).show()
                return@callback
            }
            if (address.isNullOrEmpty()) {
                Toast.makeText(
                    activity, "?????a ch??? kh??ng ???????c b??? tr???ng",
                    Toast.LENGTH_SHORT
                ).show()
                return@callback
            }
            viewModel.makeOrder(name, phone, address)
        }
        viewModel.getCart()
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
                title = "Gi??? h??ng"
            }
        }

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

    private val activity: MainActivity
        get() = requireActivity() as MainActivity
}