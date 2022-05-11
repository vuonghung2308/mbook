package com.mh.mbook.ui.signup

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.mh.mbook.repository.UserRepository
import javax.inject.Inject

class SignUpViewModel @Inject constructor(
    private val repo: UserRepository
) : ViewModel() {
    private val _signUp = MutableLiveData<Any>()
    private val _verify = MutableLiveData<Any>()

    val username = MutableLiveData<String>()
    val password = MutableLiveData<String>()
    val cPassword = MutableLiveData<String>()
    val email = MutableLiveData<String>()
    val otp = MutableLiveData<String>()

    val signUp = _signUp.switchMap {
        repo.signUp(
            username.value!!,
            password.value!!,
            email.value!!
        )
    }

    val verify = _verify.switchMap {
        repo.verify(email.value!!, otp.value!!)
    }


    fun getSignUpMessage(): String? {
        if (username.value.isNullOrEmpty()) {
            return "Username can not be blank"
        }
        if (email.value.isNullOrEmpty()) {
            return "Email can not be blank"
        }
        if (password.value.isNullOrEmpty()) {
            return "password can not be blank"
        }
        if (!password.value.equals(cPassword.value)) {
            return "Confirm password is not match"
        }
        return null
    }

    fun getVerifyMessage(): String? {
        if (otp.value == null || otp.value?.length!! < 6) {
            return "Verification code must contain 6 number"
        }
        return null
    }

    fun signUp() {
        _signUp.value = _signUp.value
    }

    fun verify() {
        _verify.value = _verify.value
    }
}