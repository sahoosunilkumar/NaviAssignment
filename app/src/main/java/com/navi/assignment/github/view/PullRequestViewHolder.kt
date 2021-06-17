package com.navi.assignment.github.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.navi.assignment.github.GlideRequests
import com.navi.assignment.github.R
import com.navi.assignment.github.model.PullInfo

class PullRequestViewHolder(view: View, private val glide: GlideRequests)
    : RecyclerView.ViewHolder(view) {
    private val title: TextView = view.findViewById(R.id.title)
    private val user: TextView = view.findViewById(R.id.user)
    private val createdDate: TextView = view.findViewById(R.id.created_date_tv)
    private val closedDate: TextView = view.findViewById(R.id.closed_date_tv)
    private val thumbnail: ImageView = view.findViewById(R.id.thumbnail)


    fun bind(post: PullInfo?) {
        title.text = post?.title ?: "loading"
        user.text = itemView.context.resources.getString(R.string.post_subtitle,
                post?.user?.login ?: "")
        createdDate.text = itemView.context.resources.getString(R.string.created_at,post?.createdAt?:"")
        closedDate.text = itemView.context.resources.getString(R.string.closed_at,post?.closedAt?:"")
        if (!post?.user?.avatarUrl.isNullOrEmpty()) {
            thumbnail.visibility = View.VISIBLE
            glide.load(post?.user?.avatarUrl)
                    .centerCrop()
                    .placeholder(R.drawable.ic_insert_photo_black_48dp)
                    .into(thumbnail)
        } else {
            thumbnail.visibility = View.GONE
            glide.clear(thumbnail)
        }
    }

    companion object {
        fun create(parent: ViewGroup, glide: GlideRequests): PullRequestViewHolder {
            val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_pull_request, parent, false)
            return PullRequestViewHolder(view, glide)
        }
    }

}