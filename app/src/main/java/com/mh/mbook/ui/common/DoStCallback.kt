package com.mh.mbook.ui.common

interface DoStCallback {
    fun doSt()
}

fun callback(callback: () -> Unit) = object : DoStCallback {
    override fun doSt() {
        callback.invoke()
    }
}