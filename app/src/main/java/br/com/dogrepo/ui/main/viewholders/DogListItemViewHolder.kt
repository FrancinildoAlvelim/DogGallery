package br.com.dogrepo.ui.main.viewholders

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import br.com.dogrepo.R
import br.com.dogrepo.util.GenericAdapter
import br.com.dogrepo.util.extensions.loadImage

class DogListItemViewHolder : RecyclerView.ViewHolder, GenericAdapter.ViewBinder<String> {

    private val dogPicture: ImageView
    private val dogPictureLoading: ProgressBar
    private val context: Context

    constructor(view: View) : super(view) {
        this.context = view.context
        this.dogPicture = view.findViewById(R.id.dogPicture)
        this.dogPictureLoading = view.findViewById(R.id.dogPictureLoading)
    }

    override fun bindView(data: String) {
        dogPicture.loadImage(uri = data,
            onDone = {
                hideImageLoading()
            },
            onError = {
                hideImageLoading()
            })
    }

    private fun hideImageLoading() {
        dogPictureLoading.let { dogPictureLoading.visibility = View.GONE }
    }
}
