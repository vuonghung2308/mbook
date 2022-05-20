package com.mh.mbook.ui.main.book

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.Toolbar
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.mh.mbook.R
import com.mh.mbook.api.response.CategoryResponse
import com.mh.mbook.databinding.FragmentListBookBinding
import com.mh.mbook.di.Injectable
import com.mh.mbook.ui.main.MainActivity
import com.mh.mbook.ui.main.MainViewModel
import com.mh.mbook.ui.main.book.adapter.BookVerticalAdapter
import com.mh.mbook.util.AppExecutors
import com.mh.mbook.vo.Status
import javax.inject.Inject

class ListBookFragment(
    private val category: CategoryResponse
) : Fragment(), Injectable {
    @Inject
    lateinit var excutors: AppExecutors

    private lateinit var binding: FragmentListBookBinding
    private val viewModel: MainViewModel
            by viewModels({ activity })

    private val adapter: BookVerticalAdapter by lazy {
        BookVerticalAdapter(excutors) {
            activity.addFragmentBookDetail(it.id)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_list_book,
            container, false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.lifecycleOwner = viewLifecycleOwner
        binding.books.adapter = adapter
        setUpSupportActionBar(binding.toolbar)
        viewModel.books.observe(viewLifecycleOwner) {
            it.data ?: return@observe
            if (it.status == Status.SUCCESS) {
                adapter.submitList(it.data)
                if (!binding.linearLayout.isVisible) {
                    binding.linearLayout.visibility = View.VISIBLE
                }
            }
        }
        viewModel.getBooks(category.id)
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
                title = "Sách ${category.name.lowercase()}"
            }
        }
    }

    private val activity: MainActivity
        get() = requireActivity() as MainActivity
}