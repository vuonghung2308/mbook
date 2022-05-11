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
import com.mh.mbook.databinding.FragmentCategoriesBinding
import com.mh.mbook.di.Injectable
import com.mh.mbook.ui.main.MainActivity
import com.mh.mbook.ui.main.MainViewModel
import com.mh.mbook.ui.main.book.adapter.CategoryAdapter
import com.mh.mbook.util.AppExecutors
import javax.inject.Inject

class CategoryFragment : Fragment(), Injectable {
    @Inject
    lateinit var excutors: AppExecutors

    private lateinit var binding: FragmentCategoriesBinding
    private val viewModel: MainViewModel
            by viewModels({ activity })

    private val categoryAdapter: CategoryAdapter by lazy {
        CategoryAdapter(excutors)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_categories,
            container, false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.lifecycleOwner = viewLifecycleOwner
        binding.categories.adapter = categoryAdapter
        viewModel.categories.observe(viewLifecycleOwner) {
            it.data ?: return@observe
            categoryAdapter.submitList(it.data)
            if (!binding.linearLayout.isVisible) {
                binding.linearLayout.visibility = View.VISIBLE
            }
        }
        viewModel.getCategories()
    }

    private val activity: MainActivity
        get() = requireActivity() as MainActivity
}