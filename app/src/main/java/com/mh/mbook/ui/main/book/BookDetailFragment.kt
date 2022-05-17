package com.mh.mbook.ui.main.book

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Html
import android.view.*
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.core.text.HtmlCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.google.android.material.appbar.AppBarLayout
import com.mh.mbook.R
import com.mh.mbook.api.response.RatingResponse
import com.mh.mbook.databinding.FragmentBookDetailBinding
import com.mh.mbook.di.Injectable
import com.mh.mbook.ui.common.callback
import com.mh.mbook.ui.main.MainActivity
import com.mh.mbook.ui.main.MainViewModel
import com.mh.mbook.ui.main.book.adapter.RatingAdapter
import com.mh.mbook.util.AppExecutors
import com.mh.mbook.vo.Status
import javax.inject.Inject

class BookDetailFragment(
    private val id: Long
) : Fragment(), Injectable {
    @Inject
    lateinit var executors: AppExecutors
    private lateinit var binding: FragmentBookDetailBinding
    private val viewModel: MainViewModel
            by viewModels({ activity })
    private var isButtonAddClicked = true
    private var isButtonPostClicked = true
    private val adapter by lazy {
        RatingAdapter(executors)
    }

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
        binding.ratings.adapter = adapter
        viewModel.book.observe(viewLifecycleOwner) { it ->
            it.data ?: return@observe
            if (it.status == Status.SUCCESS) {
                binding.book = it.data
                Glide.with(binding.image)
                    .load(it.data.imageUrl)
                    .into(binding.image)
                val str = it.data.description.split("\\n")
                val tab = "&#160;&#160;&#160;&#160;"
                var text = "$tab${str[0]}"
                for (i in str.drop(1)) {
                    text += "<br><br>$tab$i"
                }
                binding.description.text = Html.fromHtml(
                    text, HtmlCompat.FROM_HTML_MODE_LEGACY
                )
                binding.linearLayout.visibility = View.VISIBLE
                var rating: RatingResponse? = null
                val ratings = it.data.ratings.filter { ra ->
                    !ra.comment.isNullOrEmpty()
                }
                adapter.submitList(ratings)
                for (i in it.data.ratings) {
                    if (i.user.id == viewModel.user.value?.id) {
                        rating = i
                        break
                    }
                }
                rating?.let { r ->
                    binding.ratingBar.rating = r.star.toFloat()
                    binding.edtComment.setText(r.comment)
                }
            }
        }
        viewModel.add.observe(viewLifecycleOwner) {
            it.data ?: return@observe
            if (it.status == Status.SUCCESS && !isButtonAddClicked) {
                Toast.makeText(
                    activity, "Thêm vào giỏ hàng thành công",
                    Toast.LENGTH_SHORT
                ).show()
                isButtonAddClicked = true
            }
        }
        viewModel.comment.observe(viewLifecycleOwner) {
            it.data ?: return@observe
            if (it.status == Status.SUCCESS && !isButtonPostClicked) {
                Toast.makeText(
                    activity, "Đăng bình luận thành công",
                    Toast.LENGTH_SHORT
                ).show()
                isButtonPostClicked = true
                viewModel.getBaseBook()
                viewModel.getBook(id)
            }
        }
        viewModel.getBook(id)
        binding.postCb = callback {
            isButtonPostClicked = false
            val star = binding.ratingBar.rating.toInt()
            val comment = binding.edtComment.text?.toString()
            viewModel.comment(id, star, comment)
        }
        binding.addCb = callback {
            try {
                isButtonAddClicked = false
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
        binding.moreCb = callback {
            if (binding.description.maxLines == 6) {
                binding.description.maxLines = 1000
                binding.tvMore.text = "Thu gọn"
            } else {
                binding.description.maxLines = 6
                binding.tvMore.text = "Xem thêm"
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