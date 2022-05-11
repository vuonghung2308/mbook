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
import com.mh.mbook.databinding.FragmentTopSaleBinding
import com.mh.mbook.di.Injectable
import com.mh.mbook.ui.main.MainActivity
import com.mh.mbook.ui.main.MainViewModel
import com.mh.mbook.ui.main.book.adapter.BookVerticalAdapter
import com.mh.mbook.util.AppExecutors
import javax.inject.Inject

class TopSaleFragment : Fragment(), Injectable {
    @Inject
    lateinit var excutors: AppExecutors

    private lateinit var binding: FragmentTopSaleBinding
    private val viewModel: MainViewModel
            by viewModels({ activity })

    private val topSaleAdapter: BookVerticalAdapter by lazy {
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
            inflater, R.layout.fragment_top_sale,
            container, false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.lifecycleOwner = viewLifecycleOwner
        binding.topSale.adapter = topSaleAdapter
        viewModel.topSale.observe(viewLifecycleOwner) {
            it.data ?: return@observe
            topSaleAdapter.submitList(it.data)
            if (!binding.linearLayout.isVisible) {
                binding.linearLayout.visibility = View.VISIBLE
            }
        }
        viewModel.getTopSale()
    }

    private val activity: MainActivity
        get() = requireActivity() as MainActivity
}