package com.mh.mbook.repository

import androidx.lifecycle.LiveData
import com.mh.mbook.api.BookService
import com.mh.mbook.api.request.MakeOrderRequest
import com.mh.mbook.api.response.BaseResponse
import com.mh.mbook.api.response.OrderDetailResponse
import com.mh.mbook.api.response.OrderResponse
import com.mh.mbook.db.Cache
import com.mh.mbook.util.AppExecutors
import com.mh.mbook.vo.Resource
import javax.inject.Inject

class OrderRepository @Inject constructor(
    private val executors: AppExecutors,
    private val service: BookService,
    private val cache: Cache
) {
    fun makeOrder(name: String, address: String, phone: String): LiveData<Resource<BaseResponse>> {
        return object : NetworkBoundResource<BaseResponse, BaseResponse>(executors) {
            override fun saveCallResult(item: BaseResponse) {
                cache.addItemResponse.postValue(item)
            }

            override fun shouldFetch(data: BaseResponse?) = true

            override fun loadFromDb() = cache.addItemResponse

            override fun createCall() = service.makeOrder(MakeOrderRequest(name, phone, address))
        }.asLiveData()
    }

    fun getOrders(): LiveData<Resource<List<OrderResponse>>> {
        return object : NetworkBoundResource<List<OrderResponse>, List<OrderResponse>>(executors) {
            override fun saveCallResult(item: List<OrderResponse>) {
                cache.ordersResponse.postValue(item)
            }

            override fun shouldFetch(data: List<OrderResponse>?) = true
            override fun loadFromDb() = cache.ordersResponse
            override fun createCall() = service.getOrders()

        }.asLiveData();
    }

    fun getOrder(id: Long): LiveData<Resource<OrderDetailResponse>> {
        return object : NetworkBoundResource<OrderDetailResponse, OrderDetailResponse>(executors) {
            override fun saveCallResult(item: OrderDetailResponse) {
                cache.orderResponse.postValue(item)
            }

            override fun shouldFetch(data: OrderDetailResponse?) = true
            override fun loadFromDb() = cache.orderResponse
            override fun createCall() = service.getOrder(id)

        }.asLiveData();
    }
}