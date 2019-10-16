package com.fernandez.rico.jetpatck.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.fernandez.rico.jetpatck.R
import com.fernandez.rico.jetpatck.core.extensions.loadImage
import com.fernandez.rico.jetpatck.domain.Photo
import kotlinx.android.synthetic.main.item_photo.view.*

class PhotoRecyclerAdapter : PagedListAdapter<Photo, PhotoRecyclerAdapter.PhotoRecyclerViewHolder>(postDiffCallback)
{

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoRecyclerViewHolder =
        PhotoRecyclerViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.item_photo,parent,false))

    override fun onBindViewHolder(holder: PhotoRecyclerViewHolder, position: Int) {
        holder.onBind(getItem(position))
    }

    class PhotoRecyclerViewHolder(view: View): RecyclerView.ViewHolder(view)
    {
        fun onBind(item: Photo?) = with(itemView){
            imgTop.loadImage(item?.thumbnailUrl)
            txtUserName.text = item?.title
        }
    }

    companion object{

        val postDiffCallback = object:  DiffUtil.ItemCallback<Photo>()
        {
            override fun areItemsTheSame(oldItem: Photo, newItem: Photo): Boolean = oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Photo, newItem: Photo): Boolean = oldItem == newItem

        }
    }

}

