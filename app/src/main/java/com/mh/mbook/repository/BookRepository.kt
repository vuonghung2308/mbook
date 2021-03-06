package com.mh.mbook.repository

import androidx.lifecycle.LiveData
import com.mh.mbook.api.BookService
import com.mh.mbook.api.request.CommentRequest
import com.mh.mbook.api.response.*
import com.mh.mbook.db.Cache
import com.mh.mbook.util.AppExecutors
import com.mh.mbook.vo.Resource
import javax.inject.Inject

class BookRepository @Inject constructor(
    private val executors: AppExecutors,
    private val service: BookService,
    private val cache: Cache
) {
    fun baseBook(): LiveData<Resource<BaseBookResponse>> {
        return object : NetworkBoundResource<BaseBookResponse, BaseBookResponse>(executors) {
            override fun saveCallResult(item: BaseBookResponse) {
                cache.baseBookResponse.postValue(item)
            }

            override fun shouldFetch(data: BaseBookResponse?) = true

            override fun loadFromDb() = cache.baseBookResponse

            override fun createCall() = service.baseBook()
        }.asLiveData()
    }

    fun topSale(): LiveData<Resource<List<BookResponse>>> {
        return object : NetworkBoundResource<List<BookResponse>, List<BookResponse>>(executors) {
            override fun saveCallResult(item: List<BookResponse>) {
                cache.topSaleResponse.postValue(item)
            }

            override fun shouldFetch(data: List<BookResponse>?) = true

            override fun loadFromDb() = cache.topSaleResponse

            override fun createCall() = service.topSale()
        }.asLiveData()
    }

    fun topNew(): LiveData<Resource<List<BookResponse>>> {
        return object : NetworkBoundResource<List<BookResponse>, List<BookResponse>>(executors) {
            override fun saveCallResult(item: List<BookResponse>) {
                cache.topNewResponse.postValue(item)
            }

            override fun shouldFetch(data: List<BookResponse>?) = true

            override fun loadFromDb() = cache.topNewResponse

            override fun createCall() = service.topNew()
        }.asLiveData()
    }

    fun categories(): LiveData<Resource<List<CategoryResponse>>> {
        return object :
            NetworkBoundResource<List<CategoryResponse>, List<CategoryResponse>>(executors) {
            override fun saveCallResult(item: List<CategoryResponse>) {
                cache.categoriesResponse.postValue(item)
            }

            override fun shouldFetch(data: List<CategoryResponse>?) = true

            override fun loadFromDb() = cache.categoriesResponse

            override fun createCall() = service.categories()
        }.asLiveData()
    }

    fun getBook(id: Long): LiveData<Resource<BookDetailResponse>> {
        return object : NetworkBoundResource<BookDetailResponse, BookDetailResponse>(executors) {
            override fun saveCallResult(item: BookDetailResponse) {
                cache.bookResponse.postValue(item)
            }

            override fun shouldFetch(data: BookDetailResponse?) = true

            override fun loadFromDb() = cache.bookResponse

            override fun createCall() = service.getBook(id)
        }.asLiveData()
    }

    fun getBooks(categoryId: Long): LiveData<Resource<List<BookResponse>>> {
        return object : NetworkBoundResource<List<BookResponse>, List<BookResponse>>(executors) {
            override fun saveCallResult(item: List<BookResponse>) {
                cache.booksResponse.postValue(item)
            }

            override fun shouldFetch(data: List<BookResponse>?) = true
            override fun createCall() = service.getBooks(categoryId)
            override fun loadFromDb() = cache.booksResponse
        }.asLiveData()
    }

    fun comment(id: Long, star: Int, comment: String?): LiveData<Resource<BaseResponse>> {
        return object : NetworkBoundResource<BaseResponse, BaseResponse>(executors) {
            override fun saveCallResult(item: BaseResponse) {
                cache.commentResponse.postValue(item)
            }

            override fun shouldFetch(data: BaseResponse?) = true

            override fun loadFromDb() = cache.commentResponse

            override fun createCall() = service.comment(CommentRequest(id, star, comment))
        }.asLiveData()
    }
}