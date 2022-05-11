package com.mh.mbook.ui.main.cart

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import com.bumptech.glide.Glide
import com.mh.mbook.R
import com.mh.mbook.api.response.ItemResponse
import com.mh.mbook.databinding.ItemCartItemBinding
import com.mh.mbook.ui.common.DataBoundListAdapter
import com.mh.mbook.util.AppExecutors

class CartItemAdapter(
    executors: AppExecutors,
    val callback: (item: ItemResponse) -> Unit
) : DataBoundListAdapter<ItemResponse, ItemCartItemBinding>(
    appExecutors = executors,
    diffCallback = object : DiffUtil.ItemCallback<ItemResponse>() {
        override fun areItemsTheSame(
            oldItem: ItemResponse,
            newItem: ItemResponse
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: ItemResponse,
            newItem: ItemResponse
        ): Boolean {
            return oldItem == newItem
        }
    }
) {
    override fun createBinding(
        parent: ViewGroup
    ): ItemCartItemBinding {
        return DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_cart_item, parent,
            false
        )
    }

    @SuppressLint("SetTextI18n")
    override fun bind(
        binding: ItemCartItemBinding,
        item: ItemResponse
    ) {
        Glide.with(binding.root.context)
            .load(item.imageUrl)
            .into(binding.image)
        binding.name.text = item.name
        binding.price.text = "Giá: ${item.displayPrice}"
        binding.quantity.text = "Số lượng: ${item.quantity}"
        binding.btnRemove.setOnClickListener {
            callback.invoke(item)
        }
    }
}