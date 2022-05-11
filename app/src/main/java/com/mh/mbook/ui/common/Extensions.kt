package com.mh.mbook.ui.common

import android.app.Activity
import android.content.Context
import android.widget.Toast
import androidx.fragment.app.Fragment

fun Activity.showMessage(msg: String?) {
    Toast.makeText(this as Context, msg, Toast.LENGTH_SHORT).show()
}

fun Fragment.showMessage(msg: String?) {
    Toast.makeText(requireContext(), msg, Toast.LENGTH_SHORT).show()
}