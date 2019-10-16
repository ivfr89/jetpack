package com.fernandez.rico.jetpatck.core.extensions

import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.fernandez.rico.jetpatck.R

fun View.show() {
    visibility= View.VISIBLE
}
fun View.hide() {
    visibility= View.GONE
}
fun ImageView.loadImage(image: String?, centerCrop: Boolean=false) {

    val options = RequestOptions()
         .placeholder(R.mipmap.ic_launcher)
        .error(R.drawable.ic_avatar)

    if(centerCrop)
        options.centerCrop()
    else
        options.centerInside()

    Glide.with(this).load(image).apply(options).into(this)
}