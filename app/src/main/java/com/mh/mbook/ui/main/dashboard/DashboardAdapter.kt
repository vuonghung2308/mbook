package com.mh.mbook.ui.main.dashboard

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.mh.mbook.ui.main.book.BookFragment
import com.mh.mbook.ui.main.cart.CartFragment
import com.mh.mbook.ui.main.account.AccountFragment

class DashboardAdapter(
    fragment: Fragment
) : FragmentStateAdapter(fragment) {
    override fun getItemCount() = 3

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> BookFragment()
            1 -> CartFragment()
            else -> AccountFragment()
        }
    }
}