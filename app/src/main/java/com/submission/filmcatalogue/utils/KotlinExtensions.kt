package com.submission.filmcatalogue.utils

import android.widget.ImageView
import com.bumptech.glide.Glide

//implementasi glide dengan ktx
fun ImageView.loadImage(url: Int) {
    Glide.with(this.context)
        .load(url)
        .into(this)
}