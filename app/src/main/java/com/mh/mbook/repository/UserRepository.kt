package com.mh.mbook.repository

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.mh.mbook.api.ApiResponse
import com.mh.mbook.api.BookService
import com.mh.mbook.api.request.SignInRequest
import com.mh.mbook.api.request.SignUpRequest
import com.mh.mbook.api.request.VerifyRequest
import com.mh.mbook.api.response.BaseResponse
import com.mh.mbook.api.response.SignInResponse
import com.mh.mbook.db.Cache
import com.mh.mbook.util.AppExecutors
import com.mh.mbook.vo.Resource
import com.mh.mbook.vo.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val executors: AppExecutors,
    private val sp: SharedPreferences,
    private val service: BookService,
    private val cache: Cache
) {

    fun signIn(username: String, password: String): LiveData<Resource<SignInResponse>> {
        return object : NetworkBoundResource<SignInResponse, SignInResponse>(executors) {
            override fun saveCallResult(item: SignInResponse) {
                cache.signInResponse.postValue(item)
                if (!item.isError) {
                    val user = User(
                        item.id, item.fullname, item.username,
                        item.email, item.token, item.role
                    )
                    sp.edit().putString(
                        "user", Gson().toJson(user)
                    ).apply()
                }
            }

            override fun shouldFetch(data: SignInResponse?) = true

            override fun loadFromDb() = cache.signInResponse

            override fun createCall(): LiveData<ApiResponse<SignInResponse>> {
                val token = sp.getString("token", null).toString()
                println(token)
                return service.signIn(SignInRequest(username, password, token))
            }
        }.asLiveData()
    }

    fun signUp(
        username: String,
        password: String,
        email: String
    ): LiveData<Resource<BaseResponse>> {
        return object : NetworkBoundResource<BaseResponse, BaseResponse>(executors) {
            override fun saveCallResult(item: BaseResponse) {
                cache.signUpResponse.postValue(item)
            }

            override fun shouldFetch(data: BaseResponse?) = true

            override fun loadFromDb() = cache.signUpResponse

            override fun createCall(): LiveData<ApiResponse<BaseResponse>> {
                val body = SignUpRequest(username, password, email)
                return service.signUp(body)
            }
        }.asLiveData()
    }

    fun verify(email: String, code: String): LiveData<Resource<BaseResponse>> {
        return object : NetworkBoundResource<BaseResponse, BaseResponse>(executors) {
            override fun saveCallResult(item: BaseResponse) {
                cache.verifyResponse.postValue(item)
            }

            override fun shouldFetch(data: BaseResponse?) = true

            override fun loadFromDb() = cache.verifyResponse

            override fun createCall(): LiveData<ApiResponse<BaseResponse>> {
                val body = VerifyRequest(email, code)
                return service.verify(body)
            }
        }.asLiveData()
    }

    fun loadUser(): LiveData<User?> {
        val user = MutableLiveData<User?>()
        runBlocking(Dispatchers.IO) {
            val json = sp.getString("user", null)
            if (json == null) {
                user.postValue(null)
            } else {
                val ob = Gson().fromJson(
                    json, User::class.java
                )
                user.postValue(ob)
            }
        }
        return user
    }

    fun clearUser() {
        runBlocking(Dispatchers.IO) {
            sp.edit().putString(
                "user", null
            ).apply()
        }
    }
}
