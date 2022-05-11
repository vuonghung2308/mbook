package com.mh.mbook.ui.main.order.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import com.mh.mbook.R
import com.mh.mbook.api.response.OrderResponse
import com.mh.mbook.databinding.ItemOrderBinding
import com.mh.mbook.ui.common.DataBoundListAdapter
import com.mh.mbook.util.AppExecutors

class OrderAdapter(
    executors: AppExecutors,
    val callback: (item: OrderResponse) -> Unit
) : DataBoundListAdapter<OrderResponse, ItemOrderBinding>(
    appExecutors = executors,
    diffCallback = object : DiffUtil.ItemCallback<OrderResponse>() {
        override fun areItemsTheSame(
            oldItem: OrderResponse,
            newItem: OrderResponse
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: OrderResponse,
            newItem: OrderResponse
        ): Boolean {
            return oldItem == newItem
        }
    }
) {
    override fun createBinding(
        parent: ViewGroup
    ): ItemOrderBinding {
        return DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_order, parent,
            false
        )
    }

    override fun bind(
        binding: ItemOrderBinding,
        item: OrderResponse
    ) {
        binding.order = item
        binding.root.setOnClickListener {
            callback.invoke(item)
        }
    }
}