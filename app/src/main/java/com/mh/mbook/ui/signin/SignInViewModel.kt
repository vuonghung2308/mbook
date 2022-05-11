package com.mh.mbook.ui.signin

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.mh.mbook.repository.UserRepository
import javax.inject.Inject

class SignInViewModel @Inject constructor(
    userRepository: UserRepository
) : ViewModel() {
    private val _login = MutableLiveData<Any>()
    val username = MutableLiveData<String>()
    val password = MutableLiveData<String>()

    val user = _login.switchMap {
        userRepository.signIn(
            username.value.toString(),
            password.value.toString()
        )
    }

    fun signIn() {
        _login.value = _login.value
    }
}