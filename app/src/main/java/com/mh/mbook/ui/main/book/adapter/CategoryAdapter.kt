package com.mh.mbook.ui.main.book.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.mh.mbook.R
import com.mh.mbook.api.response.CategoryResponse
import com.mh.mbook.databinding.ItemCategoryBinding
import com.mh.mbook.ui.common.DataBoundListAdapter
import com.mh.mbook.util.AppExecutors

class CategoryAdapter(
    executors: AppExecutors
) : DataBoundListAdapter<CategoryResponse, ItemCategoryBinding>(
    appExecutors = executors,
    diffCallback = object : DiffUtil.ItemCallback<CategoryResponse>() {
        override fun areItemsTheSame(
            oldItem: CategoryResponse,
            newItem: CategoryResponse
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: CategoryResponse,
            newItem: CategoryResponse
        ): Boolean {
            return oldItem == newItem
        }
    }
) {
    override fun createBinding(
        parent: ViewGroup
    ): ItemCategoryBinding {
        return DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_category, parent,
            false
        )
    }

    override fun bind(
        binding: ItemCategoryBinding,
        item: CategoryResponse
    ) {
        Glide.with(binding.root.context)
            .load(item.iconUrl)
            .into(binding.image)
        binding.name.text = item.name
    }
}