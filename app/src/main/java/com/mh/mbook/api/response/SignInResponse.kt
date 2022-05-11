package com.mh.mbook.api.response

import com.mh.mbook.vo.Role

data class SignInResponse(
    val id: Long,
    val fullname: String,
    val username: String,
    val email: String,
    val token: String,
    val role: Role
) : BaseResponse() {
    val isError: Boolean
        get() = msg != null && code != null
}