package com.fernandez.rico.jetpatck.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.fernandez.rico.jetpatck.R
import com.fernandez.rico.jetpatck.domain.User
import kotlinx.android.synthetic.main.item_user.view.*

class UserRecyclerAdapter : PagedListAdapter<User, UserRecyclerAdapter.PostRecyclerViewHolder>(postDiffCallback)
{

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostRecyclerViewHolder =
        PostRecyclerViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.item_user,parent,false))

    override fun onBindViewHolder(holder: PostRecyclerViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    class PostRecyclerViewHolder(view: View): RecyclerView.ViewHolder(view)
    {
        fun onBind(item: User?) = with(itemView){
            txtName.text = item?.name
            txtUserName.text = context.getString(R.string.username,item?.userName)
            txtEmail.text = item?.email
            txtAddress.text = context.getString(R.string.address,item?.address?.street,item?.address?.city)
            txtPhone.text = item?.phone
        }
    }

    companion object{

        val postDiffCallback = object:  DiffUtil.ItemCallback<User>()
        {
            override fun areItemsTheSame(oldItem: User, newItem: User): Boolean = oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: User, newItem: User): Boolean = oldItem == newItem

        }
    }

}

