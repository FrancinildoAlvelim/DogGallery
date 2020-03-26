package br.com.dogrepo.util.extensions

import android.graphics.drawable.Drawable
import android.widget.ImageView
import br.com.dogrepo.R
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

fun ImageView.loadImage(uri: String,  onDone: ()-> Unit, onError: ()-> Unit ) {

    Glide.with(this.context)
        .load(uri)
        .placeholder(R.drawable.ic_paw_print)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .listener( object : RequestListener<Drawable>{
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
                onDone()
                return false
            }

        })
        .into(this)
}

