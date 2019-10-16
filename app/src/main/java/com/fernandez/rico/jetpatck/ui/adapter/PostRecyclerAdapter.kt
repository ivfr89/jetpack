package com.fernandez.rico.jetpatck.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.fernandez.rico.jetpatck.R
import com.fernandez.rico.jetpatck.domain.Post
import kotlinx.android.synthetic.main.item_post.view.*

class PostRecyclerAdapter : PagedListAdapter<Post, PostRecyclerAdapter.PostRecyclerViewHolder>(postDiffCallback)
{

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostRecyclerViewHolder =
        PostRecyclerViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.item_post,parent,false))

    override fun onBindViewHolder(holder: PostRecyclerViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    class PostRecyclerViewHolder(view: View): RecyclerView.ViewHolder(view)
    {
        fun onBind(item: Post?) = with(itemView){
            txtUserName.text = item?.title?.capitalize()
            txtBody.text = item?.body
        }
    }

    companion object{

        val postDiffCallback = object:  DiffUtil.ItemCallback<Post>()
        {
            override fun areItemsTheSame(oldItem: Post, newItem: Post): Boolean = oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Post, newItem: Post): Boolean = oldItem == newItem

        }
    }

}

