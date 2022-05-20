package com.mh.mbook.ui.main.account

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.firebase.messaging.FirebaseMessaging
import com.mh.mbook.R
import com.mh.mbook.databinding.FragmentAccountBinding
import com.mh.mbook.ui.common.callback
import com.mh.mbook.ui.main.MainActivity
import com.mh.mbook.ui.main.MainViewModel
import com.mh.mbook.ui.signin.SignInActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking

class AccountFragment : Fragment() {

    //    @Inject
//    lateinit var executors: AppExecutors
    private lateinit var binding: FragmentAccountBinding
    private val viewModel: MainViewModel
            by viewModels({ activity })

//    private val adapter by lazy {
//        CartItemAdapter(executors) {
//            viewModel.remove(it.id)
//        }
//    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_account,
            container, false
        )
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.lifecycleOwner = viewLifecycleOwner
        viewModel.user.observe(viewLifecycleOwner) {
            it?.let { binding.user = it }
        }
        binding.signOutCb = callback {
            viewModel.signOut()
            Intent(activity, SignInActivity::class.java)
                .apply { startActivity(this) }
            activity.finish()
        }
        binding.listOrderCb = callback {
            activity.addFragmentListOrder()
        }
    }

    private val activity: MainActivity
        get() = requireActivity() as MainActivity
}