package com.mh.mbook.util

import android.content.SharedPreferences
import com.google.gson.Gson
import com.mh.mbook.vo.User
import okhttp3.Interceptor
import okhttp3.Response

class TokenInterceptor(
    private val sp: SharedPreferences
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val user: User? = Gson().fromJson(
            sp.getString("user", null),
            User::class.java
        )
        val requestBuilder = chain.request().newBuilder()
        user?.let {
            val value = "Bearer ${user.token}"
            requestBuilder.addHeader(TOKEN_HEADER, value)
        }
        val request = requestBuilder.build()
        return chain.proceed(request)
    }

    companion object {
        const val TOKEN_HEADER = "Authorization"
    }
}