package com.mh.mbook.ui.signup.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.mh.mbook.R
import com.mh.mbook.databinding.FragmentEmailBinding
import com.mh.mbook.ui.common.callback
import com.mh.mbook.ui.common.showMessage
import com.mh.mbook.ui.signup.SignUpActivity
import com.mh.mbook.ui.signup.SignUpViewModel
import com.mh.mbook.vo.Status

class EmailFragment : Fragment() {
    private lateinit var binding: FragmentEmailBinding

    private val viewModel: SignUpViewModel
            by viewModels({ activity })

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_email,
            container, false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        binding.verifyCb = callback {
            val message = viewModel.getVerifyMessage()
            if (message != null) {
                showMessage(message)
                return@callback
            }
            viewModel.verify()

        }
        viewModel.verify.observe(viewLifecycleOwner) {
            if (it.status == Status.SUCCESS) {
                it.data ?: return@observe
                if (it.data.code == "SUCCESSFUL_VERIFICATION") {
                    activity.replaceResultEmail()
                } else showMessage(it.data.msg)
            }
        }
    }

    private val activity: SignUpActivity
        get() = requireActivity() as SignUpActivity
}