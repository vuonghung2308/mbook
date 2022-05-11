package com.mh.mbook.ui.main.book

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.google.android.material.appbar.AppBarLayout
import com.mh.mbook.R
import com.mh.mbook.databinding.FragmentBookDetailBinding
import com.mh.mbook.ui.common.callback
import com.mh.mbook.ui.main.MainActivity
import com.mh.mbook.ui.main.MainViewModel
import com.mh.mbook.vo.Status

class DetailFragment(
    private val id: Long
) : Fragment() {
    private lateinit var binding: FragmentBookDetailBinding
    private val viewModel: MainViewModel
            by viewModels({ activity })
    private var isButtonClicked = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_book_detail,
            container, false
        )
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        setUpSupportActionBar(binding.toolbar)
        binding.lifecycleOwner = viewLifecycleOwner
        viewModel.book.observe(viewLifecycleOwner) {
            it.data ?: return@observe
            if (it.status == Status.SUCCESS) {
                binding.book = it.data
                Glide.with(binding.image)
                    .load(it.data.imageUrl)
                    .into(binding.image)
                val str = it.data.description.split("\\n")
                for (i in str) {
                    val textView = TextView(binding.root.context)
                    textView.text = "    $i"
                    val padding = resources.getDimensionPixelSize(R.dimen.dimen_6)
                    textView.setPadding(0, padding, 0, 0)
                    binding.descriptions.addView(textView)
                }
                binding.linearLayout.visibility = View.VISIBLE
            }
        }
        viewModel.add.observe(viewLifecycleOwner) {
            it.data ?: return@observe
            if (it.status == Status.SUCCESS && !isButtonClicked) {
                Toast.makeText(
                    activity, "Thêm vào giỏ hàng thành công",
                    Toast.LENGTH_SHORT
                ).show()
                isButtonClicked = true
            }
        }
        viewModel.getBook(id)
        binding.addCb = callback {
            try {
                isButtonClicked = false
                val q = binding.tvQuantity.text
                    .toString().toInt()
                viewModel.add(id, q)
            } catch (e: Exception) {
                Toast.makeText(
                    activity, "Số lượng không hợp lệ",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_book, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.item_search -> {
                true
            }
            R.id.item_cart -> {
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    private fun setUpSupportActionBar(toolbar: Toolbar) {
        setHasOptionsMenu(true)
        activity.apply {
            setSupportActionBar(toolbar)
            toolbar.setNavigationOnClickListener {
                onBackPressed()
            }
            supportActionBar?.apply {
                setDisplayShowTitleEnabled(true)
                setDisplayHomeAsUpEnabled(true)
                title = "Chi tiết sách"
            }
        }

        var isWhite = false
        var old = 0
        binding.appbarLayout.addOnOffsetChangedListener(
            AppBarLayout.OnOffsetChangedListener { layout, offset ->
                if (offset - old > 0 && isWhite) {
                    binding.divider.setBackgroundResource(R.color.stroke)
                    isWhite = false
                }
                old = offset
                if (layout.totalScrollRange + offset == 0) {
                    if (!isWhite) {
                        binding.divider.setBackgroundResource(R.color.white)
                        isWhite = true
                    }
                }
            }
        )
    }

    private val activity: MainActivity
        get() = requireActivity() as MainActivity
}