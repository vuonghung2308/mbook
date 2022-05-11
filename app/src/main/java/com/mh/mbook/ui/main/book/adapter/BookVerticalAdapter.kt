package com.mh.mbook.ui.main.book.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.mh.mbook.R
import com.mh.mbook.api.response.BookResponse
import com.mh.mbook.databinding.ItemBookVerticalBinding
import com.mh.mbook.ui.common.DataBoundListAdapter
import com.mh.mbook.util.AppExecutors

class BookVerticalAdapter(
    executors: AppExecutors,
    val callback: (item: BookResponse) -> Unit
) : DataBoundListAdapter<BookResponse, ItemBookVerticalBinding>(
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
    ): ItemBookVerticalBinding {
        return DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_book_vertical, parent,
            false
        )
    }

    @SuppressLint("SetTextI18n")
    override fun bind(
        binding: ItemBookVerticalBinding,
        item: BookResponse
    ) {
        Glide.with(binding.root.context)
            .load(item.imageUrl)
            .into(binding.image)
        binding.name.text = item.name
        binding.star.text = "Đánh giá: " + item.star.toString()
        binding.price.text = "Giá: " + item.displayPrice
        binding.number.text = (currentList.indexOf(item) + 1).toString()
        binding.author.text = "Tác giả: " + item.author
        binding.root.setOnClickListener {
            callback.invoke(item)
        }
    }
}