package com.example.newstart.utils

import android.content.Context
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions

/**
 * Created by Roy
 */
object GlideManager {

    fun loadImage(context: Context, url: String, imageView: ImageView) {
        Glide.with(context)
            .load(url)
            .apply(RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)
            )
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(imageView)
    }

    fun clearCache(context: Context) {
        Glide.get(context).clearMemory()
        Glide.get(context).clearDiskCache()
    }
}