package com.mh.mbook.ui.main.book.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import com.mh.mbook.R
import com.mh.mbook.api.response.RatingResponse
import com.mh.mbook.databinding.ItemRatingBinding
import com.mh.mbook.ui.common.DataBoundListAdapter
import com.mh.mbook.util.AppExecutors

class RatingAdapter(
    executors: AppExecutors
) : DataBoundListAdapter<RatingResponse, ItemRatingBinding>(
    appExecutors = executors,
    diffCallback = object : DiffUtil.ItemCallback<RatingResponse>() {
        override fun areItemsTheSame(
            oldItem: RatingResponse,
            newItem: RatingResponse
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: RatingResponse,
            newItem: RatingResponse
        ): Boolean {
            return oldItem == newItem
        }
    }
) {
    override fun createBinding(
        parent: ViewGroup
    ): ItemRatingBinding {
        return DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.item_rating, parent,
            false
        )
    }

    override fun bind(
        binding: ItemRatingBinding,
        item: RatingResponse
    ) {
        binding.rating = item
        binding.comment.setOnClickListener {
            it as TextView
            if (it.maxLines == 3) {
                it.maxLines = 1000
            } else it.maxLines = 3
        }
    }
}