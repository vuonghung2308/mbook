package com.mh.mbook.ui.main.book

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.mh.mbook.R
import com.mh.mbook.databinding.FragmentBookBinding
import com.mh.mbook.ui.main.book.adapter.ViewPaperAdapter

class BookFragment : Fragment() {
    private lateinit var binding: FragmentBookBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_book,
            container, false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val titles = arrayOf("Dành cho bạn", "Sách bán chạy", "Thể loại", "Mới phát hành")
        binding.viewPaper.adapter = ViewPaperAdapter(this)
        binding.viewPaper.isUserInputEnabled = false
        TabLayoutMediator(binding.tabLayout, binding.viewPaper) { tab, position ->
            tab.text = titles[position]
        }.attach()
    }
}