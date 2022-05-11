package com.mh.mbook.db

import androidx.lifecycle.MutableLiveData
import com.mh.mbook.api.response.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class Cache @Inject constructor() {
    val signInResponse = MutableLiveData<SignInResponse>(null)
    val signUpResponse = MutableLiveData<BaseResponse>(null)
    val verifyResponse = MutableLiveData<BaseResponse>(null)

    val baseBookResponse = MutableLiveData<BaseBookResponse>(null)
    val topSaleResponse = MutableLiveData<List<BookResponse>>(null)
    val topNewResponse = MutableLiveData<List<BookResponse>>(null)
    val categoriesResponse = MutableLiveData<List<CategoryResponse>>(null)
    val bookResponse = MutableLiveData<BookDetailResponse>(null)


    val cartResponse = MutableLiveData<CartResponse>(null)
    val addItemResponse = MutableLiveData<BaseResponse>(null)
    val removeItemResponse = MutableLiveData<BaseResponse>(null)
    val clearCartResponse = MutableLiveData<BaseResponse>(null)

    val ordersResponse = MutableLiveData<List<OrderResponse>>(null)
    val orderResponse = MutableLiveData<OrderDetailResponse>(null)
    val makeOrderResponse = MutableLiveData<BaseResponse>(null)
}