package com.mh.mbook.ui.signup.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.mh.mbook.R
import com.mh.mbook.databinding.FragmentResultBinding
import com.mh.mbook.ui.common.callback
import com.mh.mbook.ui.signup.SignUpActivity

class ResultFragment : Fragment() {
    private lateinit var binding: FragmentResultBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_result,
            container, false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.signUpCb = callback {
            activity.finish()
        }
    }

    private val activity: SignUpActivity
        get() = requireActivity() as SignUpActivity
}