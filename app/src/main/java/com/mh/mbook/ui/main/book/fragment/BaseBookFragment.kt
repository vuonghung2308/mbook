package com.mh.mbook.ui.main.book.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.mh.mbook.R
import com.mh.mbook.databinding.FragmentBookBaseBinding
import com.mh.mbook.di.Injectable
import com.mh.mbook.ui.main.MainActivity
import com.mh.mbook.ui.main.MainViewModel
import com.mh.mbook.ui.main.book.adapter.BookHorizontalAdapter
import com.mh.mbook.util.AppExecutors
import javax.inject.Inject

class BaseBookFragment : Fragment(), Injectable {
    @Inject
    lateinit var excutors: AppExecutors

    private lateinit var binding: FragmentBookBaseBinding
    private val viewModel: MainViewModel
            by viewModels({ activity })

    private val topNewAdapter: BookHorizontalAdapter by lazy {
        BookHorizontalAdapter(excutors) {
            activity.addFragmentBookDetail(it.id)
        }
    }
    private val topSaleAdapter: BookHorizontalAdapter by lazy {
        BookHorizontalAdapter(excutors) {
            activity.addFragmentBookDetail(it.id)
        }
    }
    private val topRatingAdapter: BookHorizontalAdapter by lazy {
        BookHorizontalAdapter(excutors) {
            activity.addFragmentBookDetail(it.id)
        }
    }
    private val topComicAdapter: BookHorizontalAdapter by lazy {
        BookHorizontalAdapter(excutors) {
            activity.addFragmentBookDetail(it.id)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_book_base,
            container, false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.lifecycleOwner = viewLifecycleOwner
        binding.topComic.adapter = topComicAdapter
        binding.topRating.adapter = topRatingAdapter
        binding.topNew.adapter = topNewAdapter
        binding.topSale.adapter = topSaleAdapter
        viewModel.baseBook.observe(viewLifecycleOwner) {
            it.data ?: return@observe
            topRatingAdapter.submitList(it.data.topRating)
            topComicAdapter.submitList(it.data.topComic)
            topSaleAdapter.submitList(it.data.topSale)
            topNewAdapter.submitList(it.data.topNew)
            if (!binding.linearLayout.isVisible) {
                binding.linearLayout.visibility = View.VISIBLE
            }
        }
        viewModel.getBaseBook()
    }

    private val activity: MainActivity
        get() = requireActivity() as MainActivity
}