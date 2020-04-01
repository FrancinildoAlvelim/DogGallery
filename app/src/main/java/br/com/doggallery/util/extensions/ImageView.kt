package br.com.doggallery.util.extensions

import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

fun ImageView.loadImage(uri: String, onDone: () -> Unit = {}, onError: () -> Unit = {}) {

    this@loadImage.scaleType = ImageView.ScaleType.CENTER

    Glide.with(this.context)
        .load(uri)
        .placeholder(android.R.color.darker_gray)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .listener(object : RequestListener<Drawable> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: Target<Drawable>?,
                isFirstResource: Boolean
            ): Boolean {
                onError()
                return true
            }

            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: Target<Drawable>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                this@loadImage.scaleType = ImageView.ScaleType.FIT_CENTER
                onDone()
                return false
            }

        })
        .transition(DrawableTransitionOptions.withCrossFade())
        .into(this)
}

