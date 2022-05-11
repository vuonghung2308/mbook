package com.mh.mbook.ui.main.book.adapter

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.mh.mbook.ui.main.book.fragment.CategoryFragment
import com.mh.mbook.ui.main.book.fragment.BaseBookFragment
import com.mh.mbook.ui.main.book.fragment.TopNewFragment
import com.mh.mbook.ui.main.book.fragment.TopSaleFragment

class ViewPaperAdapter(
    fragment: Fragment
) : FragmentStateAdapter(fragment) {
    override fun getItemCount() = 4

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> BaseBookFragment()
            1 -> TopSaleFragment()
            2 -> CategoryFragment()
            else -> TopNewFragment()
        }
    }
}