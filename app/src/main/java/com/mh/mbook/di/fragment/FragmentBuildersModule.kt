package com.mh.mbook.di.fragment

import com.mh.mbook.ui.main.book.BookDetailFragment
import com.mh.mbook.ui.main.book.ListBookFragment
import com.mh.mbook.ui.main.book.fragment.BaseBookFragment
import com.mh.mbook.ui.main.book.fragment.CategoryFragment
import com.mh.mbook.ui.main.book.fragment.TopNewFragment
import com.mh.mbook.ui.main.book.fragment.TopSaleFragment
import com.mh.mbook.ui.main.cart.CartFragment
import com.mh.mbook.ui.main.order.fragment.ListOrderFragment
import com.mh.mbook.ui.main.order.fragment.OrderDetailFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuildersModule {
    @ContributesAndroidInjector
    abstract fun contributeHomeFragment(): BaseBookFragment

    @ContributesAndroidInjector
    abstract fun contributeTopSaleFragment(): TopSaleFragment

    @ContributesAndroidInjector
    abstract fun contributeTopNewFragment(): TopNewFragment

    @ContributesAndroidInjector
    abstract fun contributeCategoryFragment(): CategoryFragment

    @ContributesAndroidInjector
    abstract fun contributeCartFragment(): CartFragment

    @ContributesAndroidInjector
    abstract fun contributeListOrderFragment(): ListOrderFragment

    @ContributesAndroidInjector
    abstract fun contributeOrderDetailFragment(): OrderDetailFragment

    @ContributesAndroidInjector
    abstract fun contributeBookDetailFragment(): BookDetailFragment

    @ContributesAndroidInjector
    abstract fun contributeListBookFragment(): ListBookFragment
}