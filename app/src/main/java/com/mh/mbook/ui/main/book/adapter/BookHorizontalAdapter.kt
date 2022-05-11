package com.mh.mbook.ui.main.book.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.mh.mbook.R
import com.mh.mbook.api.response.BookResponse
import com.mh.mbook.databinding.ItemBookHorizontalBinding
import com.mh.mbook.ui.common.DataBoundListAdapter
import com.mh.mbook.util.AppExecutors
import java.text.NumberFormat
import java.util.*

class BookHorizontalAdapter(
    executors: AppExecutors,
    val callback: (item: BookResponse) -> Unit
) : DataBoundListAdapter<BookResponse, ItemBookHorizontalBinding>(
    appExecutors = executors,
    diffCallback = object : DiffUtil.ItemCallback<BookResponse>() {
        override fun areItemsTheSame(
            oldItem: BookResponse,
            newItem: BookResponse
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: BookResponse,
            newItem: BookResponse
        ): Boolean {
            return oldItem == newItem
        }
    }
) {
    override fun createBinding(
        parent: ViewGroup
    ): ItemBookHorizontalBinding {
        return DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_book_horizontal, parent,
            false
        )
    }

    override fun bind(
        binding: ItemBookHorizontalBinding,
        item: BookResponse
    ) {
        Glide.with(binding.root.context)
            .load(item.imageUrl)
            .into(binding.image)
        binding.name.text = item.name
        binding.star.text = item.star.toString()
        binding.price.text = item.displayPrice
        if (item == currentList.last()) {
            binding.padding.visibility = View.VISIBLE
        }
        binding.root.setOnClickListener {
            callback.invoke(item)
        }
    }
}