package com.mh.mbook.repository

import androidx.lifecycle.LiveData
import com.mh.mbook.api.BookService
import com.mh.mbook.api.request.AddItemRequest
import com.mh.mbook.api.request.RemoveRequest
import com.mh.mbook.api.response.BaseResponse
import com.mh.mbook.api.response.CartResponse
import com.mh.mbook.db.Cache
import com.mh.mbook.util.AppExecutors
import com.mh.mbook.vo.Resource
import javax.inject.Inject

class CartRepository @Inject constructor(
    private val executors: AppExecutors,
    private val service: BookService,
    private val cache: Cache
) {
    fun getCart(): LiveData<Resource<CartResponse>> {
        return object : NetworkBoundResource<CartResponse, CartResponse>(executors) {
            override fun saveCallResult(item: CartResponse) {
                cache.cartResponse.postValue(item)
            }

            override fun shouldFetch(data: CartResponse?) = true

            override fun loadFromDb() = cache.cartResponse

            override fun createCall() = service.getCart()
        }.asLiveData()
    }

    fun addItem(id: Long, quantity: Int): LiveData<Resource<BaseResponse>> {
        return object : NetworkBoundResource<BaseResponse, BaseResponse>(executors) {
            override fun saveCallResult(item: BaseResponse) {
                cache.makeOrderResponse.postValue(item)
            }

            override fun shouldFetch(data: BaseResponse?) = true

            override fun loadFromDb() = cache.makeOrderResponse

            override fun createCall() = service.addItem(AddItemRequest(id, quantity))
        }.asLiveData()
    }

    fun removeItem(id: Long): LiveData<Resource<BaseResponse>> {
        return object : NetworkBoundResource<BaseResponse, BaseResponse>(executors) {
            override fun saveCallResult(item: BaseResponse) {
                cache.removeItemResponse.postValue(item)
            }

            override fun shouldFetch(data: BaseResponse?) = true

            override fun loadFromDb() = cache.removeItemResponse

            override fun createCall() = service.removeItem(RemoveRequest(id))
        }.asLiveData()
    }

    fun clearCart(): LiveData<Resource<BaseResponse>> {
        return object : NetworkBoundResource<BaseResponse, BaseResponse>(executors) {
            override fun saveCallResult(item: BaseResponse) {
                cache.clearCartResponse.postValue(item)
            }

            override fun shouldFetch(data: BaseResponse?) = true

            override fun loadFromDb() = cache.clearCartResponse

            override fun createCall() = service.clearCart()
        }.asLiveData()
    }
}