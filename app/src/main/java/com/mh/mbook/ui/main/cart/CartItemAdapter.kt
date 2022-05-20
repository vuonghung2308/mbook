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
import com.mh.mbook.ui.common.callback
import com.mh.mbook.util.AppExecutors

class CartItemAdapter(
    executors: AppExecutors,
    private val deleteCb: (item: ItemResponse) -> Unit,
    private val updateCb: (id: Long, quantity: Int) -> Unit
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
        binding.price.text = "Giá bán: ${item.displayPrice}"
        binding.tvQuantity.text = "${item.quantity}"
        binding.btnRemove.setOnClickListener {
            deleteCb.invoke(item)
        }
        binding.increaseCb = callback {
            val quantity = binding.tvQuantity.text.toString().toInt()
            binding.tvQuantity.text = (quantity + 1).toString()
            updateCb.invoke(item.id, quantity + 1)
        }
        binding.decreaseCb = callback {
            var quantity = binding.tvQuantity.text.toString().toInt()
            quantity = if (quantity == 1) 2 else quantity
            binding.tvQuantity.text = (quantity - 1).toString()
            updateCb.invoke(item.id, quantity - 1)
        }
    }
}