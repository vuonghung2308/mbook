package com.mh.mbook.api

import androidx.lifecycle.LiveData
import com.mh.mbook.api.request.*
import com.mh.mbook.api.response.*
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface BookService {

    @POST("auth/sign-in")
    fun signIn(@Body body: SignInRequest): LiveData<ApiResponse<SignInResponse>>

    @POST("auth/sign-up")
    fun signUp(@Body body: SignUpRequest): LiveData<ApiResponse<BaseResponse>>

    @POST("auth/verify")
    fun verify(@Body body: VerifyRequest): LiveData<ApiResponse<BaseResponse>>

    @GET("book/")
    fun getBook(@Query("id") id: Long): LiveData<ApiResponse<BookDetailResponse>>

    @GET("book/base")
    fun baseBook(): LiveData<ApiResponse<BaseBookResponse>>

    @GET("book/top-sale")
    fun topSale(): LiveData<ApiResponse<List<BookResponse>>>

    @GET("book/top-new")
    fun topNew(): LiveData<ApiResponse<List<BookResponse>>>

    @GET("category")
    fun categories(): LiveData<ApiResponse<List<CategoryResponse>>>

    @GET("cart/")
    fun getCart(): LiveData<ApiResponse<CartResponse>>

    @POST("cart/clear")
    fun clearCart(): LiveData<ApiResponse<BaseResponse>>

    @POST("cart/remove")
    fun removeItem(@Body body: RemoveRequest): LiveData<ApiResponse<BaseResponse>>

    @POST("cart/add")
    fun addItem(@Body body: AddItemRequest): LiveData<ApiResponse<BaseResponse>>

    @GET("order")
    fun getOrders(): LiveData<ApiResponse<List<OrderResponse>>>

    @GET("order")
    fun getOrder(@Query("id") id: Long): LiveData<ApiResponse<OrderDetailResponse>>

    @POST("order/make")
    fun makeOrder(@Body body: MakeOrderRequest): LiveData<ApiResponse<BaseResponse>>
}