package com.mh.mbook.ui.signup

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.mh.mbook.R
import com.mh.mbook.databinding.ActivitySignupBinding
import com.mh.mbook.di.Injectable
import com.mh.mbook.ui.signup.fragment.EmailFragment
import com.mh.mbook.ui.signup.fragment.ResultFragment
import com.mh.mbook.ui.signup.fragment.SignUpFragment
import javax.inject.Inject

class SignUpActivity : AppCompatActivity(), Injectable {
    @Inject
    lateinit var factory: ViewModelProvider.Factory

    private lateinit var binding: ActivitySignupBinding

    private val viewModel: SignUpViewModel
            by viewModels { factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this, R.layout.activity_signup
        )
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, SignUpFragment())
            .commit()
        viewModel
    }

    fun replaceFragmentEmail() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, EmailFragment())
            .commit()
    }

    fun replaceResultEmail() {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, ResultFragment())
            .commit()
    }
}