package br.com.doggallery.ui.main.viewholders

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.recyclerview.widget.RecyclerView
import br.com.doggallery.R
import br.com.doggallery.util.GenericAdapter
import br.com.doggallery.util.extensions.loadImage

class DogListItemViewHolder : RecyclerView.ViewHolder, GenericAdapter.ViewBinder<String> {

    private var dogSelectedHandler: (image: String) -> Unit

    private val dogPicture: ImageView
    private val dogPictureLoading: ProgressBar
    private val context: Context

    constructor(view: View, onDogSelected: (image: String) -> Unit) : super(view) {
        this.context = view.context
        this.dogPicture = view.findViewById(R.id.dogPicture)
        this.dogPictureLoading = view.findViewById(R.id.dogPictureLoading)
        this.dogSelectedHandler = onDogSelected
    }

    override fun bindView(data: String) {
        this.dogPicture.loadImage(uri = data,
            onDone = {
                hideImageLoading()
            },
            onError = {
                hideImageLoading()
            })
        this.dogPicture.setOnClickListener {
            this.dogSelectedHandler(data)
        }
    }

    private fun hideImageLoading() =
        dogPictureLoading.let { it.visibility = View.GONE }
}
