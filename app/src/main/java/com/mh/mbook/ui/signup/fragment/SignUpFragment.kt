package com.mh.mbook.ui.signup.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.mh.mbook.R
import com.mh.mbook.databinding.FragmentSignupBinding
import com.mh.mbook.ui.common.callback
import com.mh.mbook.ui.common.showMessage
import com.mh.mbook.ui.signup.SignUpActivity
import com.mh.mbook.ui.signup.SignUpViewModel
import com.mh.mbook.vo.Status

class SignUpFragment : Fragment() {
    private lateinit var binding: FragmentSignupBinding
    private val viewModel: SignUpViewModel
            by viewModels({ activity })

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_signup,
            container, false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        binding.signUpCb = callback {
            val message = viewModel.getSignUpMessage()
            if (message != null) {
                showMessage(message)
                return@callback
            }
            viewModel.signUp()
        }
        viewModel.signUp.observe(viewLifecycleOwner) {
            if (it.status == Status.SUCCESS) {
                it.data ?: return@observe
                if (it.data.code == "SUCCESSFUL_REGISTRATION") {
                    activity.replaceFragmentEmail()
                } else showMessage(it.data.msg)
            }
        }
    }

    private val activity: SignUpActivity
        get() = requireActivity() as SignUpActivity
}