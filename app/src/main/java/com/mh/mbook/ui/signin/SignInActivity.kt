package com.mh.mbook.ui.signin

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.mh.mbook.R
import com.mh.mbook.databinding.ActivitySigninBinding
import com.mh.mbook.di.Injectable
import com.mh.mbook.ui.common.callback
import com.mh.mbook.ui.common.showMessage
import com.mh.mbook.ui.main.MainActivity
import com.mh.mbook.ui.signup.SignUpActivity
import com.mh.mbook.vo.Status
import javax.inject.Inject

class SignInActivity : AppCompatActivity(), Injectable {
    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private lateinit var binding: ActivitySigninBinding

    private val viewModel: SignInViewModel
            by viewModels { factory }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(
            this, R.layout.activity_signin
        )
        binding.lifecycleOwner = this
        binding.viewModel = viewModel
        binding.signUpCb = callback {
            Intent(
                this@SignInActivity as Context,
                SignUpActivity::class.java
            ).apply { startActivity(this) }
        }
        viewModel.user.observe(this) {
            if (it.status == Status.SUCCESS) {
                it.data ?: return@observe
                if (it.data.isError) {
                    showMessage(it.data.msg)
                } else {
                    Intent(this as Context, MainActivity::class.java)
                        .apply { startActivity(this) }
                    finish()
                }
            }
        }
    }
}