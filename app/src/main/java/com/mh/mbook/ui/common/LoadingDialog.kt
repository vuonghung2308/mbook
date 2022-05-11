package com.mh.mbook.ui.common

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import com.mh.mbook.R

class LoadingDialog(
    private val fm: FragmentManager
) : DialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        isCancelable = false
        val progressBar = ProgressBar(context)
        progressBar.indeterminateDrawable.setTint(
            ContextCompat.getColor(
                requireContext(),
                R.color.purple_700
            )
        )
        progressBar.background = ColorDrawable(Color.TRANSPARENT)
        dialog?.window?.decorView?.background = ColorDrawable(Color.TRANSPARENT)
        return progressBar
    }

    fun show() {
        if (!isVisible) {
            show(fm, "loading_dialog")
        }
    }

    override fun dismiss() {
        if (isVisible) {
            super.dismiss()
        }
    }
}