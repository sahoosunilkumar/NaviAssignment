package com.navi.assignment.github.view

import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.navi.assignment.github.GlideRequests
import com.navi.assignment.github.model.PullInfo

class PullRequestListingAdapter(private val glide: GlideRequests)
    : PagingDataAdapter<PullInfo, PullRequestViewHolder>(POST_COMPARATOR) {

    override fun onBindViewHolder(holder: PullRequestViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onBindViewHolder(
            holder: PullRequestViewHolder,
            position: Int,
            payloads: MutableList<Any>
    ) {
        onBindViewHolder(holder, position)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PullRequestViewHolder {
        return PullRequestViewHolder.create(parent, glide)
    }

    companion object {
        val POST_COMPARATOR = object : DiffUtil.ItemCallback<PullInfo>() {
            override fun areContentsTheSame(oldItem: PullInfo, newItem: PullInfo): Boolean =
                    oldItem == newItem

            override fun areItemsTheSame(oldItem: PullInfo, newItem: PullInfo): Boolean =
                    oldItem.id == newItem.id

            override fun getChangePayload(oldItem: PullInfo, newItem: PullInfo): Any? {
                return null
            }
        }

    }
}
