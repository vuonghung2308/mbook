package com.mh.mbook.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.mh.mbook.repository.BookRepository
import com.mh.mbook.repository.CartRepository
import com.mh.mbook.repository.OrderRepository
import com.mh.mbook.repository.UserRepository
import kotlinx.coroutines.launch
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val bookRepo: BookRepository,
    private val cartRepo: CartRepository,
    private val userRepo: UserRepository,
    private val orderRepo: OrderRepository
) : ViewModel() {
    private val _baseBook = MutableLiveData<Any>()
    private val _topSale = MutableLiveData<Any>()
    private val _topNew = MutableLiveData<Any>()
    private val _categories = MutableLiveData<Any>()
    private val _book = MutableLiveData<Long>()
    private val _cart = MutableLiveData<Any>()
    private val _add = MutableLiveData<Pair<Long, Int>>()
    private val _remove = MutableLiveData<Long>()
    private val _clear = MutableLiveData<Any>()
    private val _user = MutableLiveData<Any>()
    private val _order = MutableLiveData<Long>()
    private val _orders = MutableLiveData<Any>()
    private val _make = MutableLiveData<Triple<String, String, String>>()

    init {
        viewModelScope.launch {
            _user.value = _user.value
        }
    }

    val user = _user.switchMap {
        userRepo.loadUser()
    }

    val baseBook = _baseBook.switchMap {
        bookRepo.baseBook()
    }
    val topSale = _topSale.switchMap {
        bookRepo.topSale()
    }
    val topNew = _topNew.switchMap {
        bookRepo.topNew()
    }
    val categories = _categories.switchMap {
        bookRepo.categories()
    }
    val book = _book.switchMap {
        bookRepo.getBook(it)
    }

    val cart = _cart.switchMap {
        cartRepo.getCart()
    }
    val add = _add.switchMap {
        cartRepo.addItem(it.first, it.second)
    }
    val remove = _remove.switchMap {
        cartRepo.removeItem(it)
    }
    val clear = _clear.switchMap {
        cartRepo.clearCart()
    }

    val make = _make.switchMap {
        orderRepo.makeOrder(it.first, it.third, it.second)
    }
    val orders = _orders.switchMap {
        orderRepo.getOrders()
    }
    val order = _order.switchMap {
        orderRepo.getOrder(it)
    }

    fun getBaseBook() {
        _baseBook.value = _baseBook.value
    }

    fun getTopSale() {
        _topSale.value = _topSale.value
    }

    fun getTopNew() {
        _topNew.value = _topNew.value
    }

    fun getCategories() {
        _categories.value = _categories.value
    }

    fun getBook(id: Long) {
        _book.value = id
    }

    fun getCart() {
        _cart.value = _cart.value
    }

    fun add(id: Long, quantity: Int) {
        _add.value = Pair(id, quantity)
    }

    fun remove(id: Long) {
        _remove.value = id
    }

    fun clearCart() {
        _clear.value = _clear.value
    }

    fun getOrders() {
        _orders.value = _orders.value
    }

    fun getOrder(id: Long) {
        _order.value = id
    }

    fun signOut() {
        userRepo.clearUser()
    }

    fun makeOrder(name: String, phone: String, address: String) {
        _make.value = Triple(name, phone, address)
    }
}