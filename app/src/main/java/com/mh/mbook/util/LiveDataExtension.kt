package com.mh.mbook.util

import androidx.lifecycle.MutableLiveData

fun MutableLiveData<Any>.active() {
    value = value
}