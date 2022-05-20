package com.mh.mbook.ui.main

import android.content.Context
import android.graphics.Rect
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.mh.mbook.R
import com.mh.mbook.api.response.CategoryResponse
import com.mh.mbook.databinding.ActivityMainBinding
import com.mh.mbook.ui.main.book.BookDetailFragment
import com.mh.mbook.ui.main.book.ListBookFragment
import com.mh.mbook.ui.main.dashboard.DashboardFragment
import com.mh.mbook.ui.main.order.fragment.ListOrderFragment
import com.mh.mbook.ui.main.order.fragment.OrderDetailFragment
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject


class MainActivity : AppCompatActivity(), HasAndroidInjector {
    @Inject
    lateinit var injector: DispatchingAndroidInjector<Any>

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private lateinit var binding: ActivityMainBinding

    val viewModel: MainViewModel
            by viewModels { factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this, R.layout.activity_main
        )
        replaceDashboardFragment()
        viewModel.user.observe(this) {}
    }

    private fun replaceDashboardFragment() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, DashboardFragment())
            .commit()
    }

    fun addFragmentBookDetail(id: Long) {
        supportFragmentManager.beginTransaction()
            .add(R.id.container, BookDetailFragment(id))
            .addToBackStack(null)
            .commit()
    }

    fun addFragmentOrderDetail(id: Long) {
        supportFragmentManager.beginTransaction()
            .add(R.id.container, OrderDetailFragment(id))
            .addToBackStack(null)
            .commit()
    }

    fun addFragmentListOrder() {
        supportFragmentManager.beginTransaction()
            .add(R.id.container, ListOrderFragment())
            .addToBackStack(null)
            .commit()
    }

    fun addFragmentListBook(id: CategoryResponse) {
        supportFragmentManager.beginTransaction()
            .add(R.id.container, ListBookFragment(id))
            .addToBackStack(null)
            .commit()
    }

    override fun androidInjector() = injector
    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            val v: View? = currentFocus
            if (v is EditText) {
                val outRect = Rect()
                v.getGlobalVisibleRect(outRect)
                if (!outRect.contains(event.rawX.toInt(), event.rawY.toInt())) {
                    v.clearFocus()
                    val imm: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE)
                            as InputMethodManager
                    imm.hideSoftInputFromWindow(v.getWindowToken(), 0)
                }
            }
        }
        return super.dispatchTouchEvent(event)
    }
}
