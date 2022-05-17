package com.mh.mbook.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import com.mh.mbook.repository.BookRepository
import com.mh.mbook.repository.CartRepository
import com.mh.mbook.repository.OrderRepository
import com.mh.mbook.repository.UserRepository
import com.mh.mbook.util.active
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
    private val _comment = MutableLiveData<Triple<Long, Int, String?>>()

    init {
        viewModelScope.launch { _user.active() }
    }

    val user = _user.switchMap { userRepo.loadUser() }

    val baseBook = _baseBook.switchMap { bookRepo.baseBook() }
    val topSale = _topSale.switchMap { bookRepo.topSale() }
    val topNew = _topNew.switchMap { bookRepo.topNew() }
    val categories = _categories.switchMap { bookRepo.categories() }
    val book = _book.switchMap { bookRepo.getBook(it) }
    val cart = _cart.switchMap { cartRepo.getCart() }
    val add = _add.switchMap { cartRepo.addItem(it.first, it.second) }
    val remove = _remove.switchMap { cartRepo.removeItem(it) }
    val clear = _clear.switchMap { cartRepo.clearCart() }

    val comment = _comment.switchMap {
        bookRepo.comment(it.first, it.second, it.third)
    }
    val make = _make.switchMap {
        orderRepo.makeOrder(it.first, it.third, it.second)
    }
    val orders = _orders.switchMap { orderRepo.getOrders() }
    val order = _order.switchMap { orderRepo.getOrder(it) }

    fun getBaseBook() = _baseBook.active()
    fun getTopSale() = _topSale.active()
    fun getTopNew() = _topNew.active()
    fun getCategories() = _categories.active()
    fun getBook(id: Long) = (_book.setValue(id))
    fun getCart() = _cart.active()


    fun add(id: Long, quantity: Int) =
        (_add.setValue(Pair(id, quantity)))

    fun removeItem(id: Long) =
        (_remove.setValue(id))

    fun clearCart() = _clear.active()
    fun getOrders() = _orders.active()

    fun getOrder(id: Long) =
        (_order.setValue(id))

    fun signOut() {
        userRepo.clearUser()
    }

    fun makeOrder(name: String, phone: String, address: String) {
        _make.value = Triple(name, phone, address)
    }

    fun comment(id: Long, star: Int, comment: String?) {
        _comment.value = Triple(id, star, comment)
    }
}